package com.serveme.payment.service.impl;

import com.serveme.payment.api.dto.order.OrderDto;
import com.serveme.payment.api.dto.order.OrderItemDto;
import com.serveme.payment.api.dto.order.OrderPeopleDto;
import com.serveme.payment.api.dto.order.RestaurantOrderDto;
import com.serveme.payment.domain.Invoice;
import com.serveme.payment.domain.PaymentDetail;
import com.serveme.payment.domain.RestaurantTab;
import com.serveme.payment.enums.OrderGuestStatus;
import com.serveme.payment.enums.OrderStatus;
import com.serveme.payment.enums.PaymentProviderId;
import com.serveme.payment.persistence.InvoiceRepository;
import com.serveme.payment.service.FeeService;
import com.serveme.payment.service.PaymentDetailService;
import com.serveme.payment.service.RestaurantTabService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

//TODO add test when more than one payer
//TODO add test for trial
public class InvoiceServeTest {


    private InvoiceRepository invoiceRepository;
    private PaymentDetailService paymentDetailService;
    private RestaurantTabService restaurantTabService;
    private FeeService feeService;


    @Before
    public void before(){
        invoiceRepository = Mockito.mock(InvoiceRepository.class);
        paymentDetailService = Mockito.mock(PaymentDetailService.class);
        restaurantTabService = Mockito.mock(RestaurantTabService.class);
        feeService = Mockito.mock(FeeService.class);
    }

    @Test
    public void invoice_with_discount() {
        initMocks();
        InvoiceServiceImpl invoiceService = new InvoiceServiceImpl(
                invoiceRepository,
                restaurantTabService,
                paymentDetailService,
                feeService);

        invoiceService.executeInvoice(getOrder());

        ArgumentCaptor<Invoice> invoiceCaptor = ArgumentCaptor.forClass(Invoice.class);
        verify(invoiceRepository, Mockito.times(1)).save(invoiceCaptor.capture());

        Invoice invoiceCaptured = invoiceCaptor.getValue();
        Assert.assertNotNull("Invoice cannot be null", invoiceCaptured);
        Assert.assertEquals(
                "Total amount(subtotal+serviceCharge-discount) wrong",
                new BigDecimal(9.43).doubleValue(),
                invoiceCaptured.getCustomerNetAmount().doubleValue(),
                0.1);
        Assert.assertEquals(
                "Total serveme fee applied wong ",
                new BigDecimal(0.943).doubleValue(),
                invoiceCaptured.getServemeTotalFeeApplied().doubleValue(),
                0.1);
        Assert.assertEquals("Total Payment Gateway wrong",
                new BigDecimal(0.332).doubleValue(),
                invoiceCaptured.getPaymentGatewayTotalFeeApplied().doubleValue(),
                0.1);
        Assert.assertEquals(
                "Total restaurant benefit wrong",
                new BigDecimal(8.155).doubleValue(),
                invoiceCaptured.getRestaurantBenefit().doubleValue(),
                0.1);

    }

    private void initMocks() {
        doReturn(
                Collections.singletonList(
                        new PaymentDetail(
                                PaymentProviderId.STRIPE,
                                1,
                                "paymentGatewayToken1"))
        ).when(paymentDetailService).getPaymentDetailByUserList(Mockito.anyList());

        doReturn(
                new RestaurantTab(100, LocalDate.now(), LocalDate.now())
        ).when(restaurantTabService).getCurrentOrStartNew(Mockito.anyLong());

        doReturn(
                new BigDecimal(1.4)
        ).when(feeService).getPaymentGatewayFeePercentage();

        doReturn(
                new BigDecimal(0.2)
        ).when(feeService).getPaymentGatewayFeeFixedAmount();

        doReturn(
                new BigDecimal(10.0)
        ).when(feeService).getServemeFeePercentage();

        doReturn(
                new BigDecimal(0.0)
        ).when(feeService).getServemeFeeFixAmount();

        doReturn(
                new Invoice()
        ).when(invoiceRepository).save(Mockito.any(Invoice.class));
    }

    private OrderDto getOrder(){


        RestaurantOrderDto restaurant = new RestaurantOrderDto(2000);
        List<OrderItemDto> items = Arrays.asList(
                new OrderItemDto(1,"Dish1",new BigDecimal(20.0),1),
                new OrderItemDto(2,"Dish2",new BigDecimal(5.0),2)
        );
        List<OrderPeopleDto> people = Arrays.asList(
                new OrderPeopleDto(1, OrderGuestStatus.ACCEPTED),
                new OrderPeopleDto(1, OrderGuestStatus.DECLINED)
        );

        OrderDto order = new OrderDto(
                "order_id_1",
                "unique_order_id_1",
                restaurant,
                OrderStatus.FINISHED,
                items,
                people,
                new BigDecimal(13.0),
                new BigDecimal(12.5),
                new BigDecimal(40));

        return order;
    }





}


