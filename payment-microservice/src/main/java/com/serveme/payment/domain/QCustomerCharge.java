package com.serveme.payment.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QCustomerCharge is a Querydsl query type for CustomerCharge
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QCustomerCharge extends EntityPathBase<CustomerCharge> {

    private static final long serialVersionUID = -35410080L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomerCharge customerCharge = new QCustomerCharge("customerCharge");

    public final NumberPath<java.math.BigDecimal> amount = createNumber("amount", java.math.BigDecimal.class);

    public final StringPath chargeError = createString("chargeError");

    public final SimplePath<com.serveme.payment.payment.ChargeResultWrapper> chargeResult = createSimple("chargeResult", com.serveme.payment.payment.ChargeResultWrapper.class);

    public final EnumPath<com.serveme.payment.enums.ChargeType> chargeType = createEnum("chargeType", com.serveme.payment.enums.ChargeType.class);

    public final DateTimePath<java.time.Instant> createdAt = createDateTime("createdAt", java.time.Instant.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath idempotentTransactionId = createString("idempotentTransactionId");

    public final QInvoice invoice;

    public final StringPath notes = createString("notes");

    public final EnumPath<com.serveme.payment.enums.PaymentProviderId> paymentProviderId = createEnum("paymentProviderId", com.serveme.payment.enums.PaymentProviderId.class);

    public final EnumPath<com.serveme.payment.enums.ChargeStatus> status = createEnum("status", com.serveme.payment.enums.ChargeStatus.class);

    public final BooleanPath testMode = createBoolean("testMode");

    public final NumberPath<Integer> tries = createNumber("tries", Integer.class);

    public final DateTimePath<java.time.Instant> updatedAt = createDateTime("updatedAt", java.time.Instant.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QCustomerCharge(String variable) {
        this(CustomerCharge.class, forVariable(variable), INITS);
    }

    public QCustomerCharge(Path<? extends CustomerCharge> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QCustomerCharge(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QCustomerCharge(PathMetadata<?> metadata, PathInits inits) {
        this(CustomerCharge.class, metadata, inits);
    }

    public QCustomerCharge(Class<? extends CustomerCharge> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.invoice = inits.isInitialized("invoice") ? new QInvoice(forProperty("invoice"), inits.get("invoice")) : null;
    }

}

