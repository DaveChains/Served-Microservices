package com.serveme.service.searching.util.exception;

/**
 * Created by Davids-iMac on 19/06/15.
 */
public abstract class BaseException extends RuntimeException{

    private String beautyMessage;

    public BaseException(String logMsg, String beautyMsg){
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
