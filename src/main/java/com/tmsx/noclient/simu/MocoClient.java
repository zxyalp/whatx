package com.tmsx.noclient.simu;

import com.alibaba.fastjson.JSONObject;
import com.tmsx.noclient.base.HttpClientUtils;
import com.tmsx.noclient.base.SimpleHeaders;
import com.tmsx.noclient.base.SimpleHttpRequest;
import com.tmsx.noclient.base.SimpleHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yang.zhou
 * @date 2018/5/4
 */
public class MocoClient {


    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SimpleHeaders simpleHeaders;

    public void testRequest() throws Exception{

        SimpleHttpRequest request = HttpClientUtils.getInstance().getBasicRequest();
        request.setUrl("http://127.0.0.1:12306/posts/new");

//        System.out.println(simpleHeaders.getHeaders());
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("content-type", "application/json");

        simpleHeaders.setHeaders(headerMap);

        Map<String, Object> jsonData =new HashMap<>();
        jsonData.put("id", 1);
        jsonData.put("title", "add new post");
        jsonData.put("content", "more post");
        request.setJsonData(new JSONObject(jsonData));

        SimpleHttpResponse response = HttpClientUtils.getInstance().doPost(request);

        System.out.println(response.toString());
    }

}
