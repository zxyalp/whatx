package com.tmsx.noclient.config;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.tmsx.noclient.helper.SimuClientHelper;
import com.tmsx.noclient.utils.PropertyUtils;

import java.util.List;

/**
 * Created by yang.zhou on 2017/12/4.
 */
public class SimuClientConfig {

    public static final String TRADE_LOGIN_URL = PropertyUtils.getProperty("simu.url");
    public static final String USER_NAME = PropertyUtils.getProperty("simu.user");
    public static final String USER_PASSWORD = PropertyUtils.getProperty("simu.password");




    private static String ACCESS_TOKEN;
    private static String LOGIN_RESPONSE;

    public static String getAccessToken(){
        if (ACCESS_TOKEN == null){
            String cookie="";
            List<String> CookieList = SimuClientHelper.getLoginResponse().getAllHeaders().get("Set-Cookie");
            for (String str: CookieList) {
                cookie = cookie + str.split(";")[0];
            }
            ACCESS_TOKEN =cookie ;
        }
        return ACCESS_TOKEN;
    }

    public static String getLoginResponse(){
        if (LOGIN_RESPONSE==null){
            LOGIN_RESPONSE = SimuClientHelper.getLoginResponse().getMessageBody();
        }

        return LOGIN_RESPONSE;
    }

}
