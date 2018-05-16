package com.tmsx.noclient.helper;

import com.alibaba.fastjson.JSONObject;
import com.tmsx.noclient.config.SimuClientConfig;
import com.tmsx.noclient.http.HttpClientUtils;
import com.tmsx.noclient.http.SimpleHttpRequest;
import com.tmsx.noclient.http.SimpleHttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author yang.zhou
 * @date 2017/10/31
 */
public class SimuClientHelper {

    private static final Logger logger = LoggerFactory.getLogger(SimuClientHelper.class);


    public static SimpleHttpResponse getLoginResponse(){


        SimpleHttpRequest request = new SimpleHttpRequest();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("User-Agent", "Mozilla/5.0 Chrome/66.0.3359.139 Safari/537.36");
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        headers.put("Accept-Encoding", "gzip, deflate");
        headers.put("Accept-Language", "zh-CN,zh;q=0.9");


        Map<String, String> reqBody = new HashMap<>();
        reqBody.put("idNo", SimuClientConfig.USER_NAME);
        reqBody.put("idType", "0");
        reqBody.put("loginErr", "0");
        reqBody.put("password", SimuClientConfig.USER_PASSWORD);
        request.setFormData(reqBody);
        request.setHeader(headers);
        request.setUrl(SimuClientConfig.TRADE_LOGIN_URL);

        return HttpClientUtils.getInstance().doPost(request);



    }




}
