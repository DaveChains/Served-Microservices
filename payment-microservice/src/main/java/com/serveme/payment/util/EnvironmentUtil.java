package com.serveme.payment.util;

public interface EnvironmentUtil {
    /**
     * ENVIRONMENT
     */
    boolean isDevEnv();

    boolean isStagingEnv();

    boolean isLiveEnv();
}
