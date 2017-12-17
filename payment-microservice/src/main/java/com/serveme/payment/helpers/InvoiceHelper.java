package com.serveme.payment.helpers;

import com.serveme.payment.api.dto.order.ExtraDto;
import com.serveme.payment.api.dto.order.OrderDto;
import com.serveme.payment.api.dto.order.OrderItemDto;
import com.serveme.payment.domain.*;
import com.serveme.payment.enums.ChargeType;
import com.serveme.payment.enums.PaymentProviderId;
import com.stripe.model.Charge;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


public class InvoiceHelper {

    public static CustomerCharge generateCustomerOrderCharge(
            BigDecimal amountPeerHead,
            Invoice invoice,
            PaymentDetail stripeCustomer,
            PaymentProviderId paymentProvider) {
        return generateCustomerCharge(
                ChargeType.ORDER,
                amountPeerHead,
                invoice,
                stripeCustomer,
                paymentProvider
        );
    }


    public static CustomerCharge generateCustomerCharge(
            ChargeType chargeType,
            BigDecimal amountPeerHead,
            Invoice invoice,
            PaymentDetail stripeCustomer,
            PaymentProviderId paymentProvider) {
        return new CustomerCharge(
                invoice,
                chargeType,
                stripeCustomer.getUserId(),
                String.format("%d-%s", stripeCustomer.getUserId(), invoice.getOrderId()),
                amountPeerHead,
                invoice.isTestMode(),
                paymentProvider
        );
    }



    public static InvoiceLine generateInvoiceLineFromItem(Invoice invoice, OrderItemDto item) {

        StringBuilder conceptBuilder = new StringBuilder(item.getDishName());
        BigDecimal individualAmount = new BigDecimal(0);
        if(item.getExtras() != null && !item.getExtras().isEmpty()) {
            //Concept
            List<String> extrasList = item.getExtras()
                    .stream()
                    .map(ExtraDto::getValue)
                    .collect(Collectors.toList());
            conceptBuilder
                    .append("(")
                    .append(String.join(", ", extrasList))
                    .append(")");
            //Price
            for(ExtraDto e: item.getExtras()) {
                individualAmount = individualAmount.add(e.getPrice());
            }
        }
        individualAmount = individualAmount.add(item.getDishPrice());

        return new InvoiceLine(
                item.getDishId(),
                conceptBuilder.toString(),
                invoice,
                item.getNum(),
                CalcHelper.toCents(individualAmount),
                CalcHelper.toCents(individualAmount.multiply(new BigDecimal(item.getNum())))
        );
    }

}
