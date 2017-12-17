package com.serveme.payment.domain;

import com.serveme.payment.enums.InvoicingResult;
import com.serveme.payment.helpers.CalcHelper;
import com.serveme.payment.helpers.OrderHelper;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Entity(name = "INVOICES")
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"order_id"})},
        indexes = {
                @Index(name = "index_order_id", columnList = "order_id"),
                @Index(name = "index_created", columnList = "created_at")})
public class Invoice {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(name = "order_unique_id")
    private String orderUniqueId;

    @Column(name = "invoicing_result")
    private InvoicingResult invoicingResult;

    //MONEY
    /**
     * Amount before sc, discount, etc.
     */
    @Column(name = "customer_gross_amount", nullable = false)
    private BigDecimal customerGrossAmount;

    /**
     * Tips
     */
    @Column(name = "tip")
    private BigDecimal tip;

    /**
     * Service charge percentage in xx.xx format
     */
    @Column(name = "service_charge_percentage", nullable = false)
    private BigDecimal serviceChargePercentage;

    /**
     * The result of applying the service charge to the total amount
     */
    @Column(name = "service_charge_applied", nullable = false)
    private BigDecimal serviceChargeApplied;

    /**
     * Discount percentage to be applied
     */
    @Column(name = "discount_percentage", nullable = false)
    private BigDecimal discountPercentage;

    /**
     * The result of applying the discount to the total amount
     */
    @Column(name = "discount_applied", nullable = false)
    private BigDecimal discountApplied;

    /**
     * Amount after applying sc, discount, etc.
     */
    @Column(name = "customer_net_amount", nullable = false)
    private BigDecimal customerNetAmount;


    //Fees
    @Column(name = "payment_gateway_fee_percentage")
    private BigDecimal paymentGatewayFeePercentage;

    @Column(name = "payment_gateway_fee_percentage_applied")
    private BigDecimal paymentGatewayFeePercentageApplied;

    @Column(name = "payment_gateway_fee_fix_amount_unit")
    private BigDecimal paymentGatewayFeeFixedAmountUnit;

    @Column(name = "payment_gateway_fee_fix_amount_total")
    private BigDecimal paymentGatewayFeeFixedAmountTotal;

    @Column(name = "payment_gateway_total_fee_applied")
    private BigDecimal paymentGatewayTotalFeeApplied;

    @Column(name = "serveme_fee_percentage")
    private BigDecimal servemeFeePercentage;

    @Column(name = "serveme_fee_percentage_applied")
    private BigDecimal servemeFeePercentageApplied;

    @Column(name = "serveme_fee_fix_amount_unit")
    private BigDecimal servemeFeeFixedAmountUnit;

    @Column(name = "serveme_fee_fix_amount_total")
    private BigDecimal servemeFeeFixedAmountTotal;

    @Column(name = "serveme_total_fee_applied")
    private BigDecimal servemeTotalFeeApplied;

    //Split benefits
    @Column(name = "restaurant_benefit")
    private BigDecimal restaurantBenefit;

    @Column(name = "people_to_pay", nullable = false)
    private int peopleToPay;



    @Column(name = "test_mode")
    private boolean testMode;

    @ManyToOne
    @JoinColumn(name = "restaurantTab_id", nullable = false)
    private RestaurantTab restaurantTab;


