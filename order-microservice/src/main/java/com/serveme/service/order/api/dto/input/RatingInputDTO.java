package com.serveme.service.order.api.dto.input;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Davids-iMac on 08/11/15.
 */
public class RatingInputDTO implements Serializable {

    private String orderId;

    private int rating;

    private String comments;
    
    private BigDecimal tip;
    
    private boolean payServiceCharge;

    private String improvement;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getImprovement() {
        return improvement;
    }

    public void setImprovement(String improvement) {
        this.improvement = improvement;
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

	@Override
    public String toString() {
        return "RatingInputDTO{" +
                "orderId=" + orderId +
                ", rating=" + rating +
                ", comments='" + comments + '\'' +
                ", improvement='" + improvement + '\'' +
                '}';
    }
}
