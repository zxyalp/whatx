package com.tmsx.noclient.config;

import com.tmsx.noclient.utils.PropertyUtil;

/**
 * Created by yang.zhou on 2017/12/4.
 */
public class SimuClientConfig {

    public static final String TRADE_LOGIN_URL = PropertyUtil.getProperty("simu.url");
    public static final String USER_NAME = PropertyUtil.getProperty("simu.user");
    public static final String USER_PASSWORD = PropertyUtil.getProperty("simu.password");
}
