package com.serveme.payment.api.dto.order;

import com.serveme.payment.enums.OrderGuestStatus;

import java.io.Serializable;

/**
 * Created by Davids-iMac on 21/11/15.
 */
public class OrderPeopleDto implements Serializable {

    private long userId;

    private String status;

    private boolean found;

    private String invitedAt;

    private String acceptedAt;

    private String userName;

    private String userSurname;

    private String userPhoneNumber;

    private int rating = -1;

    private String review;

    private String reviewImprovement;

    public OrderPeopleDto() {}

    public OrderPeopleDto(
            long userId,
            OrderGuestStatus status) {
        this.status = status.toString();
        this.userId = userId;
    }

    public String getAcceptedAt() {
        return acceptedAt;
    }

    public boolean isFound() {
        return found;
    }

    public String getInvitedAt() {
        return invitedAt;
    }

    public int getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }

    public String getReviewImprovement() {
        return reviewImprovement;
    }

    public String getStatus() {
        return status;
    }

    public long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public String getUserSurname() {
        return userSurname;
    }


    public void setAcceptedAt(String acceptedAt) {
        this.acceptedAt = acceptedAt;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public void setInvitedAt(String invitedAt) {
        this.invitedAt = invitedAt;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setReviewImprovement(String reviewImprovement) {
        this.reviewImprovement = reviewImprovement;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    @Override
    public String toString() {
        return "OrderPeopleDto{" +
                "acceptedAt='" + acceptedAt + '\'' +
                ", found=" + found +
                ", invitedAt='" + invitedAt + '\'' +
                ", rating=" + rating +
                ", review='" + review + '\'' +
                ", reviewImprovement='" + reviewImprovement + '\'' +
                ", status='" + status + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPhoneNumber='" + userPhoneNumber + '\'' +
                ", userSurname='" + userSurname + '\'' +
                '}';
    }
}
