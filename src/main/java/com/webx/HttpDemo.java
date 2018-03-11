package com.webx;


import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * http test
 *
 * @author yang.zhou
 * @date 2017/10/24
 */
public class HttpDemo {

    public static void main(String[] args) {

        //httpGet();


    }

    public static void httpGet() {
        CloseableHttpClient client = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet("http://192.168.221.216:4087/tms-console/tradeconsole/appointopenday/appointOpenDayIndex.htm");

        BufferedInputStream bufferIn = null;

        BufferedOutputStream buffOut = null;


        try {

            CloseableHttpResponse response1 = client.execute(httpGet);

            HttpEntity entity = response1.getEntity();

            bufferIn = new BufferedInputStream(entity.getContent());
            buffOut = new BufferedOutputStream(new FileOutputStream(new File("index.html")));


            byte[] buf = new byte[2048];

            int len = -1;

            while ((len = bufferIn.read(buf)) != -1) {

                System.out.print(new String(buf));
            }


            EntityUtils.consume(entity);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferIn != null) {
                try {
                    bufferIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (buffOut != null) {
                try {
                    bufferIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    public static void httpPost() {
        CloseableHttpClient client = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost("http://targethost/login");


        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        nvps.add(new BasicNameValuePair("username", "vip"));
        nvps.add(new BasicNameValuePair("password", "secret"));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {

            CloseableHttpResponse response2 = client.execute(httpPost);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
