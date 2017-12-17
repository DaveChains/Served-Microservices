package com.serveme.service.searching.service.impl;

import com.serveme.service.searching.domain.*;
import com.serveme.service.searching.exception.CategoryNotFoundException;
import com.serveme.service.searching.exception.RestaurantNotFoundException;
import com.serveme.service.searching.httpService.HttpServiceFactory;
import com.serveme.service.searching.service.RestaurantService;
import com.serveme.service.searching.util.ArrayUtil;
import com.serveme.service.searching.util.es.ElasticSearchArrayResult;
import com.serveme.service.searching.util.es.ElasticSearchResultPut;
import com.serveme.service.searching.util.http.HttpServiceBase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Davids-iMac on 22/10/15.
 */
@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Inject
    protected HttpServiceFactory httpServiceFactory;
    @Value("${spring.data.elasticsearch.distance}")
    private Double distance;

    @Override
    public RestaurantDomain getRestaurant(long id) {
        return (RestaurantDomain) httpServiceFactory
                .getRestaurantService(id)
                .call();
    }

    /**
     * If no restaurants exist with that id, it's created. Otherwise it is updated
     *
     * @param restaurantDomain
     * @return true if the restaurant was created, false if it was updated
     */
    @Override
    public boolean putRestaurant(RestaurantDomain restaurantDomain) {
        ElasticSearchResultPut result = (ElasticSearchResultPut) httpServiceFactory
                .createRestaurant(restaurantDomain)
                .call();
        return "true".equals(result.created);
    }


    @Override
    public boolean putCategory(long restaurantId, CategoryDomain category) {
        RestaurantDomain restaurant = this.getRestaurant(restaurantId);
        if (restaurant == null)
            throw new RestaurantNotFoundException(restaurantId);

        boolean created = true;
        if (ArrayUtil.nullEmpty(restaurant.getMenuCategories())) {
            List<CategoryDomain> newCategories = new LinkedList<>();
            newCategories.add(category);
            restaurant.setMenuCategories(newCategories);

        } else {
            int index = restaurant.getMenuCategories().indexOf(category);
            if (index >= 0) {
                created = false;
                restaurant.getMenuCategories().set(index, category);
            } else {
                restaurant.getMenuCategories().add(category);
            }

        }

        this.putRestaurant(restaurant);
        return created;
    }

    @Override
    public boolean putDish(long restaurantId, long categoryId, DishDomain dish) {
        RestaurantDomain restaurant = this.getRestaurant(restaurantId);
        if (restaurant == null)
            throw new RestaurantNotFoundException(restaurantId);

        if (ArrayUtil.nullEmpty(restaurant.getMenuCategories())) {
            throw new CategoryNotFoundException(restaurantId, categoryId);
        }
        List<CategoryDomain> categories = restaurant.getMenuCategories();
        CategoryDomain dummyCategory = new CategoryDomain();
        dummyCategory.setId(categoryId);

        int categoryIndex = categories.indexOf(dummyCategory);
        if (categoryIndex < 0) {
            throw new CategoryNotFoundException(restaurantId, categoryId);
        }

        CategoryDomain category = categories.get(categoryIndex);

        boolean created = true;
        if (ArrayUtil.nullEmpty(category.getDishes())) {
            category.setDishes(new ArrayList<>());
        }
        int dishIndex = category.getDishes().indexOf(dish);
        if (dishIndex >= 0) {
            created = false;
            category.getDishes().set(dishIndex, dish);
        } else {
            category.getDishes().add(dish);
        }

        this.putRestaurant(restaurant);
        return created;
    }

    @Override
    public List<RestaurantDomain> findRestaurants(SearchObject searchObject) {
        if (searchObject.getTermsIn() != null && !searchObject.getTermsIn().isEmpty()) {
            HttpServiceBase tmp = httpServiceFactory
                    .findRestaurant(searchObject.buildQuery());
            ElasticSearchArrayResult result = (ElasticSearchArrayResult) tmp.call();
            List<RestaurantDomain> list;
            if (searchObject.isShowTestRestaurants()) {
                list = result.getRestaurants();
            } else {
                list = result.filterByMaxDistance(distance);
            }
            RestaurantDomain.determinateLastBookingTimes(list);
            return list;
        } else {
            return getDefaultRestaurants(searchObject.getLocation(), searchObject.isShowTestRestaurants());
        }
    }

    private List<RestaurantDomain> getDefaultRestaurants(Location location, boolean isTestUser) {

        HttpServiceBase tmp = httpServiceFactory
                .findRestaurant(DefaultSearchObject.buildQuery(location, isTestUser));
        ElasticSearchArrayResult result = (ElasticSearchArrayResult) tmp.call();
        System.out.println( "RESULT SEARH OBJECT " +  result.toString());
        List<RestaurantDomain> list;
        if (isTestUser) {
            list = result.getRestaurants();
        } else {
            list = result.filterByMaxDistance(distance);
        }
        RestaurantDomain.determinateLastBookingTimes(list);
        return list;

    }

    @Override
    public boolean deleteRestaurant(long restaurantId) {
        RestaurantDomain restaurant = this.getRestaurant(restaurantId);
        if (restaurant != null) {
            restaurant.setActive(false);
            this.putRestaurant(restaurant);
            return true;
        }
        return false;

    }

    @Override
    public boolean deleteCategory(long restaurantId, long categoryId) {
        RestaurantDomain restaurant = this.getRestaurant(restaurantId);

        if (restaurant != null && !ArrayUtil.nullEmpty(restaurant.getMenuCategories())) {

            CategoryDomain dummyCategory = new CategoryDomain();
            dummyCategory.setId(categoryId);
            if (restaurant.getMenuCategories().remove(dummyCategory)) {
                this.putRestaurant(restaurant);
                return true;

            }
        }
        return false;

    }

    @Override
    public boolean deleteDish(long restaurantId, long categoryId, long dishId) {
        RestaurantDomain restaurant = this.getRestaurant(restaurantId);

        if (restaurant != null && !ArrayUtil.nullEmpty(restaurant.getMenuCategories())) {

            CategoryDomain dummyCategory = new CategoryDomain();
            dummyCategory.setId(categoryId);
            int categoryIndex = restaurant.getMenuCategories().indexOf(dummyCategory);
            if (categoryIndex >= 0) {
                CategoryDomain category = restaurant.getMenuCategories().get(categoryIndex);
                DishDomain dummyDish = new DishDomain();
                dummyDish.setId(dishId);

                if (category.getDishes() != null && category.getDishes().remove(dummyDish)) {
                    this.putRestaurant(restaurant);
                    return true;

                }
            }

        }
        return false;

    }

}
