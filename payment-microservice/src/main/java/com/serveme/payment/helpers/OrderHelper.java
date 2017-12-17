package com.serveme.payment.helpers;

import com.serveme.payment.api.dto.order.OrderDto;
import com.serveme.payment.api.dto.order.OrderPeopleDto;
import com.serveme.payment.enums.OrderGuestStatus;
import com.serveme.payment.enums.OrderStatus;
import com.serveme.payment.exceptions.OrderNoBillableException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


public class OrderHelper {

    //TODO order status should be lineal(One status can't be achieved from two status).
    // It shouldn't be CLIENT_REVIEWED

    public static void validateOrder(OrderDto order) {
        if(order == null ||
                (!OrderStatus.FINISHED.equals(order.getStatus()) &&
                 !OrderStatus.CLIENT_REVIEWED.equals(order.getStatus()))){
            throw new OrderNoBillableException(
                    (order != null)? order.getId() : "NULL",
                    (order != null && order.getPeople() != null)? order.getPeople().size() : -1);
        }
    }


    public static List<Long> getGuestIdsFromOrder(OrderDto order) {
        return order.getPeople()
                    .stream()
                    .filter(p -> p.getStatus().equals(OrderGuestStatus.ACCEPTED.toString()))
                    .map(OrderPeopleDto::getUserId)
                    .collect(Collectors.toList());
    }


    public static BigDecimal getTotalCustomerSpent(
            BigDecimal subtotal,
            BigDecimal tip,
            BigDecimal serviceChargePercentage,
            BigDecimal discountPercentage

    ) {
        //todo what if we don't trust the subtotal sent and we calculate it here from items?
        BigDecimal result =  subtotal;
        if(tip != null){
            result = result.add(tip);
        }

        if(serviceChargePercentage != null){
            result = result.add(CalcHelper.percentage(
                    subtotal,
                    serviceChargePercentage));
        }

        if(discountPercentage != null){
            result = result.subtract(CalcHelper.percentage(
                    subtotal,
                    discountPercentage));
        }
        return result.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

//    public static BigDecimal getDiscountApplied(OrderDto order) {
//        return CalcHelper.percentage(
//                order.getSubtotal(),
//                order.getDiscountPerc());
//    }

//    public static BigDecimal getServiceChargeApplied(OrderDto order) {
//        return CalcHelper.percentage(
//                order.getSubtotal(),
//                order.getServiceChargePerc());
//    }


    public static BigDecimal getTotalCustomerSpentPeerHead(final OrderDto order) {

        if(order.getPeople()!= null && order.getPeople().size() > 0) {
            final BigDecimal people = new BigDecimal(order.getPeople().size());
            return getTotalCustomerSpent(
                    order.getSubtotal(),
                    order.getTip(),
                    order.getServiceChargePerc(),
                    order.getDiscountPerc()
            ).divide(people, 3, BigDecimal.ROUND_HALF_UP);
        } else {
            return BigDecimal.ZERO;
        }

    }

    public static BigDecimal getSubtotal(OrderDto order) {
        return order.getSubtotal();
    }
}
