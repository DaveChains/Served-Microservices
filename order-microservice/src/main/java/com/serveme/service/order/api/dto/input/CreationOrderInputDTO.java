package com.serveme.service.order.api.dto.input;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DavidChains on 21/11/15.
 */
public class CreationOrderInputDTO implements Serializable {


    /**
     * Number of minute after the request that indicate when the user should be at the restaurant.
     * 30 <= arrivalMinutes <= 60
     * for example, arrivalMinutes = 30, Now=T1...time at the restaurant T1+30
     */
    private int arrivalMinutes;

    /**
     * Table for. Don't confuse with split bill
     */
    private int tableFor;

    /**
     * General order comments
     */
    private String comments;

    private String observation;

    /**
     * List of dish id and number
     */
    private List<OrderedItemInputDTO> items;

    public CreationOrderInputDTO(){}

    public int getArrivalMinutes() {
        return arrivalMinutes;
    }

    public void setArrivalMinutes(int arrivalMinutes) {
        this.arrivalMinutes = arrivalMinutes;
    }

    public int getTableFor() {
        return tableFor;
    }

    public void setTableFor(int tableFor) {
        this.tableFor = tableFor;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<OrderedItemInputDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderedItemInputDTO> items) {
        this.items = items;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
