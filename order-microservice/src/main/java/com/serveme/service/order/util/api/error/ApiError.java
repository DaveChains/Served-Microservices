package com.serveme.service.order.util.api.error;

/**
 * Created by Davids-iMac on 20/11/15.
 */
public class ApiError {

    private int code;

    private String message;

    private String field;

    private Object value;

    public ApiError(){}

    public ApiError(int code, String field, String value, String message){
        this.code       = code;
        this.field = field;
        this.message    = message;
        this.value = value;
    }




    public int getCode() {
        return code;
    }

    public ApiError setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ApiError setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getField() {
        return field;
    }

    public ApiError setField(String field) {
        this.field = field;
        return this;
    }

    public Object getValue() {
        return value;
    }

    public ApiError setValue(Object value) {
        this.value = value;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApiError)) return false;

        ApiError apiError = (ApiError) o;

        if (code != apiError.code) return false;
        return !(field != null ? !field.equals(apiError.field) : apiError.field != null);

    }


    @Override
    public String toString() {
        return "ApiError{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", field='" + field + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}



