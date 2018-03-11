package com.webx;

import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yang.zhou
 * @date 2017/10/26
 */
public class CustomContext {

    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpClients.createDefault();


        try {
            // Create a local instance of cookie store
            CookieStore cookieStore = new BasicCookieStore();

            //Create local HTTP context
            HttpClientContext context = HttpClientContext.create();

            context.setCookieStore(cookieStore);

            HttpPost httpPost = new HttpPost("http://192.168.221.23:8080/hb_crm/login/checkUser.do");


            List<NameValuePair> nameValuePairs = new ArrayList<>();

            nameValuePairs.add(new BasicNameValuePair("password", "1"));
            nameValuePairs.add(new BasicNameValuePair("username", "ronghua.gan"));
            nameValuePairs.add(new BasicNameValuePair("verycode", "1"));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));

            System.out.println("Excuting request:" + httpPost.getRequestLine());


            // Pass local context as a parameter
            CloseableHttpResponse response = httpClient.execute(httpPost, context);

            try {
                System.out.println("---------------------------");
                System.out.println(response.getStatusLine());
                List<Cookie> cookies = cookieStore.getCookies();
                for (Cookie cookie : cookies) {
                    System.out.println("Local cookie" + cookie);
                }
                System.out.println(EntityUtils.toString(response.getEntity()));
                EntityUtils.consume(response.getEntity());
            } finally {
                response.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
