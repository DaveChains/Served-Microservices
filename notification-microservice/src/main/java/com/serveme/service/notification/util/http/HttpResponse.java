package com.serveme.service.notification.util.http;

import java.io.Serializable;

/**
 * Created by DavidChains on 12/02/16.
 */
public class HttpResponse<T>  implements Serializable{

    private int status;

    private String errorMsg;

    private T data;

    public HttpResponse(){}



    public HttpResponse(int status){
        this.status = status;
    }

    public HttpResponse(int status, T data){
        this.status = status;
        this.data = data;
    }


    public HttpResponse(int status, T data, String errorMsg){
        this.status = status;
        this.data = data;
        this.errorMsg = errorMsg;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "status=" + status +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + data +
                '}';
    }
}
