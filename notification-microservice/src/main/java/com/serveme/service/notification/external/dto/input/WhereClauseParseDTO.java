package com.serveme.service.notification.external.dto.input;


/**
 * Created by DavidChains on 10/12/15.
 */
public class WhereClauseParseDTO {
	
	private String deviceToken;

	public WhereClauseParseDTO() {
	}
	
	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	@Override
	public String toString() {
		return "WhereClauseParseDTP [deviceToken=" + deviceToken + "]";
	}
	
}