    @OneToMany(mappedBy = "invoice", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CustomerCharge> customerCharges;


    @OneToMany(mappedBy = "invoice", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<InvoiceLine> lines;


    public Invoice(){}


    public Invoice(
            String orderId,
            String orderUniqueId,
            RestaurantTab restaurantTab,
            BigDecimal customerGrossAmount,
            BigDecimal tip,
            BigDecimal serviceChargePercentage,
            BigDecimal discountPercentage,
            BigDecimal paymentGatewayFeePercentage,
            BigDecimal paymentGatewayFeeFixedAmountUnit,
            BigDecimal servemeFeePercentage,
            BigDecimal servemeFeeFixedAmountUnit,
            boolean testMode,
            int peopleToPay) {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.customerGrossAmount = customerGrossAmount;
        this.tip = tip != null ? tip : BigDecimal.ZERO;
        this.discountPercentage = discountPercentage;
        this.orderId = orderId;
        this.orderUniqueId = orderUniqueId;
        this.restaurantTab = restaurantTab;
        this.serviceChargePercentage = serviceChargePercentage;
        this.testMode = testMode;

        //Service charge
        this.serviceChargeApplied = CalcHelper.percentage(
                customerGrossAmount,
                serviceChargePercentage);

        //Discount
        this.discountApplied = CalcHelper.percentage(
                customerGrossAmount,
                discountPercentage != null ? discountPercentage : BigDecimal.ZERO);

        this.customerNetAmount = OrderHelper.getTotalCustomerSpent(
                customerGrossAmount,
                tip != null ? tip : BigDecimal.ZERO,
                serviceChargePercentage,
                discountPercentage);

        //FEES

        calculateFeesForPayers(
                peopleToPay,
                paymentGatewayFeePercentage,
                paymentGatewayFeeFixedAmountUnit,
                servemeFeePercentage,
                servemeFeeFixedAmountUnit);



    }

    public void calculateFeesForPayers(
            int peopleToPay,
            BigDecimal paymentGatewayFeePercentage,
            BigDecimal paymentGatewayFeeFixedAmountUnit,
            BigDecimal servemeFeePercentage,
            BigDecimal servemeFeeFixedAmountUnit) {

        if(peopleToPay > 0) {
            this.invoicingResult = InvoicingResult.OK;
            this.peopleToPay = peopleToPay;
            this.paymentGatewayFeeFixedAmountUnit = paymentGatewayFeeFixedAmountUnit;
            this.paymentGatewayFeePercentage = paymentGatewayFeePercentage;
            this.paymentGatewayFeePercentageApplied = CalcHelper
                    .percentage(
                            this.customerNetAmount,
                            paymentGatewayFeePercentage
                    );

            this.paymentGatewayFeeFixedAmountTotal = paymentGatewayFeeFixedAmountUnit
                    .multiply(new BigDecimal(peopleToPay));
            this.paymentGatewayTotalFeeApplied = paymentGatewayFeePercentageApplied
                    .add(paymentGatewayFeeFixedAmountTotal);

            this.servemeFeeFixedAmountUnit = servemeFeeFixedAmountUnit;
            this.servemeFeeFixedAmountTotal = servemeFeeFixedAmountUnit
                    .multiply(new BigDecimal(peopleToPay));
            this.servemeFeePercentage = servemeFeePercentage;
            this.servemeFeePercentageApplied = CalcHelper
                    .percentage(
                            this.customerNetAmount,
                            servemeFeePercentage);
            this.servemeTotalFeeApplied = servemeFeePercentageApplied
                    .add(servemeFeeFixedAmountTotal);

            this.restaurantBenefit = customerNetAmount
                    .subtract(paymentGatewayTotalFeeApplied)
                    .subtract(servemeTotalFeeApplied);
        }else {
            setNoPayers();
        }
    }



    public void setNoPayers() {
        this.invoicingResult = InvoicingResult.NO_PAYERS;
        this.peopleToPay = 0;
        this.paymentGatewayFeeFixedAmountUnit = null;
        this.paymentGatewayFeePercentage = null;
        this.paymentGatewayFeePercentageApplied = null;
        this.paymentGatewayFeeFixedAmountTotal = null;
        this.paymentGatewayTotalFeeApplied = null;
        this.servemeFeeFixedAmountUnit = null;
        this.servemeFeeFixedAmountTotal = null;
        this.servemeFeePercentage = null;
        this.servemeFeePercentageApplied = null;
        this.servemeTotalFeeApplied = null;
        this.restaurantBenefit = null;
    }


    public InvoicingResult getInvoicingResult() {
        return invoicingResult;
    }


    public BigDecimal getPaymentGatewayFeeFixedAmountTotal() {
        return paymentGatewayFeeFixedAmountTotal;
    }

    public int getPeopleToPay() {
        return peopleToPay;
    }

    public BigDecimal getServemeFeeFixedAmountTotal() {
        return servemeFeeFixedAmountTotal;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public List<CustomerCharge> getCustomerCharges() {
        return customerCharges;
    }

    public BigDecimal getCustomerGrossAmount() {
        return customerGrossAmount;
    }

    public BigDecimal getCustomerNetAmount() {
        return customerNetAmount;
    }

    public BigDecimal getDiscountApplied() {
        return discountApplied;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public long getId() {
        return id;
    }

    public List<InvoiceLine> getLines() {
        return lines;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderUniqueId() {
        return orderUniqueId;
    }

    public BigDecimal getPaymentGatewayFeeFixedAmountUnit() {
        return paymentGatewayFeeFixedAmountUnit;
    }

    public BigDecimal getPaymentGatewayFeePercentage() {
        return paymentGatewayFeePercentage;
    }

    public BigDecimal getPaymentGatewayFeePercentageApplied() {
        return paymentGatewayFeePercentageApplied;
    }

    public BigDecimal getPaymentGatewayTotalFeeApplied() {
        return paymentGatewayTotalFeeApplied;
    }

    public BigDecimal getRestaurantBenefit() {
        return restaurantBenefit;
    }

    public RestaurantTab getRestaurantTab() {
        return restaurantTab;
    }

    public BigDecimal getServemeFeeFixedAmountUnit() {
        return servemeFeeFixedAmountUnit;
    }

    public BigDecimal getServemeFeePercentage() {
        return servemeFeePercentage;
    }

    public BigDecimal getServemeFeePercentageApplied() {
        return servemeFeePercentageApplied;
    }

    public BigDecimal getServemeTotalFeeApplied() {
        return servemeTotalFeeApplied;
    }

    public BigDecimal getServiceChargeApplied() {
        return serviceChargeApplied;
    }

    public BigDecimal getServiceChargePercentage() {
        return serviceChargePercentage;
    }

    public boolean isTestMode() {
        return testMode;
    }

    public BigDecimal getTip() {
        return tip;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }


    //SETTERS

    public void setRestaurantTab(RestaurantTab restaurantTab) {
        this.restaurantTab = restaurantTab;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLines(List<InvoiceLine> lines) {
        this.lines = lines;
    }


    public void setCustomerCharges(List<CustomerCharge> customerCharges) {
        this.customerCharges = customerCharges;
    }








    @Override
    public String toString() {
        return "Invoice{" +
                "createdAt=" + createdAt +
                ", customerGrossAmount=" + customerGrossAmount +
                ", customerNetAmount=" + customerNetAmount +
                ", discountApplied=" + discountApplied +
                ", discountPercentage=" + discountPercentage +
                ", id=" + id +
                ", lines=" + lines +
                ", orderGuestCharges=" + customerCharges +
                ", orderId='" + orderId + '\'' +
                ", orderUniqueId='" + orderUniqueId + '\'' +
                ", paymentGatewayTotalFeeApplied=" + paymentGatewayTotalFeeApplied +
                ", paymentGatewayFeeFixedAmountUnit=" + paymentGatewayFeeFixedAmountUnit +
                ", paymentGatewayFeePercentage=" + paymentGatewayFeePercentage +
                ", restaurantBenefit=" + restaurantBenefit +
                ", restaurantTab=" + restaurantTab +
                ", servemeTotalFeeApplied=" + servemeTotalFeeApplied +
                ", servemeFeeFixedAmountUnit=" + servemeFeeFixedAmountUnit +
                ", servemeFeePercentage=" + servemeFeePercentage +
                ", serviceChargeApplied=" + serviceChargeApplied +
                ", serviceChargePercentage=" + serviceChargePercentage +
                ", testMode=" + testMode +
                ", tip=" + tip +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
