package com.serveme.service.order.domain;

import com.serveme.service.order.enums.SplitBillInvitationStatus;

import java.io.Serializable;

/**
 * Created by Davids-iMac on 21/11/15.
 */
public class OrderPeople implements Serializable {

    private long userId;

    private SplitBillInvitationStatus status;

    private boolean found;

    private String invitedAt;

    private String acceptedAt;

    private String userName;

    private String userSurname;

    private String userPhoneNumber;

    private int rating = -1;

    private String review;

    private String reviewImprovement;


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public SplitBillInvitationStatus getStatus() {
        return status;
    }

    public void setStatus(SplitBillInvitationStatus status) {
        this.status = status;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public String getInvitedAt() {
        return invitedAt;
    }

    public void setInvitedAt(String invitedAt) {
        this.invitedAt = invitedAt;
    }

    public String getAcceptedAt() {
        return acceptedAt;
    }

    public void setAcceptedAt(String acceptedAt) {
        this.acceptedAt = acceptedAt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReviewImprovement() {
        return reviewImprovement;
    }

    public void setReviewImprovement(String reviewImprovement) {
        this.reviewImprovement = reviewImprovement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderPeople)) return false;

        OrderPeople that = (OrderPeople) o;

        return userId == that.userId;

    }

    @Override
    public int hashCode() {
        return (int) (userId ^ (userId >>> 32));
    }

    @Override
    public String toString() {
        return "OrderPeople{" +
                "userId=" + userId +
                ", status=" + status +
                ", found=" + found +
                ", invitedAt=" + invitedAt +
                ", acceptedAt=" + acceptedAt +
                ", userName='" + userName + '\'' +
                ", userSurname='" + userSurname + '\'' +
                ", userPhoneNumber='" + userPhoneNumber + '\'' +
                ", rating=" + rating +
                ", review='" + review + '\'' +
                ", reviewImprovement='" + reviewImprovement + '\'' +
                '}';
    }
}
