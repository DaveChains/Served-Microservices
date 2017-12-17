package com.serveme.payment.api;

import com.serveme.payment.api.dto.payment.CardInputDto;
import com.serveme.payment.api.dto.payment.CardInputDtoOld;
import com.serveme.payment.domain.PaymentDetail;
import com.serveme.payment.exceptions.AuthorizationException;
import com.serveme.payment.service.PaymentDetailService;
import com.serveme.payment.api.dto.UserDto;
import com.stripe.exception.StripeException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Davids-iMac on 29/05/16.
 */
@Controller
public class PaymentDetailController {

//    private static final org.slf4j.Logger logger = LoggerFactory
//            .getLogger(PaymentDetailController.class);


    private static final Logger logger = Logger.getLogger(PaymentDetailController.class.getName());

    private final PaymentDetailService paymentDetailService;

    private final String stripeApiPublicKey;

    @Inject
    public PaymentDetailController(
            PaymentDetailService paymentDetailService,
            @Value("${stripe.apiPublicKey}") String stripeApiPublicKey){
        this.paymentDetailService = paymentDetailService;
        this.stripeApiPublicKey = stripeApiPublicKey;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(
            value = "/payment/customer/{customerId}/payment_detail",
            method = RequestMethod.POST,
            produces = "application/json"
    )
    public void setPaymentDetail(
            @PathVariable("customerId") Long customerId,
            @RequestBody @Valid CardInputDto cardInput,
            HttpServletRequest req) throws StripeException{

        logger.log(Level.INFO,
                "Setting payment detail for customer "+customerId+" . " +
                        "\nDetails:\n"+cardInput.toString());
        UserDto user = (UserDto)req.getAttribute("user");
        if(user.getId() != customerId){
            logger.log(Level.SEVERE,
                    "CustomerId(" + customerId + ") is not the owner " +
                    "of the accessToken("+user.getId()+")");
            throw new AuthorizationException(customerId);
        }
        paymentDetailService.setCustomerPaymentDetail(
                customerId,
                cardInput.getCardToken(),
                cardInput.getCardType(),
                cardInput.getCardLastDigits());
        logger.log(Level.INFO,
                "Card detail successfully set for customer "+customerId);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(
            value = "/payment/{customerId}/card",
            method = RequestMethod.GET,
            produces = "application/json")
    public PaymentDetail getCard(
            @PathVariable("customerId") Long customerId,
            HttpServletRequest req) throws StripeException{

        UserDto user = (UserDto)req.getAttribute("user");
        if(user.getId() != customerId){
            throw new AuthorizationException(customerId);
        }
        return paymentDetailService.getPaymentDetailByUser(user.getId());
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(
            value = "/payment/stripe/key/public",
            method = RequestMethod.GET,
            produces = "application/json")
    public String getStripePublicApiKey() {
        return stripeApiPublicKey;
    }

}
