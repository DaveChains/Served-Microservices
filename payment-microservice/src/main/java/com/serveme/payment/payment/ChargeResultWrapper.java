package com.serveme.payment.payment;

import com.serveme.payment.util.DataUtil;

import java.util.HashMap;
import java.util.Map;

public class ChargeResultWrapper {

    private String type;

    private Object result;

    public ChargeResultWrapper() {}

    public ChargeResultWrapper(Object result) {
        this("live", result);
    }

    private ChargeResultWrapper(String type, Object result) {
        this.type = type;
        this.result = result;
    }

    public static ChargeResultWrapper testInstance() {
        Map<String, String> m = new HashMap<>();
        m.put("data", "testData");
        return new ChargeResultWrapper("test", DataUtil.toJson(m));
    }

    public Object getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "ChargeResultWrapper{" +
                "result=" + result +
                '}';
    }
}
