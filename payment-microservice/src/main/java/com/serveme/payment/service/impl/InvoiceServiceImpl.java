package com.serveme.payment.service.impl;

import com.serveme.payment.api.dto.order.OrderDto;
import com.serveme.payment.domain.*;
import com.serveme.payment.enums.PaymentProviderId;
import com.serveme.payment.exceptions.InvoiceNotFoundException;
import com.serveme.payment.helpers.InvoiceHelper;
import com.serveme.payment.persistence.InvoiceRepository;
import com.serveme.payment.service.InvoiceService;
import com.serveme.payment.helpers.OrderHelper;
import com.serveme.payment.service.PaymentDetailService;
import com.serveme.payment.service.RestaurantTabService;
import com.serveme.payment.service.FeeService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This service is in charge of the invoice generation.
 * It doesn't perform any payment
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(InvoiceServiceImpl.class);
    private final InvoiceRepository invoiceRepository;
    private final PaymentDetailService paymentDetailService;
    private final RestaurantTabService restaurantTabService;
    private final FeeService feeService;

    private final static PaymentProviderId paymentProvider = PaymentProviderId.STRIPE;

    @Inject
    public InvoiceServiceImpl(
            InvoiceRepository invoiceRepository,
            RestaurantTabService restaurantTabService,
            PaymentDetailService paymentDetailService,
            FeeService feeService){
        this.invoiceRepository = invoiceRepository;
        this.paymentDetailService = paymentDetailService;
        this.restaurantTabService = restaurantTabService;
        this.feeService = feeService;
    }

    @Override
    public Invoice getByOrderId(String orderId) {
        logger.info("Retrieving oder {}", orderId);
        Invoice invoice = invoiceRepository.findByOrderId(orderId);
        logger.info("Retrieved oder {} successfully", orderId);
        if(invoice == null){
            throw new InvoiceNotFoundException(orderId);
        }
        return invoice;
    }

    @Override
    @Transactional
    public void executeInvoice(final OrderDto order){
        if(invoiceRepository.findByOrderId(order.getId()) == null) {
            OrderHelper.validateOrder(order);
            final List<PaymentDetail> paymentDetails = getCustomersPaymentFromOrder(order);
            final Invoice invoice = paymentDetails!= null && paymentDetails.size() > 0 ?
                    generateInvoiceWithPayers(order, paymentDetails) :
                    generateInvoiceWithNoPayers(order);
            invoice.setLines(getInvoiceLines(invoice, order));
            invoiceRepository.save(invoice);
        } else {
            logger.info("Attempting to insert duplicate order {}", order.getId());
        }

    }

    /**
     *
     * @param stripeCustomers List of stripe customer details
     * @param amountInCents Amount to be charged to the guest
     * @param invoice Invoice from which the guest will be charged
     * @return List of charges with all the information to be processed
     */
    private List<CustomerCharge> getCustomerChargesFromOrder(
            List<PaymentDetail> stripeCustomers,
            BigDecimal amountInCents,
            Invoice invoice) {
        return stripeCustomers
                .stream()
                .map(stripeCustomerPayment ->
                        InvoiceHelper.generateCustomerOrderCharge(
                                amountInCents,
                                invoice,
                                stripeCustomerPayment,
                                paymentProvider
                        ))
                .collect(Collectors.toList());
    }

    private List<InvoiceLine> getInvoiceLines(Invoice invoice, OrderDto order) {
        return order
                .getItems()
                .stream()
                .map(item ->
                        InvoiceHelper.generateInvoiceLineFromItem(
                                invoice,
                                item
                        ))
                .collect(Collectors.toList());
    }

    /**
     *
     * @param order From where the guests are retrieved
     * @return A list with stripe details of the guests
     */
    private List<PaymentDetail> getCustomersPaymentFromOrder(OrderDto order) {
        List<Long> orderGuestIdList = OrderHelper.getGuestIdsFromOrder(order);
        final List<PaymentDetail> stripeCustomers = paymentDetailService
                .getPaymentDetailByUserList(orderGuestIdList);
        return stripeCustomers != null ? stripeCustomers : new ArrayList<>();
    }


    /**
     * Generates an invoice.
     * First gets the restaurant tab.
     * If there isn't any for the current period, one is created.
     */
    private Invoice generateInvoiceWithPayers(OrderDto order, List<PaymentDetail> paymentDetails) {
        final Invoice invoice = createInvoice(order, paymentDetails.size());

        List<CustomerCharge> customerCharges = getCustomerChargesFromOrder(
                paymentDetails,
                OrderHelper.getTotalCustomerSpentPeerHead(order),
                invoice
        );
        invoice.setCustomerCharges(customerCharges);
        return invoice;
    }


    private Invoice createInvoice(OrderDto order, int peopleToPay) {
        final RestaurantTab restaurantTab = restaurantTabService
                .getCurrentOrStartNew(order.getRestaurant().getId());
        return new Invoice(
                order.getId(),
                order.getUniqueId(),
                restaurantTab,
                OrderHelper.getSubtotal(order),
                order.getTip(),
                order.getServiceChargePerc(),
                order.getDiscountPerc(),
                feeService.getPaymentGatewayFeePercentage(),
                feeService.getPaymentGatewayFeeFixedAmount(),
                feeService.getServemeFeePercentage(),
                feeService.getServemeFeeFixAmount(),
                order.isTestMode(),
                peopleToPay);
    }

    private Invoice generateInvoiceWithNoPayers(OrderDto order) {
        final RestaurantTab restaurantTab = restaurantTabService
                .getCurrentOrStartNew(order.getRestaurant().getId());
        return new Invoice(
                order.getId(),
                order.getUniqueId(),
                restaurantTab,
                OrderHelper.getSubtotal(order),
                order.getTip(),
                order.getServiceChargePerc(),
                order.getDiscountPerc(),
                feeService.getPaymentGatewayFeePercentage(),
                feeService.getPaymentGatewayFeeFixedAmount(),
                feeService.getServemeFeePercentage(),
                feeService.getServemeFeeFixAmount(),
                order.isTestMode(),
                0);
    }

}
