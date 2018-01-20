package com.tmsx.noclient.simu;

import com.tmsx.noclient.base.DefaultHeaders;
import com.tmsx.noclient.base.HttpClientUtils;
import com.tmsx.noclient.base.SimpleHttpRequest;
import com.tmsx.noclient.base.SimpleHttpResponse;
import com.tmsx.noclient.context.TestContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 *
 * @author yang.zhou
 * @date 2017/11/23
 */
public class SimuClient {

    private static final Logger logger = LoggerFactory.getLogger(SimuClient.class);

    @Autowired
    private DefaultHeaders defaultHeaders;

    public void login(){
        SimpleHttpRequest request = HttpClientUtils.getInstance().getBasicRequest();
        request.setUrl(TestContextHolder.LOGIN_SIMU_URL);

        System.out.println(defaultHeaders.getHeaders());
        Map<String, String> formData =new HashMap<>();
        formData.put("idNo", "110101198701011954");
        formData.put("idType", "0");
        formData.put("loginErr", "0");
        formData.put("password", "qq1111");
        request.setFormData(formData);

        SimpleHttpResponse response = HttpClientUtils.getInstance().doPost(request);

        System.out.println(response.toString());



    }
}
