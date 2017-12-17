package com.serveme.payment.domain;

import com.serveme.payment.enums.ChargeStatus;
import com.serveme.payment.enums.ChargeType;
import com.serveme.payment.enums.PaymentProviderId;
import com.serveme.payment.payment.ChargeResultWrapper;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;

@Entity(name = "CUSTOMER_CHARGES")
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id","invoice_id"})},
        indexes = {
                @Index(name = "index_user", columnList = "user_id"),
                @Index(name = "index_order", columnList = "invoice_id"),
                @Index(name = "index_created", columnList = "created_at"),
                @Index(name = "index_status", columnList = "status")})
public class CustomerCharge {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @NotNull
    @NotEmpty
    @Column(name = "idempotent_transaction_id", nullable = false)
    private String idempotentTransactionId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private long userId;

    @NotNull
    @Min(0)
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "status", nullable = false)
    private ChargeStatus status;

    @Column(name = "payment_provider", nullable = false)
    private PaymentProviderId paymentProviderId;

    @Column(name = "charge_type", nullable = false)
    private ChargeType chargeType;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "charge_result", columnDefinition = "TEXT")
    private ChargeResultWrapper chargeResult;

    @Column(name = "charge_error", columnDefinition = "TEXT")
    private String chargeError;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    @Column(name = "test_mode")
    private boolean testMode;

    @Column(name = "tries")
    private int tries = 0;


    public CustomerCharge(){
    }


    public CustomerCharge(
            Invoice invoice,
            ChargeType chargeType,
            long userId,
            String idempotentTransactionId,
            BigDecimal amount,
            boolean testMode,
            PaymentProviderId paymentProviderId) {
        this(
                invoice,
                chargeType,
                userId,
                idempotentTransactionId,
                amount,
                testMode,
                paymentProviderId,
                null);
    }

    public CustomerCharge(
            Invoice invoice,
            ChargeType chargeType,
            long userId,
            String idempotentTransactionId,
            BigDecimal amount,
            boolean testMode,
            PaymentProviderId paymentProviderId,
            String notes) {

        this.chargeType = chargeType;
        this.status = ChargeStatus.PENDING;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.amount = amount;
        this.invoice = invoice;
        this.idempotentTransactionId = idempotentTransactionId;
        this.userId = userId;
        this.testMode = testMode;
        this.paymentProviderId = paymentProviderId;
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }

    public int getTries() {
        return tries;
    }

    public void setTries(int tries) {
        this.tries = tries;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public ChargeResultWrapper getChargeResult() {
        return chargeResult;
    }

    public String getChargeError() {
        return chargeError;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public long getId() {
        return id;
    }

    public String getIdempotentTransactionId() {
        return idempotentTransactionId;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public ChargeStatus getStatus() {
        return status;
    }

    public boolean isTestMode() {
        return testMode;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public long getUserId() {
        return userId;
    }

    public ChargeType getChargeType() {
        return chargeType;
    }



    public void setChargeResult(ChargeResultWrapper chargeResult) {
        this.chargeResult = chargeResult;
    }

    public void setChargeError(String chargeError) {
        this.chargeError = chargeError;
    }

    public void setIdempotentTransactionId(String idempotentTransactionId) {
        this.idempotentTransactionId = idempotentTransactionId;
    }

    public void setStatus(ChargeStatus status) {
        this.status = status;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }


    public PaymentProviderId getPaymentProviderId() {
        return paymentProviderId;
    }

    @Override
    public String toString() {
        return "CustomerCharge{" +
                "amount=" + amount +
                ", id=" + id +
                ", idempotentTransactionId='" + idempotentTransactionId + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", userId=" + userId +
                ", status=" + status +
                ", paymentProviderId=" + paymentProviderId +
                ", chargeResult='" + chargeResult + '\'' +
                ", chargeError='" + chargeError + '\'' +
                ", invoice=" + invoice +
                ", testMode=" + testMode +
                '}';
    }
}
