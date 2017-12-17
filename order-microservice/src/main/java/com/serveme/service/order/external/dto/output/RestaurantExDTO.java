package com.serveme.service.order.external.dto.output;


import com.serveme.service.order.domain.RestaurantOrder;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Davids-iMac on 25/10/15.
 */
public class RestaurantExDTO extends RestaurantOrder implements Serializable {

    private Map<String, List<DishExDTO>> menu;

    public Map<String, List<DishExDTO>> getMenu() {
        return menu;
    }

    public void setMenu(Map<String, List<DishExDTO>> menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {

        final StringBuilder menuBuilder = new StringBuilder("\n");
        if (menu != null) {
            Map.Entry<String, List<DishExDTO>> entry;
            Iterator<Map.Entry<String, List<DishExDTO>>> iter = menu.entrySet().iterator();
            while (iter.hasNext()) {
                entry = iter.next();
                String category = entry.getKey();
                List<DishExDTO> dishList = entry.getValue();
                menuBuilder.append("{\n'category': '" + category.toUpperCase() + "'");
                menuBuilder.append("\n'dishes':[");
                dishList.forEach(dish -> menuBuilder.append("\n\t" + dish.toString()));
                menuBuilder.append("\n]");
            }

        } else {
            menuBuilder.append("NO MENU");
        }

        return "{" +
                "\n'id' : " + this.getId() +
                "\n'menu':[\n" + menuBuilder.toString() + "]" +
                "\n}";
    }
}
