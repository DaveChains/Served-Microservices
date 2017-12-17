package com.serveme.payment.util;

public class Environment {

    private static String profile;

    /**
     * Right now only one profile
     */
    public static void setProfile(String profile){
        Environment.profile = profile;
    }

    public static boolean isDev(){
        return "dev".equals(profile.toLowerCase());
    }

    public static boolean isStaging(){
        return "staging".equals(profile.toLowerCase());
    }

    public static boolean isLive(){
        return "live".equals(profile.toLowerCase());
    }

}
