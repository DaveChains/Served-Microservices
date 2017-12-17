package com.serveme.payment.api.dto.order;

import com.serveme.payment.enums.OrderStatus;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Davids-iMac on 29/05/16.
 */
public class OrderDto {

    @NotNull
    @NotEmpty
    private String id;

    private RestaurantOrderDto restaurant;

    /**
     * List of ordered items
     */
    private List<OrderItemDto> items;

    /**
     * Number of people to eat
     */
    private int tableFor;

    /**
     * People who will share the bill(at least the owner will be here)
     */
    private List<OrderPeopleDto> people;

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


    public OrderDto() {}

    public OrderDto(
            String id,
            String uniqueId,
            RestaurantOrderDto restaurant,
            OrderStatus status,
            List<OrderItemDto> items,
            List<OrderPeopleDto> people,
            BigDecimal subtotal,
            BigDecimal serviceChargePerc,
            BigDecimal discountPerc) {
        this.status = status;
        this.items = items;
        this.id = id;
        this.discountPerc = discountPerc;
        this.people = people;
        this.restaurant = restaurant;
        this.serviceChargePerc = serviceChargePerc;
        this.subtotal = subtotal;
        this.uniqueId = uniqueId;
    }

    public String getAcceptedAt() {
        return acceptedAt;
    }

    public BigDecimal getAmountCharged() {
        return amountCharged;
    }

    public String getArrivalAt() {
        return arrivalAt;
    }

    public String getCancelledAt() {
        return cancelledAt;
    }

    public String getChargeConfirmationNumber() {
        return chargeConfirmationNumber;
    }

    public String getComments() {
        return comments;
    }

    public String getDateOfCharge() {
        return dateOfCharge;
    }

    public String getDeclinedAt() {
        return declinedAt;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public BigDecimal getDiscountPerc() {
        return discountPerc;
    }

    public boolean isEmailSent() {
        return emailSent;
    }

    public String getFinishedAt() {
        return finishedAt;
    }

    public String getId() {
        return id;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public String getObservation() {
        return observation;
    }

    public boolean isOrderCharged() {
        return orderCharged;
    }

    public BigDecimal getOrderedAtRestaurant() {
        return orderedAtRestaurant;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public String getPaidAt() {
        return paidAt;
    }

    public boolean isPayServiceCharge() {
        return payServiceCharge;
    }

    public List<OrderPeopleDto> getPeople() {
        return people;
    }

    public String getRequestedAt() {
        return requestedAt;
    }

    public RestaurantOrderDto getRestaurant() {
        return restaurant;
    }

    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }

    public BigDecimal getServiceChargePerc() {
        return serviceChargePerc;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public int getTableFor() {
        return tableFor;
    }

    public boolean isTestMode() {
        return testMode;
    }

    public BigDecimal getTip() {
        return tip;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getUniqueId() {
        return uniqueId;
    }


    @Override
    public String toString() {
        return "OrderDto{" +
                "acceptedAt='" + acceptedAt + '\'' +
                ", id='" + id + '\'' +
                ", restaurant=" + restaurant +
                ", items=" + items +
                ", tableFor=" + tableFor +
                ", people=" + people +
                ", status=" + status +
                ", requestedAt='" + requestedAt + '\'' +
                ", arrivalAt='" + arrivalAt + '\'' +
                ", declinedAt='" + declinedAt + '\'' +
                ", cancelledAt='" + cancelledAt + '\'' +
                ", finishedAt='" + finishedAt + '\'' +
                ", paidAt='" + paidAt + '\'' +
                ", ownerId=" + ownerId +
                ", comments='" + comments + '\'' +
                ", serviceChargePerc=" + serviceChargePerc +
                ", serviceCharge=" + serviceCharge +
                ", subtotal=" + subtotal +
                ", total=" + total +
                ", tip=" + tip +
                ", payServiceCharge=" + payServiceCharge +
                ", emailSent=" + emailSent +
                ", orderCharged=" + orderCharged +
                ", amountCharged=" + amountCharged +
                ", dateOfCharge='" + dateOfCharge + '\'' +
                ", chargeConfirmationNumber='" + chargeConfirmationNumber + '\'' +
                ", discountPerc=" + discountPerc +
                ", discount=" + discount +
                ", orderedAtRestaurant=" + orderedAtRestaurant +
                ", testMode=" + testMode +
                ", uniqueId='" + uniqueId + '\'' +
                ", observation='" + observation + '\'' +
                '}';
    }
}
