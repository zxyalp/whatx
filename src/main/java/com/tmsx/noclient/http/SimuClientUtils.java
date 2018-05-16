package com.tmsx.noclient.http;

import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yang.zhou
 * @date 2018/5/15
 */
public class SimuClientUtils extends HttpClientUtils{

    private static final Logger logger = LoggerFactory.getLogger(SimuClientUtils.class);

    private static SimuClientUtils simuClientUtils =null;

    private SimuClientUtils(){
        super();
    }

    @Override
    public SimpleHttpRequest getBasicRequest() {

        HttpClientContext context = new HttpClientContext();
        CookieStore cookieStore = new BasicCookieStore();
        context.setCookieStore(cookieStore);

        return super.getBasicRequest();
    }
}
