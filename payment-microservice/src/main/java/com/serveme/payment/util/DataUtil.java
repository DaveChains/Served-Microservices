package com.serveme.payment.util;

import com.google.gson.Gson;

public class DataUtil {


    private static final Gson gson = new Gson();

    public static String toJson(Object obj){
        return gson.toJson(obj);
    }

    public static<T> T fromJson(String json, Class<T> clazz){
        return gson.fromJson(json, clazz);
    }

    public static boolean isNull(Object o){
        return o == null;
    }

}
