package com.millennialslab.served.service.service.impl;

import com.millennialslab.served.service.api.dto.input.PaymentInfoDto;
import com.millennialslab.served.service.api.dto.output.CardOutputDto;
import com.millennialslab.served.service.service.PaymentService;
import com.millennialslab.served.service.util.http.HttpServiceBase;
import com.millennialslab.served.service.util.http.HttpServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PaymentServiceImpl implements PaymentService {
    protected static final String CREATE_STRIPE_CUSTOMER = "/payment/customer/{id}/payment_detail";
    protected static final String GET_CARD_INFO = "/payment/{id}/card";

    Logger logger = Logger.getLogger(PaymentServiceImpl.class.getName());

    @Value("${service.payment.host}")
    private String host;

    @Value("${service.payment.port}")
    private int port;

    @Override
    public boolean addPaymentCard(String accessToken, long userId, String cardToken, String cardType, String cardLastDigits) {
        try {
            logger.log(Level.INFO, "Calling payment service for user " + userId);
            new HttpServiceBase()
                    .setUrl("http://" + host + ":" + port)
                    .setHttpMethod(HttpMethod.POST)
                    .setBody(new CardOutputDto(cardToken, cardType, cardLastDigits))
                    .setPathParam("id", String.valueOf(userId))
                    .setHeader("access-token", accessToken)
                    .setPath(CREATE_STRIPE_CUSTOMER)
                    .call();
            logger.log(Level.INFO, "Call to  payment service for user " + userId+" success");
            return true;
        } catch (HttpServiceException ex) {
            logger.log(Level.WARNING, "Can't connect to payment service", ex);
            throw new RuntimeException(ex);
        } catch (Exception ex) {
            logger.log(Level.WARNING, ex.toString(), ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public PaymentInfoDto getCurrentCard(String accessToken, long userId) {
        try {
            return (PaymentInfoDto) new HttpServiceBase()
                    .setUrl("http://" + host + ":" + port)
                    .setHttpMethod(HttpMethod.GET)
                    .setPathParam("id", String.valueOf(userId))
                    .setHeader("access-token", accessToken)
                    .setPath(GET_CARD_INFO)
                    .setReturnedClass(PaymentInfoDto.class)
                    .call();
        } catch (HttpServiceException ex) {
            logger.log(Level.WARNING, "Can't connect to payment service");
        } catch (Exception ex) {
            logger.log(Level.WARNING, ex.toString());
        }
        logger.log(Level.WARNING, "Error");
        return null;
    }
}
