package com.serveme.service.order.domain;

import com.serveme.service.order.enums.OrderStatus;
import com.serveme.service.order.util.persistence.mongo.BaseEntity;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davids-iMac on 18/11/15.
 */
@Entity(name = "orders")
public class Order extends BaseEntity {

    /**
     * Restaurant's information
     */
    private RestaurantOrder restaurant;

    /**
     * List of ordered items
     */
    private List<OrderItem> items;

    /**
     * Number of people to eat
     */
    private int tableFor;

    /**
     * People who will share the bill(at least the owner will be here)
     */
    private List<OrderPeople> people;

    /**
     * Order status: Pending, Accepted, Declined(By th restaurant), Cancelled(by the user), Finished
     */
    private OrderStatus status;

    /**
     * when the user made the request
     */
    private String requestedAt;

    /**
     * If the order has been accepted, when it was
     * The order could have been accepted once but cancelled later
     */
    private String acceptedAt;

    /**
     * When the user should be at the restaurant
     */
    private String arrivalAt;

    /**
     * If the order is declined by the restaurant, when the response took place
     */
    private String declinedAt;

    /**
     * If the order is cancelled by the user, when the response took place
     */
    private String cancelledAt;

    /**
     * If the order is accepted and everything is normal, eventually the order has to be finished by the restaurant.
     * So this field indicates when that happens
     */
    private String finishedAt;

    /**
     * Moment when the payment was delivered to the restaurant
     */
    private String paidAt;

    /**
     * User who created the order(within the split bill invited people)
     */
    private long ownerId;

    /**
     * General comments about the order
     */
    private String comments;

    /**
     * Service charge percentage used by restaurant at the moment of finishing
     */
    private BigDecimal serviceChargePerc;

    /**
     * Service charge amount
     */
    private BigDecimal serviceCharge;

    /**
     * Subtotal: all dishes added up without fees, taxes or charges
     */
    private BigDecimal subtotal;

    /**
     * Total: total amount users have to pay (includes fees, taxes and charges)
     */
    private BigDecimal total;

    /**
     * Tip: total tip of the order
     */
    private BigDecimal tip;

    /**
     * PayServiceCharge: if the user wants to pay the service charge
     */
    private boolean payServiceCharge;

    /**
     * PayServiceCharge: if the email has been sent when the order is finished
     */
    private boolean emailSent;

    /**
     * orderCharged: if the payment has been charged
     */
    private boolean orderCharged;

    /**
     * orderCharged: the amount that was charged
     */
    private BigDecimal amountCharged;

    /**
     * dateOfCharge: the date of the charge
     */
    private String dateOfCharge;

    /**
     * orderCharged: charge identification or confirmation number
     */
    private String chargeConfirmationNumber;

    private BigDecimal discountPerc;

    private BigDecimal discount;

    private BigDecimal orderedAtRestaurant;

    private boolean testMode;

    private String uniqueId;

    private String observation;

    /**
     * Flag for payment synchronization
     */
    private boolean paymentSync = false;

    private Location originLocation;

    public static List<Order> removeTestOrders(List<Order> orders) {
        List<Order> list = new ArrayList<Order>();
        if (orders != null) {
            for (Order order : orders) {
                if (!order.isTestMode()) {
                    list.add(order);
                }
            }
        }
        return list;
    }


    public boolean isPaymentSync() {
        return paymentSync;
    }

    public void setPaymentSync(boolean paymentSync) {
        this.paymentSync = paymentSync;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(String requestedAt) {
        this.requestedAt = requestedAt;
    }

    public String getAcceptedAt() {
        return acceptedAt;
    }

    public void setAcceptedAt(String acceptedAt) {
        this.acceptedAt = acceptedAt;
    }

    public String getDeclinedAt() {
        return declinedAt;
    }

    public void setDeclinedAt(String declinedAt) {
        this.declinedAt = declinedAt;
    }

    public String getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(String cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    public String getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(String finishedAt) {
        this.finishedAt = finishedAt;
    }

    public String getArrivalAt() {
        return arrivalAt;
    }

    public void setArrivalAt(String arrivalAt) {
        this.arrivalAt = arrivalAt;
    }

    public String getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(String paidAt) {
        this.paidAt = paidAt;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public List<OrderPeople> getPeople() {
        return people;
    }

    public void setPeople(List<OrderPeople> people) {
        this.people = people;
    }

    public RestaurantOrder getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantOrder restaurant) {
        this.restaurant = restaurant;
    }

    public int getTableFor() {
        return tableFor;
    }

    public void setTableFor(int tableFor) {
        this.tableFor = tableFor;
    }

    public BigDecimal getServiceChargePerc() {
        return serviceChargePerc;
    }

    public void setServiceChargePerc(BigDecimal serviceChargePerc) {
        this.serviceChargePerc = serviceChargePerc;
    }

    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(BigDecimal serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTip() {
        return tip;
    }

    public void setTip(BigDecimal tip) {
        this.tip = tip;
    }

    public boolean isPayServiceCharge() {
        return payServiceCharge;
    }

    public void setPayServiceCharge(boolean payServiceCharge) {
        this.payServiceCharge = payServiceCharge;
    }

    public boolean isEmailSent() {
        return emailSent;
    }

    public void setEmailSent(boolean emailSent) {
        this.emailSent = emailSent;
    }

    public boolean isOrderCharged() {
        return orderCharged;
    }

    public void setOrderCharged(boolean orderCharged) {
        this.orderCharged = orderCharged;
    }

    public BigDecimal getAmountCharged() {
        return amountCharged;
    }

    public void setAmountCharged(BigDecimal amountCharged) {
        this.amountCharged = amountCharged;
    }

    public String getDateOfCharge() {
        return dateOfCharge;
    }

    public void setDateOfCharge(String dateOfCharge) {
        this.dateOfCharge = dateOfCharge;
    }

    public String getChargeConfirmationNumber() {
        return chargeConfirmationNumber;
    }

    public void setChargeConfirmationNumber(String chargeConfirmationNumber) {
        this.chargeConfirmationNumber = chargeConfirmationNumber;
    }

    public BigDecimal getDiscountPerc() {
        return discountPerc;
    }

    public void setDiscountPerc(BigDecimal discountPerc) {
        this.discountPerc = discountPerc;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getOrderedAtRestaurant() {
        return orderedAtRestaurant;
    }

    public void setOrderedAtRestaurant(BigDecimal orderedAtRestaurant) {
        this.orderedAtRestaurant = orderedAtRestaurant;
    }

    public boolean isTestMode() {
        return testMode;
    }

    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    @Override
    public String toString() {
        return "Order{" +
                "acceptedAt=" + acceptedAt +
                ", restaurant=" + restaurant +
                ", items=" + items +
                ", tableFor=" + tableFor +
                ", people=" + people +
                ", status=" + status +
                ", requestedAt=" + requestedAt +
                ", arrivalAt=" + arrivalAt +
                ", declinedAt=" + declinedAt +
                ", cancelledAt=" + cancelledAt +
                ", finishedAt=" + finishedAt +
                ", paidAt=" + paidAt +
                ", ownerId=" + ownerId +
                ", serviceChargePerc=" + serviceChargePerc +
                ", serviceCharge=" + serviceCharge +
                ", subtotal=" + subtotal +
                ", total=" + total +
                ", comments='" + comments + '\'' +
                '}';
    }

    public Location getOriginLocation() {
        return originLocation;
    }

    public void setOriginLocation(Location originLocation) {
        this.originLocation = originLocation;
    }
}
