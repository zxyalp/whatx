package com.webx;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.cookie.BasicClientCookie2;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import sun.net.www.http.HttpClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yang.zhou on 2017/10/26.
 */
public class WebClient {

    public static void main(String[] args) throws Exception {

        BasicCookieStore cookieStore = new BasicCookieStore();


        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

        try {
            HttpPost httpPost = new HttpPost("https://www.baidu.com");

            List<NameValuePair> nameValuePairs = new ArrayList<>();

            nameValuePairs.add(new BasicNameValuePair("password", "1"));
            nameValuePairs.add(new BasicNameValuePair("username", "ronghua.gan"));
            nameValuePairs.add(new BasicNameValuePair("verycode", "1"));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));

            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();

                System.out.println("Login form get:" + response.getStatusLine());


                EntityUtils.consume(entity);

                System.out.println("Initial set of cookie:");

                List<Cookie> cookies = new ArrayList<>();
                if (cookies.isEmpty()) {
                    System.out.println("None");
                } else {
                    for (Cookie cookie : cookies) {
                        System.out.println("-" + cookie);
                    }
                }
            } finally {
                response.close();
            }

        } finally {
            httpClient.close();
        }

    }
}
