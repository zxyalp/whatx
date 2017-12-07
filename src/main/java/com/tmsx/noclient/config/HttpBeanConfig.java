package com.tmsx.noclient.config;

import org.apache.http.client.protocol.HttpClientContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

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

}
