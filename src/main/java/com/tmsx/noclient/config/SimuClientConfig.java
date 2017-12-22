package com.tmsx.noclient.config;

import com.tmsx.noclient.utils.PropertyUtils;

/**
 * Created by yang.zhou on 2017/12/4.
 */
public class SimuClientConfig {

    public static final String TRADE_LOGIN_URL = PropertyUtils.getProperty("simu.url");
    public static final String USER_NAME = PropertyUtils.getProperty("simu.user");
    public static final String USER_PASSWORD = PropertyUtils.getProperty("simu.password");
}
