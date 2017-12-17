package com.serveme.service.order.builder;

import com.serveme.service.order.domain.ExtraDomain;
import com.serveme.service.order.domain.OrderItem;
import com.serveme.service.order.external.dto.output.DishExDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davids-iMac on 21/11/15.
 */
public class OrderItemBuilder {

    private DishExDTO dish;

    private int num;

    private String comments;
    
    private boolean starter;

    private String option;

    private List<ExtraDomain> extras;

    public OrderItemBuilder setDish(DishExDTO dish){
        this.dish = dish;
        return this;
    }

    public OrderItemBuilder setNum(int num){
        this.num = num;
        return this;
    }

    public OrderItemBuilder setComments(String comments){
        this.comments = comments;
        return this;
    }

    public OrderItemBuilder setStarter(boolean starter){
        this.starter = starter;
        return this;
    }

    public OrderItemBuilder setOption(String option) {
        this.option = option;
        return this;
    }

    public OrderItem build(){

        OrderItem orderItem = new OrderItem();
        orderItem.setDishId(dish.getId());
        orderItem.setDishName(dish.getName());
        orderItem.setDishDescription(dish.getDescription());
        orderItem.setDishPrice(dish.getPrice());
        orderItem.setNum(num);
        orderItem.setOption(option);
        orderItem.setComments(comments);
        orderItem.setStarter(starter);
        orderItem.setExtras(extras);
        return orderItem;

    }

    public OrderItemBuilder setExtras(ArrayList<ExtraDomain> extras) {
        this.extras = extras;
        return this;
    }
}
