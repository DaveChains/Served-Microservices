package com.serveme.service.order.external.service;

import com.serveme.service.order.domain.Order;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/invoice")
public interface PaymentService {
    @POST
    @Path("/invoice/order")
    @Consumes("application/json")
    @Produces("application/json")
    void invoiceOrder(Order order);
}
