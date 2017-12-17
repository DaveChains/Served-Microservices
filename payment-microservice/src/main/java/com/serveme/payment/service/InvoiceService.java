package com.serveme.payment.service;

import com.serveme.payment.api.dto.order.OrderDto;
import com.serveme.payment.domain.Invoice;
import com.serveme.payment.exceptions.InvoicePersistenceException;
import com.serveme.payment.exceptions.OrderWithNoPayerWithPaymentDetailException;

public interface InvoiceService {
    /**
     * Retrieves the invoice linked to a given order.
     * @param orderId Given orderId
     * @return orderInvoice
     */
    Invoice getByOrderId(String orderId);

    /**
     * Generates an invoice from a given order.
     * It's idempotent, so in case or duplication(already an invoice for the given order)
     * will abort without throwing any exception .
     * @param order The order which the invoice will be generated from
     * @param order The order which the invoice will be generated from
     * @throws OrderWithNoPayerWithPaymentDetailException none of the guest in the order has
     * payments registered
     * @throws InvoicePersistenceException problem when saving invoice in db
     */
    void executeInvoice(OrderDto order);

}
