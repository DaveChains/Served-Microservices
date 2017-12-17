package com.serveme.service.order.util;

import org.jasypt.digest.StandardStringDigester;
import org.jasypt.util.password.StrongPasswordEncryptor;

/**
 * Created by Davids-iMac on 23/10/15.
 */
public class SecurityUtil {



    public static String encryptString(String value){
        String encryptedValue = new StrongPasswordEncryptor().encryptPassword(value);

        return encryptedValue;
    }

    public static boolean checkEncrypted(String plainString, String encryptedString){
        return new StrongPasswordEncryptor().checkPassword(plainString, encryptedString);

    }
}
