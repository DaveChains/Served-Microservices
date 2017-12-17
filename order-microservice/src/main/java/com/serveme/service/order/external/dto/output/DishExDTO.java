package com.serveme.service.order.external.dto.output;

import com.serveme.service.order.domain.ExtraDomain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Davids-iMac on 21/11/15.
 */
public class DishExDTO implements Serializable {

    private long id;

    private long restaurantId;

    private long categoryId;

    private String name;

    private String description;

    private BigDecimal price;
    
    private boolean available;

    private List<ExtraDomain> extras;

    public DishExDTO(){}

    public DishExDTO(long id){ this.id = id;}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<ExtraDomain> getExtras() {
        return extras;
    }

    public void setExtras(List<ExtraDomain> extras) {
        this.extras = extras;
    }
   
    public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DishExDTO)) return false;

        DishExDTO dishExDTO = (DishExDTO) o;

        return id == dishExDTO.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "{" +
                "'categoryId':" + categoryId +
                ",'id':" + id  +
                ",'restaurantId':" + restaurantId  +
                ",'name': '" + name + '\'' +
                ",'description': '" + description + '\'' +
                ",'price':" + price +
                "}";
    }
}
