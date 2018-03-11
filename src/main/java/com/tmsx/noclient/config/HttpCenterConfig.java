package com.tmsx.noclient.config;

import com.tmsx.noclient.SpringScanMarker;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * @author yang.zhou
 * @date 2017/11/28
 */
@Configuration
@ComponentScan(basePackageClasses = SpringScanMarker.class)
@Import({HttpBeanConfig.class})
@ImportResource("httpClientBeans.xml")
public class HttpCenterConfig {

}
