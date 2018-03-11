package com.webx;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * httpclient使用流程
 * 1. 创建HttpClient对象。
 * 2. 创建请求方法的实例，并指定请求URL。如果需要发送GET请求， 创建HttpGet对象； 如果需要发送POST请求，创建HttpPost对象。
 * 3. 如果需要发送请求参数， 可调用HttpGet、 HttpPost共同的setParams(HetpParams params)方法来添加请求参数； 对于HttpPost对象而言，
 * 也可调用setEntity(HttpEntity entity)方法来设置请求参数。
 * 4. 调用HttpClient对象的execute(HttpUriRequest request)发送请求该方法返回一个HttpResponse。
 * 5. 调用HttpResponse的getAllHeaders()、 getHeaders(String name)等方法可获取服务器的响应头； 调用HttpResponse的getEntity()方法
 * 可获取HttpEntity对象，该对象包装了服务器的响应内容。 程序可通过该对象获取服务器的响应内容。
 * 6. 释放连接。 无论执行方法是否成功， 都必须释放连接。
 *
 * @author yang.zhou
 * @date 2017/10/26
 */
public class BasicRequest {

    public static void main(String[] args) {

//        doGet();
        doPost();
    }


    public static void doGet() {
        String url = "http://192.168.221.216:15080/trade/login/login.htm?targeturl=http://192.168.221.216:4085/newpcsm/buylist.html";

        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {

            HttpGet httpGet = new HttpGet(url);

            CloseableHttpResponse response1 = httpClient.execute(httpGet);

            HttpEntity entity = response1.getEntity();

            String content = EntityUtils.toString(entity, "utf-8");

            System.out.println(httpGet.getURI());
            System.out.println(content);

        } catch (ClientProtocolException c) {
            c.fillInStackTrace();
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

    public static void doPost() {

        String url = "http://192.168.221.23:8080/hb_crm/login/checkUser.do";

        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {

            HttpPost httpPost = new HttpPost(url);

            httpPost.setHeaders(addHeaders());

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("password", "1"));
            nameValuePairs.add(new BasicNameValuePair("username", "ronghua.gan"));
            nameValuePairs.add(new BasicNameValuePair("verycode", "1"));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

            CloseableHttpResponse response = httpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();

            String responseContent = EntityUtils.toString(entity, "utf-8");

            System.out.println(httpPost.getURI());
            System.out.println(httpPost.getMethod());
            System.out.println("请求头部信息：" + Arrays.toString(httpPost.getAllHeaders()));

            System.out.println(responseContent);
            System.out.println(response);
            System.out.println(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK);
            System.out.println("返回头部信息：" + Arrays.toString(response.getAllHeaders()));

            EntityUtils.consume(entity);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void doPostHandle() {

        String url = "http://192.168.221.23:8080/hb_crm/login/checkUser.do";

        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {

            HttpPost httpPost = new HttpPost(url);

            httpPost.setHeaders(addHeaders());

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("password", "1"));
            nameValuePairs.add(new BasicNameValuePair("username", "ronghua.gan"));
            nameValuePairs.add(new BasicNameValuePair("verycode", "1"));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

            ResponseHandler<String> responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= HttpStatus.SC_OK && status < HttpStatus.SC_MULTIPLE_CHOICES) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };

            String responseBody = httpClient.execute(httpPost, responseHandler);

            System.out.println(httpPost.getURI());
            System.out.println(httpPost.getMethod());
            System.out.println("请求头部信息：" + Arrays.toString(httpPost.getAllHeaders()));

            System.out.println(responseBody);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static Header[] addHeaders() {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("accept", "*/*"));
        headers.add(new BasicHeader("content-type", "application/x-www-form-urlencoded"));
        headers.add(new BasicHeader("accept-encoding", "gzip, deflate"));
        headers.add(new BasicHeader("user-agent", "Chrome/60.0.3112.90 Safari/537.36"));
        headers.add(new BasicHeader("x-requested-with", "XMLHttpRequest"));
        return headers.toArray(new Header[0]);
    }


    public static void cookieManager() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://192.168.221.23:8080/hb_crm/login/checkUser.do");

    }
}
