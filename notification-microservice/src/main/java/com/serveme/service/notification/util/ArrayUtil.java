package com.serveme.service.notification.util;

import java.util.Collection;

/**
 * Created by DavidChains on 31/05/15.
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
