package com.millennialslab.served.service.enums;

/**
 * Created by Davids-iMac on 15/11/15.
 */
public enum DeviceType {

    ANDROID("android"),iOS("ios");


    private String value;

    DeviceType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
