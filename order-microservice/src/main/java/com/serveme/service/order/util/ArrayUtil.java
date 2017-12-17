package com.serveme.service.order.util;

import java.util.Collection;

/**
 * Created by Davids-iMac on 31/05/15.
 */
public class ArrayUtil {


    public static boolean notNull(Object[] array){
        return array !=null;
    }

    public static boolean nullEmpty(Object[] array){
        return array == null || array.length <= 0;
    }

    public static boolean nullEmpty(Collection array){return array == null || array.size()<=0;}


}
