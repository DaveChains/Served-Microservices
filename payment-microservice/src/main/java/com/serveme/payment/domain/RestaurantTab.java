package com.serveme.payment.domain;


import com.serveme.payment.persistence.converters.LocalDateJpaConverter;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "RESTAURANT_TABS")
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id","start_period_inclusive"})},
        indexes = {
        @Index(name = "index_restaurant_id", columnList = "restaurant_id"),
        @Index(name = "index_start_period", columnList = "start_period_inclusive"),
        @Index(name = "index_end_period", columnList = "end_period_exclusive")})
public class RestaurantTab {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name = "restaurant_id", nullable = false)
    private long restaurantId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "start_period_inclusive")
    @Convert(converter = LocalDateJpaConverter.class)
    private LocalDate startPeriodInclusive;

    @Column(name = "end_period_exclusive")
    private LocalDate endPeriodInclusive;

    @Column(name = "transferred")
    private boolean transferred;


    @OneToMany(mappedBy = "restaurantTab", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Invoice> invoices;



    public RestaurantTab() {}


    public RestaurantTab(
            long restaurantId,
            LocalDate startPeriodInclusive,
            LocalDate endPeriodInclusive) {
        this.createdAt = Instant.now();
        this.endPeriodInclusive = endPeriodInclusive;
        this.restaurantId = restaurantId;
        this.startPeriodInclusive = startPeriodInclusive;
        this.updatedAt = Instant.now();
    }


    public RestaurantTab(
            long restaurantId,
            LocalDate startPeriodInclusive,
            LocalDate endPeriodInclusive,
            List<Invoice> invoices) {
        this(restaurantId, startPeriodInclusive, endPeriodInclusive);
        this.invoices = invoices;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getEndPeriodInclusive() {
        return endPeriodInclusive;
    }

    public void setEndPeriodInclusive(LocalDate endPeriodInclusive) {
        this.endPeriodInclusive = endPeriodInclusive;
    }

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

    public boolean isTransferred() {
        return transferred;
    }

    public void setTransferred(boolean transferred) {
        this.transferred = transferred;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }


    @Override
    public String toString() {
        return "RestaurantTab{" +
                "createdAt=" + createdAt +
                ", id=" + id +
                ", restaurantId=" + restaurantId +
                ", updatedAt=" + updatedAt +
                ", startPeriodInclusive=" + startPeriodInclusive +
                ", endPeriodInclusive=" + endPeriodInclusive +
                ", transferred=" + transferred +
                ", invoices=" + invoices +
                '}';
    }
}
