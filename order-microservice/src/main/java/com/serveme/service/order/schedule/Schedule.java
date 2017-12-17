package com.serveme.service.order.schedule;


import com.serveme.service.order.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class Schedule {
    private static final Logger logger = Logger.getLogger(Schedule.class.getName());

    private final OrderService orderService;

    private  final int paymentBulkSize;

    @Inject
    public Schedule(
            OrderService orderService,
            @Value("${order.payment.sync.bulkSize: 10}") int paymentBulkSize){
        this.orderService = orderService;
        this.paymentBulkSize = paymentBulkSize;
    }

    @Scheduled(fixedDelay = 1000 * 60 * 5)//Every 10 minutes
    public void processNextCharges(){
        logger.log(Level.INFO, "Payment synchronization Schedule starting...");
        orderService.invoiceFinishedOrders(paymentBulkSize);
        logger.log(Level.INFO, "Payment synchronization Schedule finished");

    }

    @Scheduled(cron = "0 0 12 ? * MON")
    public void generateInvoicesForAllRestaurants(){
        logger.log(Level.INFO, "Restaurant invoicing Schedule starting...");
        orderService.generateInvoicesForAllRestaurants();
        logger.log(Level.INFO, "Restaurant invoicing Schedule finished");

    }
}
