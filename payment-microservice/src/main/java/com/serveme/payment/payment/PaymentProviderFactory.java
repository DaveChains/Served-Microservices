package com.serveme.payment.payment;

import com.serveme.payment.enums.PaymentProviderId;
import com.serveme.payment.exceptions.NoImplementedProviderException;
import com.serveme.payment.payment.impl.StripeProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@Component
public class PaymentProviderFactory {

    private final Map<PaymentProviderId, PaymentProvider> providerMap;

    @Inject
    public PaymentProviderFactory(@Value("${stripe.apiSecretKey}") String stripeApiSecretKey){
        providerMap = new HashMap<>();
        providerMap.put(PaymentProviderId.STRIPE,
                new StripeProvider(
                        PaymentProviderId.STRIPE,
                        stripeApiSecretKey)
        );

    }

    public PaymentProvider defaultInstance(){
       return getInstance(PaymentProviderId.STRIPE);
    }

    public PaymentProvider getInstance(PaymentProviderId paymentProviderId){
        PaymentProvider provider = providerMap.get(paymentProviderId);
        if(provider == null) {
            throw new NoImplementedProviderException(paymentProviderId);
        }
        return provider;
    }
}
