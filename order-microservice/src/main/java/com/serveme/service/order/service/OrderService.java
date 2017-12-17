package com.serveme.service.order.service;

import com.serveme.service.order.api.dto.input.CreationOrderInputDTO;
import com.serveme.service.order.domain.Invoice;
import com.serveme.service.order.domain.Order;
import com.serveme.service.order.external.dto.output.DevicesDTO;
import com.serveme.service.order.external.dto.output.UserExDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by Davids-iMac on 21/11/15.
 */
public interface OrderService {

    Order getUserCurrentOrder(long userId);

    Order createOrder(UserExDTO user, long restaurantId, CreationOrderInputDTO orderInputDTO, String accessToken);

    Map<String, List> splitBill(Order order, List<String> phones);

    Order getById(String id, long userId);

    long countUserHistory(long userId);

    List<Order> getUserHistory(long userId, int from, int limit);

    void rateOrder(long userId, String orderId, int rating, String comments, String improvement);

    long countRestaurantHistory(long restaurantId);

    long countRestaurantPending(long restaurantId);

    long countRestaurantAccepted(long restaurantId);

    long countRestaurantActive(long restaurantId);

    long countRestaurantFinished(long restaurantId, long startOfDay);

    List<Order> getRestaurantHistory(long restaurantId, int from, int size);

    List<Order> getRestaurantPendingOrders(long restaurantId, int from, int size);

    List<Order> getRestaurantAcceptedOrders(long restaurantId, int from, int size);

    List<Order> getRestaurantActiveOrders(long restaurantId, int from, int size);

    List<Order> getRestaurantFinishedOrders(long restaurantId, int from, int size, long startOfDay);

    List<Order> getRestaurantFinishedOrders(long restaurantId, long initDate, long endDate);

    List<Order> getAllHistory(int from, int size);

    List<Order> getAllPendingOrders(int from, int size);

    List<Order> getAllAcceptedOrders(int from, int size);

    List<Order> getAllActiveOrders(int from, int size);

    List<Invoice> getInvoices(long restaurantId, int from, int limit);

    boolean acceptOrder(String orderId, long restaurantId);

    boolean declineOrder(String orderId, long restaurantId);

    boolean cancelOrder(String orderId, long restaurantId);

    boolean finishOrder(String orderId, long restaurantId, BigDecimal orderedAtRestaurant);

    Order getById(String id);

    void updateTipAndPayServiceCharge(String orderId, BigDecimal tip, boolean payServiceCharge);

    List<Order> getNotCharged(int max);

    List<List<Long>> getNumberOfOrdersByRestaurant(long userId, List<Long> resraurantIds);

    void invoiceFinishedOrders(int bulkSize);

    void setInvoiceToTransferred(String invoiceId);

    void saveCharge(Order order, BigDecimal amount, String confirmation);

    void rememberOrder(DevicesDTO devices, Order order, int minutesRemainingToBookingTime);

    void autocancelOrderIfNoResponseFromRestaurant(DevicesDTO devices, DevicesDTO restaurantDevice, DevicesDTO adminDevices, Order order);

    //void sendEmailsToFinishedOrders();

    //Test methods
    boolean acceptOrder(String orderId);

    boolean declineOrder(String orderId);

    boolean cancelOrder(String orderId);

    boolean finishOrder(String orderId, BigDecimal orderedAtRestaurant);

    void generateInvoicesForAllRestaurants();
}
