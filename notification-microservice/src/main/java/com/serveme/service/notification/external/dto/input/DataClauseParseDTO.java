package com.serveme.service.notification.external.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by DavidChains on 10/12/15.
 */
public class DataClauseParseDTO {

	private String orderId;
	private String orderState;
	@JsonProperty(value="content-available")
	private String avaibleContent;
	private String alert;
	private String sound;
	
	
	public String getSound() {
		return sound;
	}

	public void setSound(String sound) {
		this.sound = sound;
	}

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

	public String getAvaibleContent() {
		return "1";
	}

	public void setAvaibleContent(String avaibleContent) {
		this.avaibleContent = avaibleContent;
	}
	
	public DataClauseParseDTO() {
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderState() {
		return orderState;
	}
	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
	@Override
	public String toString() {
		return "DataClauseParseDTP [orderId=" + orderId + ", orderState=" + orderState + "]";
	}
	
	
}
