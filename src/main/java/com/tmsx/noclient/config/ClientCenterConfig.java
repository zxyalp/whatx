package com.tmsx.noclient.config;

import com.tmsx.noclient.BaseScanMarker;
import org.apache.http.client.protocol.HttpClientContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author yang.zhou
 * @date 2017/11/1
 */
@Configuration
public class ClientCenterConfig {

    @Bean
    public HttpClientContext httpClientContext(){
        return HttpClientContext.create();
    }

}
