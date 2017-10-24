package com.webx;


import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;


/**http test
 * Created by yang.zhou on 2017/10/24.
 */
public class HttpDemo {

    public static void main(String[] args) {

    }

    public static void httpGet(){
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://www.baidu.com/");

        try {

            CloseableHttpResponse response1 = client.execute(httpGet);

        }catch (IOException e){

        }


    }
}
