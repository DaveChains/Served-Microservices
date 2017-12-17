package com.serveme.service.domain;
import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RestaurantTab {

    private long id;
    private long restaurantId;
    private LocalDate startPeriodInclusive;
    private LocalDate endPeriodInclusive;
    private boolean transferred;
    private List<Invoice> invoices;
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(long restaurantId) {
		this.restaurantId = restaurantId;
	}
	public LocalDate getStartPeriodInclusive() {
		return startPeriodInclusive;
	}
	public void setStartPeriodInclusive(LocalDate startPeriodInclusive) {
		this.startPeriodInclusive = startPeriodInclusive;
	}
	public LocalDate getEndPeriodInclusive() {
		return endPeriodInclusive;
	}
	public void setEndPeriodInclusive(LocalDate endPeriodInclusive) {
		this.endPeriodInclusive = endPeriodInclusive;
	}
	public boolean isTransferred() {
		return transferred;
	}
	public void setTransferred(boolean transferred) {
		this.transferred = transferred;
	}
	public List<Invoice> getInvoices() {
		return invoices;
	}
	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}

}
