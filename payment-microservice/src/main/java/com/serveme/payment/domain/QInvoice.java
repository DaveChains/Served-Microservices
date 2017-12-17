package com.serveme.payment.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QInvoice is a Querydsl query type for Invoice
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QInvoice extends EntityPathBase<Invoice> {

    private static final long serialVersionUID = -101917761L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInvoice invoice = new QInvoice("invoice");

    public final DateTimePath<java.time.Instant> createdAt = createDateTime("createdAt", java.time.Instant.class);

    public final ListPath<CustomerCharge, QCustomerCharge> customerCharges = this.<CustomerCharge, QCustomerCharge>createList("customerCharges", CustomerCharge.class, QCustomerCharge.class, PathInits.DIRECT2);

    public final NumberPath<java.math.BigDecimal> customerGrossAmount = createNumber("customerGrossAmount", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> customerNetAmount = createNumber("customerNetAmount", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> discountApplied = createNumber("discountApplied", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> discountPercentage = createNumber("discountPercentage", java.math.BigDecimal.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.serveme.payment.enums.InvoicingResult> invoicingResult = createEnum("invoicingResult", com.serveme.payment.enums.InvoicingResult.class);

    public final ListPath<InvoiceLine, QInvoiceLine> lines = this.<InvoiceLine, QInvoiceLine>createList("lines", InvoiceLine.class, QInvoiceLine.class, PathInits.DIRECT2);

    public final StringPath orderId = createString("orderId");

    public final StringPath orderUniqueId = createString("orderUniqueId");

    public final NumberPath<java.math.BigDecimal> paymentGatewayFeeFixedAmountTotal = createNumber("paymentGatewayFeeFixedAmountTotal", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> paymentGatewayFeeFixedAmountUnit = createNumber("paymentGatewayFeeFixedAmountUnit", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> paymentGatewayFeePercentage = createNumber("paymentGatewayFeePercentage", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> paymentGatewayFeePercentageApplied = createNumber("paymentGatewayFeePercentageApplied", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> paymentGatewayTotalFeeApplied = createNumber("paymentGatewayTotalFeeApplied", java.math.BigDecimal.class);

    public final NumberPath<Integer> peopleToPay = createNumber("peopleToPay", Integer.class);

    public final NumberPath<java.math.BigDecimal> restaurantBenefit = createNumber("restaurantBenefit", java.math.BigDecimal.class);

    public final QRestaurantTab restaurantTab;

    public final NumberPath<java.math.BigDecimal> servemeFeeFixedAmountTotal = createNumber("servemeFeeFixedAmountTotal", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> servemeFeeFixedAmountUnit = createNumber("servemeFeeFixedAmountUnit", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> servemeFeePercentage = createNumber("servemeFeePercentage", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> servemeFeePercentageApplied = createNumber("servemeFeePercentageApplied", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> servemeTotalFeeApplied = createNumber("servemeTotalFeeApplied", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> serviceChargeApplied = createNumber("serviceChargeApplied", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> serviceChargePercentage = createNumber("serviceChargePercentage", java.math.BigDecimal.class);

    public final BooleanPath testMode = createBoolean("testMode");

    public final NumberPath<java.math.BigDecimal> tip = createNumber("tip", java.math.BigDecimal.class);

    public final DateTimePath<java.time.Instant> updatedAt = createDateTime("updatedAt", java.time.Instant.class);

    public QInvoice(String variable) {
        this(Invoice.class, forVariable(variable), INITS);
    }

    public QInvoice(Path<? extends Invoice> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QInvoice(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QInvoice(PathMetadata<?> metadata, PathInits inits) {
        this(Invoice.class, metadata, inits);
    }

    public QInvoice(Class<? extends Invoice> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.restaurantTab = inits.isInitialized("restaurantTab") ? new QRestaurantTab(forProperty("restaurantTab")) : null;
    }

}

