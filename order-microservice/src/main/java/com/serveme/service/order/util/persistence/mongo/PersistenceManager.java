package com.serveme.service.order.util.persistence.mongo;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;

public interface PersistenceManager {

    MongoCollection<Document> getCollection(Class clazz);

    <T> T mongoToClass(Document doc, Class<T> clazz);

    <T extends IPersistentObject> T findById(Class<T> clazz, String id);

    <T extends IPersistentObject> List<T> findFilteredList(Class<T> clazz, Bson filter, int from, int limit);

    <T extends IPersistentObject> List<T> findFilteredList(Class<T> clazz, Bson filter, int from, int limit, Bson sortFilter);

    <T extends IPersistentObject> T         insert(T entity, Class<T> clazz);

    <T extends IPersistentObject> boolean   update(T entity);

    <T extends IPersistentObject> boolean update(Class<T> clazz, Bson filter, Bson docUpdate);

    <T extends IPersistentObject> boolean   delete(Class<T> clazz, String id);

    <T extends IPersistentObject> long count(Class<T> clazz, Bson filter);
}
