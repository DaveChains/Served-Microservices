package com.serveme.service.order.util.persistence.mongo.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.serveme.service.order.util.persistence.mongo.IPersistentObject;
import com.serveme.service.order.util.persistence.mongo.PersistenceManager;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class MongoPersistenceManager implements PersistenceManager {


    public static final String MONGO_ID_FIELD = "_id";
    public static final String JAVA_ID_FIELD = "id";
    protected MongoDatabase db;
    protected MongoClient mongoClient;
    Logger logger = Logger.getLogger(MongoPersistenceManager.class.getName());

    @Inject
    public MongoPersistenceManager(MongoClient mongoClient, @Value("${spring.enums.mongodb.database}") String database) {
        try {
            this.mongoClient = mongoClient;
            this.db = mongoClient.getDatabase(database);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public MongoCollection<Document> getCollection(Class clazz) {
        return this.getCollection(this.getCollectionName(clazz));
    }

    @Override
    public <T> T mongoToClass(Document doc, Class<T> clazz) {

        ObjectMapper mapper = new ObjectMapper();
        String json = serializeMongoObject(doc);
        try {
            return mapper.readValue(json, clazz);

        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }


    /**************************************
     * FIND METHODS
     **************************************/
    @Override
    public <T extends IPersistentObject> T findById(Class<T> clazz, String id) {

        T result = null;
        MongoCollection<Document> dbc = this.getCollection(clazz);
        Document doc = dbc.find(Filters.eq(MONGO_ID_FIELD, new ObjectId(id))).first();

        if (doc != null) {
            result = mongoToClass(doc, clazz);
        }
        return result;
    }

    @Override
    public <T extends IPersistentObject> List<T> findFilteredList(Class<T> clazz, Bson filter, int from, int limit) {
        return findFilteredList(clazz, filter, from, limit, null);
    }

    @Override
    public <T extends IPersistentObject> List<T> findFilteredList(Class<T> clazz, Bson filter, int from, int limit, Bson sortFilter) {
        List<T> list = new ArrayList<>();
        MongoCollection<Document> collection = this.getCollection(clazz);
        FindIterable<Document> iterable = collection.find(filter).skip(from).limit(limit);
        if (sortFilter != null) iterable = iterable.sort(sortFilter);
        MongoCursor<Document> cursor = iterable.iterator();
        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                list.add(mongoToClass(doc, clazz));
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    /************************************
     * UPDATE METHODS
     ************************************/
    @Override
    public <T extends IPersistentObject> T insert(T entity, Class<T> clazz) {

        MongoCollection<Document> collection = this.getCollection(entity.getClass());

        String json = new Gson().toJson(entity);
        Document doc = Document.parse(json);
        collection.insertOne(doc);
        return mongoToClass(doc, clazz);
    }

    @Override
    public <T extends IPersistentObject> boolean update(T entity) {
        MongoCollection<Document> collection = this.getCollection(entity.getClass());

        String json = new Gson().toJson(entity);
        Document doc = Document.parse(json);

        UpdateResult result = collection.replaceOne(Filters.eq(MONGO_ID_FIELD, new ObjectId(entity.getId())), doc);

        return result.getModifiedCount() > 0;
    }


    @Override
    public <T extends IPersistentObject> boolean update(Class<T> clazz, Bson filter, Bson docUpdate) {

        MongoCollection<Document> collection = this.getCollection(clazz);
        UpdateResult result = collection.updateOne(filter, docUpdate);

        return result.getModifiedCount() > 0;
    }


    @Override
    public <T extends IPersistentObject> boolean delete(Class<T> clazz, String id) {

        MongoCollection<Document> collection = this.getCollection(clazz);
        DeleteResult result = collection.deleteOne(Filters.eq(MONGO_ID_FIELD, new ObjectId(id)));
        return result.getDeletedCount() > 0;

    }

    /************************************
     * UTIL METHODS
     ************************************/


    @Override
    public <T extends IPersistentObject> long count(Class<T> clazz, Bson filter) {
        MongoCollection<Document> collection = this.getCollection(clazz);
        long count = collection.count(filter);
        return count;
    }


    /************************************
     * PRIVATE METHODS
     ************************************/

    protected String getCollectionName(Class clazz) {
        String collectionName;
        Entity entityAnn = (Entity) clazz.getAnnotation(Entity.class);
        if (entityAnn != null && !entityAnn.name().isEmpty()) {
            collectionName = entityAnn.name();
        } else {
            Table tableAnn = (Table) clazz.getAnnotation(Table.class);
            if (tableAnn != null && !tableAnn.name().isEmpty()) {
                collectionName = tableAnn.name();
            } else {
                collectionName = clazz.getSimpleName().toLowerCase();
            }
        }
        return collectionName;
    }


    private MongoCollection<Document> getCollection(String collectionName) {
        return this.db.getCollection(collectionName);
    }


    private String serializeMongoObject(Document data) {
        return serializeMongoObject(data.toJson());
    }


    private String serializeMongoObject(String data) {
        String[] idList = data.split("\"_id");

        StringBuilder finalJson = new StringBuilder();
        if (idList != null && idList.length > 0) {
            for (String idObject : idList) {
                int index = idObject.indexOf("$oid");
                if (index < 0) {
                    finalJson.append(idObject);
                    continue;
                }
                String sub1 = idObject.substring(index + 5);
                int indexColon = sub1.indexOf("}");
                String idValue = "";
                String restOfString = "";
                if (indexColon >= 0) {
                    idValue = sub1.substring(0, indexColon);
                    restOfString = sub1.substring(indexColon + 1);
                }
                finalJson.append("\"" + JAVA_ID_FIELD + "\"" + idValue + restOfString);

            }

        }
        return finalJson.toString();
    }


}
