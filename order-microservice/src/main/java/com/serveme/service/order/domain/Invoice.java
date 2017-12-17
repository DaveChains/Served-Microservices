package com.serveme.service.order.domain;

import com.serveme.service.order.util.persistence.mongo.BaseEntity;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by DavidChains on 3/27/2016.
 */
@Entity(name = "invoices")
public class Invoice extends BaseEntity {

    private String generationDate;

    private String initDate;

    private String endDate;

    private BigDecimal totalService;

    private boolean transferred;

    private BigDecimal totalTips;

    private BigDecimal total;

    private int totalOrders;

    private long restaurantId;

    private List<InvoiceLine> invoiceItems;

    public String getGenerationDate() {
        return generationDate;
    }

    public void setGenerationDate(String generationDate) {
        this.generationDate = generationDate;
    }

    public String getInitDate() {
        return initDate;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getTotalService() {
        return totalService;
    }

    public void setTotalService(BigDecimal totalService) {
        this.totalService = totalService;
    }

    public boolean isTransferred() {
        return transferred;
    }

    public void setTransferred(boolean transferred) {
        this.transferred = transferred;
    }

    public BigDecimal getTotalTips() {
        return totalTips;
    }

    public void setTotalTips(BigDecimal totalTips) {
        this.totalTips = totalTips;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public List<InvoiceLine> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<InvoiceLine> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
        result = prime * result + ((generationDate == null) ? 0 : generationDate.hashCode());
        result = prime * result + ((initDate == null) ? 0 : initDate.hashCode());
        result = prime * result + (int) (restaurantId ^ (restaurantId >>> 32));
        result = prime * result + totalOrders;
        result = prime * result + (transferred ? 1231 : 1237);
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
        Invoice other = (Invoice) obj;
        if (endDate == null) {
            if (other.endDate != null)
                return false;
        } else if (!endDate.equals(other.endDate))
            return false;
        if (generationDate == null) {
            if (other.generationDate != null)
                return false;
        } else if (!generationDate.equals(other.generationDate))
            return false;
        if (initDate == null) {
            if (other.initDate != null)
                return false;
        } else if (!initDate.equals(other.initDate))
            return false;
        if (restaurantId != other.restaurantId)
            return false;
        if (totalOrders != other.totalOrders)
            return false;
        if (transferred != other.transferred)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Invoice [generationDate=" + generationDate + ", initDate=" + initDate + ", endDate=" + endDate
                + ", transferred=" + transferred + ", totalTips=" + totalTips + ", totalOrders=" + totalOrders
                + ", restaurantId=" + restaurantId + ", invoiceItems=" + invoiceItems + "]";
    }

}
