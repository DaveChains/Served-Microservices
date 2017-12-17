package com.serveme.service.order.dao.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.serveme.service.order.domain.Order;
import com.serveme.service.order.domain.TestDomain;
import com.serveme.service.order.util.persistence.mongo.BaseDAO;
import com.serveme.service.order.util.persistence.mongo.PersistenceManager;
import com.serveme.service.order.util.persistence.mongo.impl.MongoPersistenceManager;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

/**
 * Created by Davids-iMac on 23/05/16.
 */
@Repository
public class TestDao extends BaseDAO<TestDomain> {


    public TestDao() {
        super(TestDomain.class);
    }


    public TestDomain findByKey(String key) {
        PersistenceManager manager = this.getPersistenceManager();

        TestDomain result = null;
        MongoCollection<Document> dbc = manager.getCollection(TestDomain.class);

        Bson filter = Filters.eq("key", key);

        Document doc = dbc.find(filter).first();

        if (doc != null) {
            result = manager.mongoToClass(doc, TestDomain.class);
        }
        return result;
    }
}
