package com.serveme.payment.payment.impl;

import com.serveme.payment.domain.CustomerCharge;
import com.serveme.payment.domain.PaymentDetail;
import com.serveme.payment.enums.CurrencyISO4217;
import com.serveme.payment.enums.PaymentProviderId;
import com.serveme.payment.helpers.CalcHelper;
import com.serveme.payment.payment.ChargeResultWrapper;
import com.serveme.payment.payment.PaymentProvider;
import com.serveme.payment.util.DataUtil;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.net.RequestOptions;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

public class StripeProvider implements PaymentProvider {


    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(StripeProvider.class);
    private final PaymentProviderId providerId;

    public StripeProvider(
            PaymentProviderId providerId,
            String apiSecretKey){
        this.providerId = providerId;
        Stripe.apiKey = apiSecretKey;
    }


    @Override
    public PaymentProviderId getId(){
        return providerId;
    }



    @Override
    public String createCustomer(
            String cardToken,
            Map<String, Object> metadata
    ) throws StripeException {

        logger.info("Stripe creating customer ");
        return  createCustomer(
                cardToken,
                metadata.get("description") != null ?
                        metadata.get("description").toString() :
                        "NO DESCRIPTION"
        );
    }


    public void updateCustomer(
            String providerCustomerId,
            String newCardToken
    )throws Exception {

        logger.info("Stripe. Customer update starting");
        Customer cu;
        try{

            logger.info("Stripe .retrieving customer with customerId {}", providerCustomerId);
            cu = Customer.retrieve(providerCustomerId);
            if(cu == null){

                logger.info("Stripe. No customer for customerId {}", providerCustomerId);
                throw new RuntimeException(
                        "There is no customer for stripe id "+providerCustomerId);
            }

        }catch(Exception ex){
            logger.error(
                    "Stripe. Error retrieving customer with customerId " + providerCustomerId,
                    ex);
            throw new RuntimeException(ex.getMessage());

        }

        Map<String, Object> params = new HashMap<>();
        params.put("source", newCardToken);
        try{

            logger.info("Stripe. Updating customer in Stripe servers with customerId {}", providerCustomerId);
            cu.update(params);

        }catch(Exception ex){
            logger.error(
                    "Stripe. Error updating customer in stripe server with customerId " + providerCustomerId,
                    ex);
            throw new RuntimeException(ex.getMessage());
        }
    }


    @Override
    public ChargeResultWrapper charge(
            String customerToken,
            @Valid CustomerCharge c) throws StripeException{
        logger.info(
                "Stripe: Charging to stripe token {}\n{}",
                customerToken,
                c);
        Charge chargeResult = charge(
                customerToken,
                c.getIdempotentTransactionId(),
                CalcHelper.toCents(c.getAmount()),
                CurrencyISO4217.GBP.toString(),
                String.format(
                        "user %d, orderInvoice %s ",
                        c.getUserId(),
                        c.getInvoice().getId()
                )
        );
        if(chargeResult.getPaid()) {
            logger.info(
                    "Stripe: Charge to stripe token {} successfully performed",
                    customerToken);
        } else {
            logger.info(
                    "Stripe: Charge to stripe token {} FAILED",
                    customerToken);
        }
        return new ChargeResultWrapper(chargeResult);
    }

    /**
     * PRIVATES
     */
    private String createCustomer(String token, String description) throws StripeException {

        logger.info("Stripe Provider creating customer with token {} and description {}", token, description);
        Map<String, Object> customerParams = new HashMap<>();
        customerParams.put("source", token);
        customerParams.put("description", description);
        return Customer.create(customerParams).getId();
    }

    private Charge charge(
            String stripeCustomerId,
            String idempotentId,
            int amount,
            String currency,
            String description) throws StripeException{

        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("customer", stripeCustomerId);
        chargeParams.put("amount", amount);
        chargeParams.put("currency", currency);
        chargeParams.put("description", description);

        //TODO send emails for payment notification ;).
        // be carefully as in case of invalid email, payment doesn't go through
//        if(Environment.isLive()){
//            if(email != null && !email.isEmpty()){
//                chargeParams.put("receipt_email", email.replace(" ", ""));
//            }
//        }

        return Charge.create(
                chargeParams,
                RequestOptions
                        .builder()
                        .setIdempotencyKey(idempotentId)
                        .build()
        );
    }
}
