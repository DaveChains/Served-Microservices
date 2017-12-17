package com.serveme.payment.api.dto.payment;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Edgar on 4/10/2016.
 */
public class CardInputDtoOld implements Serializable {


    @NotNull
    @Min(value = 0)
    private long id;

    @NotNull
    @NotEmpty
    private String token;

    public String getToken() {
        return token;
    }

    public long getId() {
        return id;
    }
}
