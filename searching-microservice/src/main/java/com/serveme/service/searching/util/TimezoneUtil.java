package com.serveme.service.searching.util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class TimezoneUtil {
	
	public static long timeZoneOffSet(String timezone){
        Calendar c = new GregorianCalendar();
        c.setTimeZone(TimeZone.getTimeZone("UTC"));

        Calendar c2 = new GregorianCalendar();
        c2.setTimeZone(TimeZone.getTimeZone(timezone));
        return c2.getTimeZone().getOffset(c.getTimeInMillis());

    }
}
