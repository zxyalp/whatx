package com.tmsx.noclient.config;

import com.tmsx.noclient.base.DefaultHeaders;
import org.apache.http.client.protocol.HttpClientContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author yang.zhou
 * @date 2017/11/1
 */
@Configuration
@Primary
public class HttpBeanConfig {

    @Bean
    public HttpClientContext httpClientContext(){
        return HttpClientContext.create();
    }

    @Bean
    public DefaultHeaders defaultHeaders(){

        Map<String, String> headersMap = new HashMap<>();
        headersMap.put("accept", "*/*");
        headersMap.put("content-type", "application/x-www-form-urlencoded");
        headersMap.put("accept-encoding", "gzip, deflate");
        headersMap.put("user-agent", "Chrome/60.0.3112.90 Safari/537.36");
        headersMap.put("x-requested-with", "XMLHttpRequest");

        DefaultHeaders defaultHeaders = new DefaultHeaders();
        defaultHeaders.setHeaders(headersMap);

        return defaultHeaders;
    }

}
