package com.serveme.payment.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity(name = "INVOICE_LINES")
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"dishId","invoice_id"})},
        indexes = {
                @Index(name = "index_order", columnList = "invoice_id")})
public class InvoiceLine {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    /**
     * Dish id
     */
    @Column(name = "dishId", nullable = false)
    @Min(0)
    private long dishId;

    /**
     * Describe the line(dish plus extras)
     */
    @Column(name = "concept", nullable = false)
    @NotNull
    @NotEmpty
    private String concept;

    /**
     * Number of dishes
     */
    @Column(name = "num", nullable = false)
    @Min(0)
    private int num;

    /**
     * This amount covers extras too
     */
    @Column(name = "individualAmount", nullable = false)
    @Min(0)
    private int individualAmount;

    /**
     * This amount covers extras too
     */
    @Column(name = "totalAmount", nullable = false)
    @Min(0)
    private int totalAmount;


    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    public InvoiceLine(){}

    public InvoiceLine(
            long dishId,
            String concept,
            Invoice invoice,
            int num,
            int individualAmount,
            int totalAmount) {
        this.concept = concept;
        this.dishId = dishId;
        this.individualAmount = individualAmount;
        this.invoice = invoice;
        this.num = num;
        this.totalAmount = totalAmount;
    }

    public String getConcept() {
        return concept;
    }

    public long getDishId() {
        return dishId;
    }

    public long getId() {
        return id;
    }

    public int getIndividualAmount() {
        return individualAmount;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public int getNum() {
        return num;
    }

    public int getTotalAmount() {
        return totalAmount;
    }


    @Override
    public String toString() {
        return "InvoiceLineCustomer{" +
                "concept='" + concept + '\'' +
                ", id=" + id +
                ", dishId=" + dishId +
                ", num=" + num +
                ", individualAmount=" + individualAmount +
                ", totalAmount=" + totalAmount +
                ", invoiceCustomer=" + invoice +
                '}';
    }
}
