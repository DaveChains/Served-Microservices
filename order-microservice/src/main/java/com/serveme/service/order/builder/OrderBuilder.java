package com.serveme.service.order.builder;

import com.serveme.service.order.domain.Order;
import com.serveme.service.order.domain.OrderItem;
import com.serveme.service.order.domain.OrderPeople;
import com.serveme.service.order.domain.RestaurantOrder;
import com.serveme.service.order.enums.OrderStatus;
import com.serveme.service.order.external.dto.output.UserExDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Davids-iMac on 21/11/15.
 */
public class OrderBuilder {

    private UserExDTO owner;

    private long arrivalTime;

    private int tableFor = 1;

    private RestaurantOrder restaurant;

    private List<OrderItem> items;

    private List<OrderPeople> splitBillInvitations;

    private String comments;

    private String observation;

    public OrderBuilder setRestaurant(RestaurantOrder restaurant) {
        this.restaurant = restaurant;
        return this;
    }

    public OrderBuilder setItems(List<OrderItem> items) {
        this.items = items;
        return this;
    }

    public OrderBuilder setSplitBillInvitations(List<OrderPeople> splitBillInvitations) {
        this.splitBillInvitations = splitBillInvitations;
        return this;

    }

    public OrderBuilder setArrivalTime(long arrivalMilli) {
        this.arrivalTime = arrivalMilli;
        return this;
    }

    public OrderBuilder setTableFor(int tableFor) {
        this.tableFor = tableFor;
        return this;
    }

    public OrderBuilder setOwner(UserExDTO owner) {
        this.owner = owner;
        return this;
    }

    public OrderBuilder setComments(String comments) {
        this.comments = comments;
        return this;
    }

    public OrderBuilder setObservation(String observation) {
        this.observation = observation;
        return this;
    }

    public Order build() {
        Order order = new Order();
        order.setRestaurant(restaurant);
        order.setItems(items);
        order.setPeople(splitBillInvitations);
        order.setStatus(OrderStatus.PENDING);
        order.setRequestedAt(String.valueOf(System.currentTimeMillis()));
        order.setArrivalAt(String.valueOf(arrivalTime));
        order.setTableFor(tableFor);
        order.setOwnerId(owner.getId());
        order.setComments(comments);
        order.setServiceChargePerc(restaurant.getServiceChargePerc());
        order.setTotal(calcCurrentTotal(items));
        order.setObservation(observation);
        return order;
    }

    private BigDecimal calcCurrentTotal(List<OrderItem> items) {
        BigDecimal total = new BigDecimal(0.0);
        for (OrderItem item : items) {
            total = total.add(item.getDishPrice().multiply(new BigDecimal(item.getNum())));
        }
        return total;
    }
}
