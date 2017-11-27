package com.tmsx.noclient.context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.protocol.HttpClientContext;

/**
 *
 * @author yang.zhou
 * @date 2017/11/24
 */
public class HttpContext {

    private static final Log logger = LogFactory.getLog(HttpContext.class);

    private static HttpClientContext context = null;

    private HttpContext(){

    }

    public static HttpClientContext getInstance(){
        if (context == null ){
            synchronized (HttpContext.class){
                if (context == null){
                    context = HttpClientContext.create();
                }
            }
        }
        return context;
    }




}
