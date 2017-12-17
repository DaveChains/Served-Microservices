package com.serveme.service.order.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;

import com.serveme.service.order.util.persistence.mongo.BaseEntity;

/**
 * Created by DavidChains on 3/27/2016.
 */
@Entity(name = "invoices_line")
public class InvoiceLine  extends BaseEntity {
	
	private String orderId;
	private BigDecimal total;
	private BigDecimal serviceTotal;
	private BigDecimal tipTotal;
	private String date;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public BigDecimal getServiceTotal() {
		return serviceTotal;
	}
	public void setServiceTotal(BigDecimal serviceTotal) {
		this.serviceTotal = serviceTotal;
	}
	public BigDecimal getTipTotal() {
		return tipTotal;
	}
	public void setTipTotal(BigDecimal tipTotal) {
		this.tipTotal = tipTotal;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result + ((serviceTotal == null) ? 0 : serviceTotal.hashCode());
		result = prime * result + ((tipTotal == null) ? 0 : tipTotal.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		InvoiceLine other = (InvoiceLine) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		if (serviceTotal == null) {
			if (other.serviceTotal != null)
				return false;
		} else if (!serviceTotal.equals(other.serviceTotal))
			return false;
		if (tipTotal == null) {
			if (other.tipTotal != null)
				return false;
		} else if (!tipTotal.equals(other.tipTotal))
			return false;
		if (total == null) {
			if (other.total != null)
				return false;
		} else if (!total.equals(other.total))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "InvoiceLine [orderId=" + orderId + ", total=" + total + ", serviceTotal=" + serviceTotal + ", tipTotal="
				+ tipTotal + ", date=" + date + "]";
	}
}
