package com.tmsx.noclient.context;

import com.tmsx.noclient.utils.PropertyUtil;

/**
 *
 * @author yang.zhou
 * @date 2017/10/31
 */
public class TestContextHolder {

    public static final String LOGIN_SIMU_URL = PropertyUtil.getProperty("simu.url");
    public static final String CUST_ID = PropertyUtil.getProperty("simu.user");
    public static final String PASSWORD = PropertyUtil.getProperty("simu.password");

}
