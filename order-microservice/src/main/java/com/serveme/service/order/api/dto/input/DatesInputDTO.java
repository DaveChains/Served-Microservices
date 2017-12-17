package com.serveme.service.order.api.dto.input;

import java.sql.Timestamp;

/**
 * Created by Edgar on 3/13/2016.
 */
public class DatesInputDTO {
    private Timestamp initDate;

    private Timestamp endDate;

    public Timestamp getInitDate() {
        return initDate;
    }

    public void setInitDate(Timestamp initDate) {
        this.initDate = initDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }
}
