package com.millennialslab.served.service.util.exception.http;

/**
 * Created by Davids-iMac on 19/06/15.
 */
public abstract class BaseHTTPException extends RuntimeException{

    private String beautyMessage;

    public BaseHTTPException(String logMsg, String beautyMsg){
        super(logMsg);
        this.beautyMessage = beautyMsg;
    }


    public String getBeautyMessage() {
        return beautyMessage;
    }

    public String getLogMessage(){
        return this.getMessage();
    }

}
