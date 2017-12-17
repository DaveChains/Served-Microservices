package com.serveme.payment.schedule;


import com.serveme.payment.service.PaymentProcessorService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class Schedule {
    private static final Logger logger = Logger.getLogger(Schedule.class.getName());

    private final PaymentProcessorService paymentProcessorService;

    @Inject
    public Schedule(PaymentProcessorService paymentProcessorService){
        this.paymentProcessorService = paymentProcessorService;
    }

    @Scheduled(fixedDelay = 1000 * 60 * 10)//Every 10 minutes
    public void processNextCharges(){
        logger.log(Level.INFO, "New order payments Schedule starting...");
        paymentProcessorService.defaultProcessNextCharges();
        logger.log(Level.INFO, "New order payments Schedule finished");

    }



}
