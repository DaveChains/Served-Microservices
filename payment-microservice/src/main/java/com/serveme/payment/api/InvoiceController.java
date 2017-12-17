package com.serveme.payment.api;

import com.serveme.payment.api.dto.order.OrderDto;
import com.serveme.payment.domain.Invoice;
import com.serveme.payment.service.InvoiceService;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * Created by Davids-iMac on 29/05/16.
 */
@Controller
public class InvoiceController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(InvoiceController.class);
    private final InvoiceService invoiceService;

    @Inject
    public InvoiceController(InvoiceService invoiceService){
        this.invoiceService = invoiceService;

    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/invoice/order", method = RequestMethod.POST, produces = "application/json")
    public void createInvoiceForOrder(
            @RequestBody @Valid OrderDto order){
            invoiceService.executeInvoice(order);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(
            value = "/invoice/order/{orderId}/refresh",
            method = RequestMethod.PUT,
            produces = "application/json")
    public void createInvoiceForOrder(@PathVariable("orderId") String orderId){
        /**
         * TODO
         * This service will take the invoice linked to the given order.
         * If the invoice is in NO accepted status, will be refresh.
         * It's idempotent.
         * Refresh an invoice means to bring the order again, generate the charges and fees,
         * generate a new idempotentId for charge and leave it ready to be charged
         */
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(
            value = "/invoice/order/{orderId}",
            method = RequestMethod.GET,
            produces = "application/json")
    public Invoice getInvoiceByOrder(@PathVariable("orderId") String orderId){
        return invoiceService.getByOrderId(orderId);
    }


}
