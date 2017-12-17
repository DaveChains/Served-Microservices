package com.serveme.service.transfer.dto;

import java.io.Serializable;

/**
 * Created by Davids-iMac on 09/11/15.
 */
public class PostcodesIOResponse implements Serializable {


    private  int status;

    private LocationDTO result;

    public LocationDTO getResult() {
        return result;
    }

    public void setResult(LocationDTO result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PostcodesIOResponse{" +
                "status=" + status +
                ", result=" + result +
                '}';
    }
}
