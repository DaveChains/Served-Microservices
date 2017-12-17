package com.serveme.service.searching.exception;

/**
 * Created by Davids-iMac on 30/10/15.
 */
public class CategoryNotFoundException extends RestaurantNotFoundException{

    private long categoryId;

    public CategoryNotFoundException(long restaurantId, long categoryId){
        super(restaurantId);
        this.categoryId = restaurantId;
    }

    public long getCategoryId() {
        return categoryId;
    }
}
