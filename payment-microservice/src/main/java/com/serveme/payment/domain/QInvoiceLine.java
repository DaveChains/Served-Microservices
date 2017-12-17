package com.serveme.payment.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QInvoiceLine is a Querydsl query type for InvoiceLine
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QInvoiceLine extends EntityPathBase<InvoiceLine> {

    private static final long serialVersionUID = 1018103891L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInvoiceLine invoiceLine = new QInvoiceLine("invoiceLine");

    public final StringPath concept = createString("concept");

    public final NumberPath<Long> dishId = createNumber("dishId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> individualAmount = createNumber("individualAmount", Integer.class);

    public final QInvoice invoice;

    public final NumberPath<Integer> num = createNumber("num", Integer.class);

    public final NumberPath<Integer> totalAmount = createNumber("totalAmount", Integer.class);

    public QInvoiceLine(String variable) {
        this(InvoiceLine.class, forVariable(variable), INITS);
    }

    public QInvoiceLine(Path<? extends InvoiceLine> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QInvoiceLine(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QInvoiceLine(PathMetadata<?> metadata, PathInits inits) {
        this(InvoiceLine.class, metadata, inits);
    }

    public QInvoiceLine(Class<? extends InvoiceLine> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.invoice = inits.isInitialized("invoice") ? new QInvoice(forProperty("invoice"), inits.get("invoice")) : null;
    }

}

