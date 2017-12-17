package com.serveme.service.order.dao.impl;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.serveme.service.order.domain.Order;
import com.serveme.service.order.enums.OrderStatus;
import com.serveme.service.order.util.persistence.mongo.BaseDAO;
import com.serveme.service.order.util.persistence.mongo.PersistenceManager;
import com.serveme.service.order.util.persistence.mongo.impl.MongoPersistenceManager;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Davids-iMac on 18/11/15.
 */
@Repository
public class OrderDao extends BaseDAO<Order> {

    public OrderDao() {
        super(Order.class);
    }


    /**************************
     * USER METHODS
     **************************/

    public Order findByIdAndUser(String id, long userId) {
        PersistenceManager manager = this.getPersistenceManager();

        Order result = null;
        MongoCollection<Document> dbc = manager.getCollection(Order.class);

        Bson filter = Filters.and(
                Filters.eq(MongoPersistenceManager.MONGO_ID_FIELD, new ObjectId(id)),
                Filters.eq("people.userId", userId)
        );

        Document doc = dbc.find(filter).first();

        if (doc != null) {
            result = manager.mongoToClass(doc, Order.class);
        }
        return result;
    }


    public Order findCurrentOrderByUser(long userId) {
        PersistenceManager manager = this.getPersistenceManager();

        Order result = null;
        MongoCollection<Document> dbc = manager.getCollection(Order.class);

        Bson filter = Filters.and(
                Filters.eq("people.userId", userId),
                Filters.or(
                        Filters.eq("status", OrderStatus.PENDING.toString()),
                        Filters.eq("status", OrderStatus.ACCEPTED.toString()),
                        Filters.eq("status", OrderStatus.FINISHED.toString()))
        );


        Document doc = dbc.find(filter).first();

        if (doc != null) {
            result = manager.mongoToClass(doc, Order.class);
        }
        return result;
    }


    public long countFinishedOrdersByUser(long userId) {
        Bson filter = Filters.and(
                Filters.eq("people.userId", userId),
                Filters.or(
                        Filters.eq("status", OrderStatus.CLIENT_REVIEWED.toString()),
                        Filters.eq("status", OrderStatus.FINISHED.toString()))
        );
        return this.count(filter);
    }

    public List<Long> countFinishedOrdersByUserAndRestaurantWithSinceDays(long userId, long restaurantId) {
        Bson filter = Filters.and(
                Filters.eq("people.userId", userId),
                Filters.eq("restaurant.id", restaurantId),
                Filters.or(
                        Filters.eq("status", OrderStatus.CLIENT_REVIEWED.toString()),
                        Filters.eq("status", OrderStatus.FINISHED.toString()))
        );
        List<Long> daysSinceOrders = new ArrayList<Long>();
        Long currentDate = new Date().getTime();
        List<Order> orders = this.getList(filter, 0, Integer.MAX_VALUE);
        for (int i = 0; i < orders.size(); i++) {
            Long orderDate = Long.parseLong(orders.get(i).getFinishedAt());
            Long daysSince = (Long) ((currentDate - orderDate) / 86400000);
            daysSinceOrders.add(daysSince);
        }
        return daysSinceOrders;
    }


    public List<Order> findHistoryByUser(long userId, int from, int limit) {

        Bson filter = Filters.and(
                Filters.eq("people.userId", userId),
                Filters.or(
                        Filters.eq("status", OrderStatus.CLIENT_REVIEWED.toString()),
                        Filters.eq("status", OrderStatus.FINISHED.toString()))
        );
        Bson sortFilter = Sorts.descending("arrivalAt");
        return this.getList(filter, from, limit, sortFilter);
    }


    public List<Order> getActiveOrdersFromUserList(List<Long> userIdList) {

        Bson filter = Filters.and(
                Filters.in("people.userId", userIdList),
                Filters.or(
                        Filters.eq("status", OrderStatus.PENDING.toString()),
                        Filters.eq("status", OrderStatus.ACCEPTED.toString()),
                        Filters.eq("status", OrderStatus.FINISHED.toString()))
        );

        return this.getList(filter, 0, 1000);


    }

    /***************************************
     * RESTAURANT METHODS
     ***************************************/

    public long countOrdersByRestaurant(long restaurantId) {
        Bson filter = Filters.and(
                Filters.eq("restaurant.id", restaurantId)
        );
        return this.count(filter);
    }

    public String getNextIdByRestaurant(long restaurantId) {
        long current = countOrdersByRestaurant(restaurantId);

        for (int i = 1; i < 11; i++) {  //10 tries
            String newId = restaurantId + "-" + (current + i);
            Bson bson = Filters.and(
                    Filters.eq("uniqueId", newId)
            );
            long exists = this.count(bson);
            if (exists == 0) return newId;
        }
        throw new MongoException("Unable to find an unused id");
    }

    public long countFinishedOrdersByRestaurant(long restaurantId) {
        Bson filter = Filters.and(
                Filters.eq("restaurant.id", restaurantId),
                Filters.or(
                        Filters.eq("status", OrderStatus.CLIENT_REVIEWED.toString()),
                        Filters.eq("status", OrderStatus.FINISHED.toString()))
        );
        return this.count(filter);
    }

    public long countFinishedOrdersByRestaurant(long restaurantId, long startOfDay) {
        Bson filter = Filters.and(
                Filters.eq("restaurant.id", restaurantId),
                Filters.or(
                        Filters.eq("status", OrderStatus.CLIENT_REVIEWED.toString()),
                        Filters.eq("status", OrderStatus.FINISHED.toString())),
                Filters.gte("arrivalAt", "" + startOfDay)
        );
        return this.count(filter);
    }

