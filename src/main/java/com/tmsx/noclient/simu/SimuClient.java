package com.tmsx.noclient.simu;

import com.tmsx.noclient.base.HttpClientUtils;
import com.tmsx.noclient.base.SimpleHttpRequest;
import com.tmsx.noclient.base.SimpleHttpResponse;
import com.tmsx.noclient.context.TestContextHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 *
 * @author yang.zhou
 * @date 2017/11/23
 */
public class SimuClient {
    private static final Log loggger = LogFactory.getLog(SimuClient.class);


    public static void login(){
        SimpleHttpRequest request = HttpClientUtils.getInstance().getBasicRequest();
        request.setUrl(TestContextHolder.LOGIN_SIMU_URL);
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
