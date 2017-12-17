package com.serveme.payment.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPaymentDetail is a Querydsl query type for PaymentDetail
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QPaymentDetail extends EntityPathBase<PaymentDetail> {

    private static final long serialVersionUID = -168461047L;

    public static final QPaymentDetail paymentDetail = new QPaymentDetail("paymentDetail");

    public final StringPath cardLastDigits = createString("cardLastDigits");

    public final StringPath cardType = createString("cardType");

    public final DateTimePath<java.time.Instant> createdAt = createDateTime("createdAt", java.time.Instant.class);

    public final StringPath customerToken = createString("customerToken");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.serveme.payment.enums.PaymentProviderId> paymentProvider = createEnum("paymentProvider", com.serveme.payment.enums.PaymentProviderId.class);

    public final DateTimePath<java.time.Instant> updatedAt = createDateTime("updatedAt", java.time.Instant.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QPaymentDetail(String variable) {
        super(PaymentDetail.class, forVariable(variable));
    }

    public QPaymentDetail(Path<? extends PaymentDetail> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPaymentDetail(PathMetadata<?> metadata) {
        super(PaymentDetail.class, metadata);
    }

}