    public long countPendingOrdersByRestaurant(long restaurantId) {
        Bson filter = Filters.and(
                Filters.eq("restaurant.id", restaurantId),
                Filters.eq("status", OrderStatus.PENDING.toString())
        );
        return this.count(filter);
    }

    public long countAcceptedOrdersByRestaurant(long restaurantId) {
        Bson filter = Filters.and(
                Filters.eq("restaurant.id", restaurantId),
                Filters.eq("status", OrderStatus.ACCEPTED.toString())
        );
        return this.count(filter);
    }

    public long countActiveOrdersByRestaurant(long restaurantId) {
        Bson filter = Filters.and(
                Filters.eq("restaurant.id", restaurantId),
                Filters.in("status", OrderStatus.ACCEPTED.toString(), OrderStatus.PENDING.toString())
        );
        return this.count(filter);
    }

    public List<Order> findHistoryByRestaurant(long restaurantId, int from, int limit) {

        Bson filter = Filters.and(
                Filters.eq("restaurant.id", restaurantId),
                Filters.or(
                        Filters.eq("status", OrderStatus.CLIENT_REVIEWED.toString()),
                        Filters.eq("status", OrderStatus.FINISHED.toString()))
        );

        Bson sortFilter = Sorts.descending("arrivalAt");
        return this.getList(filter, from, limit, sortFilter);
    }


    public List<Order> findPendingListByRestaurant(long restaurantId, int from, int limit) {

        Bson filter = Filters.and(
                Filters.eq("restaurant.id", restaurantId),
                Filters.eq("status", OrderStatus.PENDING.toString())
        );

        return this.getList(filter, from, limit);
    }

    public List<Order> findAcceptedListByRestaurant(long restaurantId, int from, int limit) {

        Bson filter = Filters.and(
                Filters.eq("restaurant.id", restaurantId),
                Filters.eq("status", OrderStatus.ACCEPTED.toString())
        );

        return this.getList(filter, from, limit);
    }

    public List<Order> findActiveListByRestaurant(long restaurantId, int from, int limit) {

        Bson filter = Filters.and(
                Filters.eq("restaurant.id", restaurantId),
                Filters.in("status", OrderStatus.ACCEPTED.toString(), OrderStatus.PENDING.toString())
        );

        return this.getList(filter, from, limit);
    }


    public List<Order> findFinishedListByRestaurant(long restaurantId, int from, int size, long startOfDay) {
        Bson filter = Filters.and(
                Filters.eq("restaurant.id", restaurantId),
                Filters.in("status", OrderStatus.FINISHED.toString(), OrderStatus.CLIENT_REVIEWED.toString()),
                Filters.gte("finishedAt", "" + startOfDay)
        );
        Bson sortFilter = Sorts.descending("arrivalAt");
        return this.getList(filter, from, size, sortFilter);
    }

    public List<Order> findFinishedListByRestaurant(long restaurantId, long initDate, long endDate) {
        Bson filter = Filters.and(
                Filters.eq("restaurant.id", restaurantId),
                Filters.in("status", OrderStatus.FINISHED.toString(), OrderStatus.CLIENT_REVIEWED.toString()),
                Filters.gte("finishedAt", "" + initDate),
                Filters.lte("finishedAt", "" + endDate)
        );

        long size = this.count(filter);

        return this.getList(filter, 0, (int) size);
    }

    public List<Order> findAllHistory(int from, int limit) {

        Bson filter = Filters.and(
                Filters.or(
                        Filters.eq("status", OrderStatus.CLIENT_REVIEWED.toString()),
                        Filters.eq("status", OrderStatus.FINISHED.toString()))
        );
        Bson sortFilter = Sorts.descending("arrivalAt");
        return this.getList(filter, from, limit, sortFilter);
    }

    public List<Order> findPendingToSynchronize(int limit) {

        Bson filter = Filters.and(
                Filters.or(
                        Filters.eq("status", OrderStatus.CLIENT_REVIEWED.toString()),
                        Filters.eq("status", OrderStatus.FINISHED.toString()))
                ,Filters.or(
                        Filters.exists("paymentSync", false),
                        Filters.eq("paymentSync", false))
        );
        Bson sortFilter = Sorts.descending("arrivalAt");
        return this.getList(filter, 0, limit, sortFilter);
    }


    public List<Order> findAllPendingList(int from, int limit) {

        Bson filter = Filters.and(
                Filters.eq("status", OrderStatus.PENDING.toString())
        );

        return this.getList(filter, from, limit);
    }

    @Deprecated
    public List<Order> findAllNotCharged(int max) {

        Bson filter = Filters.and(
                Filters.eq("orderCharged", false)
        );

        return this.getList(filter, 0, max);
    }

    public List<Order> findAllEmailNotSent() {

        Bson filter = Filters.and(
                Filters.eq("emailSent", false)
        );

        return this.getList(filter, 0, Integer.MAX_VALUE);
    }

    public List<Order> findAllAcceptedList(int from, int limit) {

        Bson filter = Filters.and(
                Filters.eq("status", OrderStatus.ACCEPTED.toString())
        );

        return this.getList(filter, from, limit);
    }

    public List<Order> findAllActiveList(int from, int limit) {

        Bson filter = Filters.and(
                Filters.in("status", OrderStatus.ACCEPTED.toString(), OrderStatus.PENDING.toString())
        );

        return this.getList(filter, from, limit);
    }
}


