package com.serveme.payment.util.impl;

import com.serveme.payment.util.Environment;
import com.serveme.payment.util.EnvironmentUtil;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentUtilImpl implements EnvironmentUtil {


    @Override
    public boolean isDevEnv() {
        return Environment.isDev();
    }

    @Override
    public boolean isStagingEnv() {
        return Environment.isStaging();
    }

    @Override
    public boolean isLiveEnv() {
        return Environment.isLive();
    }

}
