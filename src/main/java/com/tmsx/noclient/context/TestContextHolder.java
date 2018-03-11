package com.tmsx.noclient.context;

import com.tmsx.noclient.utils.PropertyUtils;

/**
 * @author yang.zhou
 * @date 2017/10/31
 */
public class TestContextHolder {

    public static final String LOGIN_SIMU_URL = PropertyUtils.getProperty("simu.url");
    public static final String CUST_ID = PropertyUtils.getProperty("simu.user");
    public static final String PASSWORD = PropertyUtils.getProperty("simu.password");

}
