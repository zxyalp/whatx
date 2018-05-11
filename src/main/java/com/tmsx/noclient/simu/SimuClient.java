package com.tmsx.noclient.simu;

import com.tmsx.noclient.base.SimpleHeaders;
import com.tmsx.noclient.base.HttpClientUtils;
import com.tmsx.noclient.base.SimpleHttpRequest;
import com.tmsx.noclient.base.SimpleHttpResponse;
import com.tmsx.noclient.context.TestContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 *
 * @author yang.zhou
 * @date 2017/11/23
 */
@Component
public class SimuClient {

    private static final Logger logger = LoggerFactory.getLogger(SimuClient.class);

    @Autowired
    private SimpleHeaders simpleHeaders;

    public void login(){
        SimpleHttpRequest request = HttpClientUtils.getInstance().getBasicRequest();
        request.setUrl(TestContextHolder.LOGIN_SIMU_URL);

        System.out.println(simpleHeaders.getHeaders());
        Map<String, String> formData =new HashMap<>();
        formData.put("idNo", "420101199501013282");
        formData.put("idType", "0");
        formData.put("loginErr", "0");
        formData.put("password", "qq1111");

        request.setHeader(simpleHeaders.getHeaders());
        request.setFormData(formData);

        SimpleHttpResponse response = HttpClientUtils.getInstance().doPost(request);

        System.out.println(response.getStatusLine());

        System.out.println(response.getMessageBody());


    }
}
