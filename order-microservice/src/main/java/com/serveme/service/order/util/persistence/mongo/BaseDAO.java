package com.serveme.service.order.util.persistence.mongo;

import org.bson.conversions.Bson;

import javax.inject.Inject;
import java.util.List;

public abstract class BaseDAO<T extends BaseEntity> {

    @Inject
    private PersistenceManager persistenceManager;

    private Class<T> entityClass;

    public BaseDAO(Class clazz) {
        entityClass = clazz;
    }

    protected PersistenceManager getManager() {
        return persistenceManager;
    }

    public PersistenceManager getPersistenceManager() {
        return getManager();
    }


    public void setPersistenceManager(PersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
    }


    /**
     * READ
     */
    public T getById(String id) {
        return this.getManager().findById(entityClass, id);
    }


    protected long count(Bson filter) {
        return this.getManager().count(entityClass, filter);
    }

    protected List<T> getList(Bson filter, int from, int limit) {
        return this.getManager().findFilteredList(entityClass, filter, from, limit);
    }

    protected List<T> getList(Bson filter, int from, int limit, Bson sortFilter) {
        return this.getManager().findFilteredList(entityClass, filter, from, limit, sortFilter);
    }


    /**
     * UPDATE
     */
    public T create(T entity) {
        return this.getManager().insert(entity, entityClass);
    }

    public boolean update(T entity) {
        return this.getManager().update(entity);
    }

    public boolean update(Bson filter, Bson docUpdate) {
        return this.getManager().update(entityClass, filter, docUpdate);
    }

    public boolean delete(String id) {
        return this.getManager().delete(entityClass, id);
    }

}
