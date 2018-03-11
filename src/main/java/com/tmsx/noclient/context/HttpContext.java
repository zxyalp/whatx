package com.tmsx.noclient.context;

import org.apache.http.client.protocol.HttpClientContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yang.zhou
 * @date 2017/11/24
 */
public class HttpContext {

    private static final Logger logger = LoggerFactory.getLogger(HttpContext.class);

    private static HttpClientContext context = null;

    private HttpContext() {

    }

    public static HttpClientContext getInstance() {
        if (context == null) {
            synchronized (HttpContext.class) {
                if (context == null) {
                    context = HttpClientContext.create();
                }
            }
        }
        return context;
    }


}
