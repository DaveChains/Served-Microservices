package com.serveme.service.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Invoice {
    private long id;
    private String orderId;
    private String orderUniqueId;
    private BigDecimal customerGrossAmount;
    private BigDecimal tip;
    private BigDecimal serviceChargePercentage;
    private BigDecimal serviceChargeApplied;
    private BigDecimal discountPercentage;
    private BigDecimal discountApplied;
    private BigDecimal customerNetAmount;
    private BigDecimal paymentGatewayFeePercentage;
    private BigDecimal paymentGatewayFeePercentageApplied;
    private BigDecimal paymentGatewayFeeFixedAmountUnit;
    private BigDecimal paymentGatewayFeeFixedAmountTotal;
    private BigDecimal paymentGatewayTotalFeeApplied;
    private BigDecimal servemeFeePercentage;
    private BigDecimal servemeFeePercentageApplied;
    private BigDecimal servemeFeeFixedAmountUnit;
    private BigDecimal servemeFeeFixedAmountTotal;
    private BigDecimal servemeTotalFeeApplied;
    private BigDecimal restaurantBenefit;
    private int peopleToPay;
    private boolean testMode;
    private RestaurantTab restaurantTab;
    private List<InvoiceLine> lines;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderUniqueId() {
		return orderUniqueId;
	}
	public void setOrderUniqueId(String orderUniqueId) {
		this.orderUniqueId = orderUniqueId;
	}
	public BigDecimal getCustomerGrossAmount() {
		return customerGrossAmount;
	}
	public void setCustomerGrossAmount(BigDecimal customerGrossAmount) {
		this.customerGrossAmount = customerGrossAmount;
	}
	public BigDecimal getTip() {
		return tip;
	}
	public void setTip(BigDecimal tip) {
		this.tip = tip;
	}
	public BigDecimal getServiceChargePercentage() {
		return serviceChargePercentage;
	}
	public void setServiceChargePercentage(BigDecimal serviceChargePercentage) {
		this.serviceChargePercentage = serviceChargePercentage;
	}
	public BigDecimal getServiceChargeApplied() {
		return serviceChargeApplied;
	}
	public void setServiceChargeApplied(BigDecimal serviceChargeApplied) {
		this.serviceChargeApplied = serviceChargeApplied;
	}
	public BigDecimal getDiscountPercentage() {
		return discountPercentage;
	}
	public void setDiscountPercentage(BigDecimal discountPercentage) {
		this.discountPercentage = discountPercentage;
	}
	public BigDecimal getDiscountApplied() {
		return discountApplied;
	}
	public void setDiscountApplied(BigDecimal discountApplied) {
		this.discountApplied = discountApplied;
	}
	public BigDecimal getCustomerNetAmount() {
		return customerNetAmount;
	}
	public void setCustomerNetAmount(BigDecimal customerNetAmount) {
		this.customerNetAmount = customerNetAmount;
	}
	public BigDecimal getPaymentGatewayFeePercentage() {
		return paymentGatewayFeePercentage;
	}
	public void setPaymentGatewayFeePercentage(BigDecimal paymentGatewayFeePercentage) {
		this.paymentGatewayFeePercentage = paymentGatewayFeePercentage;
	}
	public BigDecimal getPaymentGatewayFeePercentageApplied() {
		return paymentGatewayFeePercentageApplied;
	}
	public void setPaymentGatewayFeePercentageApplied(BigDecimal paymentGatewayFeePercentageApplied) {
		this.paymentGatewayFeePercentageApplied = paymentGatewayFeePercentageApplied;
	}
	public BigDecimal getPaymentGatewayFeeFixedAmountUnit() {
		return paymentGatewayFeeFixedAmountUnit;
	}
	public void setPaymentGatewayFeeFixedAmountUnit(BigDecimal paymentGatewayFeeFixedAmountUnit) {
		this.paymentGatewayFeeFixedAmountUnit = paymentGatewayFeeFixedAmountUnit;
	}
	public BigDecimal getPaymentGatewayFeeFixedAmountTotal() {
		return paymentGatewayFeeFixedAmountTotal;
	}
	public void setPaymentGatewayFeeFixedAmountTotal(BigDecimal paymentGatewayFeeFixedAmountTotal) {
		this.paymentGatewayFeeFixedAmountTotal = paymentGatewayFeeFixedAmountTotal;
	}
	public BigDecimal getPaymentGatewayTotalFeeApplied() {
		return paymentGatewayTotalFeeApplied;
	}
	public void setPaymentGatewayTotalFeeApplied(BigDecimal paymentGatewayTotalFeeApplied) {
		this.paymentGatewayTotalFeeApplied = paymentGatewayTotalFeeApplied;
	}
	public BigDecimal getServemeFeePercentage() {
		return servemeFeePercentage;
	}
	public void setServemeFeePercentage(BigDecimal servemeFeePercentage) {
		this.servemeFeePercentage = servemeFeePercentage;
	}
	public BigDecimal getServemeFeePercentageApplied() {
		return servemeFeePercentageApplied;
	}
	public void setServemeFeePercentageApplied(BigDecimal servemeFeePercentageApplied) {
		this.servemeFeePercentageApplied = servemeFeePercentageApplied;
	}
	public BigDecimal getServemeFeeFixedAmountUnit() {
		return servemeFeeFixedAmountUnit;
	}
	public void setServemeFeeFixedAmountUnit(BigDecimal servemeFeeFixedAmountUnit) {
		this.servemeFeeFixedAmountUnit = servemeFeeFixedAmountUnit;
	}
	public BigDecimal getServemeFeeFixedAmountTotal() {
		return servemeFeeFixedAmountTotal;
	}
	public void setServemeFeeFixedAmountTotal(BigDecimal servemeFeeFixedAmountTotal) {
		this.servemeFeeFixedAmountTotal = servemeFeeFixedAmountTotal;
	}
	public BigDecimal getServemeTotalFeeApplied() {
		return servemeTotalFeeApplied;
	}
	public void setServemeTotalFeeApplied(BigDecimal servemeTotalFeeApplied) {
		this.servemeTotalFeeApplied = servemeTotalFeeApplied;
	}
	public BigDecimal getRestaurantBenefit() {
		return restaurantBenefit;
	}
	public void setRestaurantBenefit(BigDecimal restaurantBenefit) {
		this.restaurantBenefit = restaurantBenefit;
	}
	public int getPeopleToPay() {
		return peopleToPay;
	}
	public void setPeopleToPay(int peopleToPay) {
		this.peopleToPay = peopleToPay;
	}
	public boolean isTestMode() {
		return testMode;
	}
	public void setTestMode(boolean testMode) {
		this.testMode = testMode;
	}
	public RestaurantTab getRestaurantTab() {
		return restaurantTab;
	}
	public void setRestaurantTab(RestaurantTab restaurantTab) {
		this.restaurantTab = restaurantTab;
	}
	public List<InvoiceLine> getLines() {
		return lines;
	}
	public void setLines(List<InvoiceLine> lines) {
		this.lines = lines;
	}
}
