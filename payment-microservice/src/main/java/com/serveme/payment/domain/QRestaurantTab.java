package com.serveme.payment.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QRestaurantTab is a Querydsl query type for RestaurantTab
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QRestaurantTab extends EntityPathBase<RestaurantTab> {

    private static final long serialVersionUID = 1890400810L;

    public static final QRestaurantTab restaurantTab = new QRestaurantTab("restaurantTab");

    public final DateTimePath<java.time.Instant> createdAt = createDateTime("createdAt", java.time.Instant.class);

    public final DatePath<java.time.LocalDate> endPeriodInclusive = createDate("endPeriodInclusive", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Invoice, QInvoice> invoices = this.<Invoice, QInvoice>createList("invoices", Invoice.class, QInvoice.class, PathInits.DIRECT2);

    public final NumberPath<Long> restaurantId = createNumber("restaurantId", Long.class);

    public final DatePath<java.time.LocalDate> startPeriodInclusive = createDate("startPeriodInclusive", java.time.LocalDate.class);

    public final BooleanPath transferred = createBoolean("transferred");

    public final DateTimePath<java.time.Instant> updatedAt = createDateTime("updatedAt", java.time.Instant.class);

    public QRestaurantTab(String variable) {
        super(RestaurantTab.class, forVariable(variable));
    }

    public QRestaurantTab(Path<? extends RestaurantTab> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRestaurantTab(PathMetadata<?> metadata) {
        super(RestaurantTab.class, metadata);
    }

}

