package com.serveme.service.order.dao.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.serveme.service.order.domain.Invoice;
import com.serveme.service.order.util.persistence.mongo.BaseDAO;
import com.serveme.service.order.util.persistence.mongo.PersistenceManager;
import com.serveme.service.order.util.persistence.mongo.impl.MongoPersistenceManager;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InvoiceDao extends BaseDAO<Invoice> {

    public InvoiceDao() {
        super(Invoice.class);
    }

    public Invoice findById(String id) {
        PersistenceManager manager = this.getPersistenceManager();

        Invoice result = null;
        MongoCollection<Document> dbc = manager.getCollection(Invoice.class);

        Bson filter = Filters.and(
                Filters.eq(MongoPersistenceManager.MONGO_ID_FIELD, new ObjectId(id))
        );

        Document doc = dbc.find(filter).first();

        if (doc != null) {
            result = manager.mongoToClass(doc, Invoice.class);
        }
        return result;
    }

    public List<Invoice> findByRestaurant(long restaurantId, int from, int limit) {
        Bson filter = Filters.eq("restaurantId", restaurantId);
        Bson sortFilter = Sorts.descending("generationDate");
        return this.getList(filter, from, limit, sortFilter);
    }

}
