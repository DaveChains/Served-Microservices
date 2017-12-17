package com.serveme.service.notification.external.dto.output;

import java.io.Serializable;

/**
 * Created by DavidChains on 10/12/15.
 */
public class ResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean result;

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "ResponseDTO [result=" + result + "]";
	}
	

}
