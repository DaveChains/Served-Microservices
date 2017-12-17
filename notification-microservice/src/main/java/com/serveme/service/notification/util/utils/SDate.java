package com.serveme.service.notification.util.utils;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by DavidChains on 28/11/15.
 */
public class SDate extends  Date{


    public SDate(){
        super();
    }

    public SDate(String formattedDate){
        super();
        try{
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            Date date= df.parse(formattedDate);
            this.setTime(date.getTime());

        }catch (ParseException ex){
            throw new RuntimeException(ex.getMessage());

        }


    }


    public SDate(long milliseconds){
        super(milliseconds);
    }


    @Override
    public String toString(){

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String result = df.format(this);
        return result;
    }
}
