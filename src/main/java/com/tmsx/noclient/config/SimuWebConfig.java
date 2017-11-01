package com.tmsx.noclient.config;

import com.tmsx.noclient.utils.PropertyUtil;

/**
 * Created by yang.zhou on 2017/11/1.
 */
public class SimuWebConfig {

    public static final String LOGIN_SIMU_URL = PropertyUtil.getProperty("simu.url");
    public static final String CUST_ID = PropertyUtil.getProperty("simu.user");
    public static final String PASSWORD = PropertyUtil.getProperty("simu.password");


}
