package com.tmsx.noclient.simu.logon;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * Created by yang.zhou on 2017/10/26.
 */
public class CreateUser {

    public static void main(String[] args) {

    }


    public static void doGet(String url){
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {

            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);

            HttpEntity entity = response.getEntity();


        }catch (IOException e){
            e.printStackTrace();
        }


    }

    public static void doPost(){


    }
}
