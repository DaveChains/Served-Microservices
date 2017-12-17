package com.serveme.service.order.domain;

import com.serveme.service.order.util.persistence.mongo.BaseEntity;

import javax.persistence.Entity;

/**
 * Created by Davids-iMac on 18/11/15.
 */
@Entity(name = "test")
public class TestDomain extends BaseEntity {

    private String key;

    private String value;

    public TestDomain(){}

    public TestDomain(String key, String value){
        this.key = key;
        this.value = value;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
