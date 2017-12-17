package com.serveme.service.order.util.persistence.mongo;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Davids-iMac on 17/08/14.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseEntity implements Serializable, IPersistentObject {

    @Id
    protected String id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o==null || !(o instanceof BaseEntity)) return false;

        BaseEntity that = (BaseEntity) o;

        if (!id.equals(that.id)) return false;

        return true;
    }



    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
