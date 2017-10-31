package com.tmsx.noclient.base;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * HTTP 请求通用客户端封装
 * Created by yang.zhou on 2017/10/30.
 */
public class HttpClientUtils {

    private final static Log logger = LogFactory.getLog(HttpClientUtils.class);

    private static HttpClientUtils httpClientUtils;

    private HttpClientUtils() {
    }

    public static HttpClientUtils getInstance() {
        if (httpClientUtils == null) {
            return new HttpClientUtils();
        }
        return httpClientUtils;
    }

    /**
     * Http GET接口
     */
    public SimpleHttpResponse doGet(SimpleHttpRequest request) {
        SimpleHttpResponse response = null;
        try {
            HttpGet httpMethod = new HttpGet();

            buildQueryParams(httpMethod, request);

            buildHeaderParams(httpMethod, request);

            response = executeInternal(httpMethod);

        } catch (URISyntaxException u) {
            logger.error("UURISyntaxException.", u);
            throw new RuntimeException("URISyntaxException.", u);

        } catch (Exception e) {
            logger.error("Error in invoking HTTP API.", e);
            throw new RuntimeException("Error in invoking HTTP API", e);
        }
        return response;
    }


    /**
     * Http Post请求封装
     */

    public SimpleHttpResponse doPost(SimpleHttpRequest request) {
        SimpleHttpResponse response;
        try {
            HttpPost httpMethod = new HttpPost();

            buildQueryParams(httpMethod, request);

            buildHeaderParams(httpMethod, request);

            buildEntityBody(httpMethod, request);

            response = executeInternal(httpMethod);

        } catch (Exception e) {
            logger.error("Error in invoking HTTP API", e);
            throw new RuntimeException("Error in invoking HTTP API", e);
        }

        return response;
    }


    public SimpleHttpResponse doPost(String token, String httpUrl, JSONObject jsonObject) {
        SimpleHttpRequest request = getBasicRequest(token);
        request.setUrl(httpUrl);
        request.setJsonData(jsonObject);
        request.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
        return doPost(request);
    }

    public SimpleHttpRequest getBasicRequest() {
        return getBasicRequest(null);
    }


    public SimpleHttpRequest getBasicRequest(String accessToken) {
        SimpleHttpRequest request = new SimpleHttpRequest();
        request.addHeader("Cookie: ", accessToken);
        return request;
    }


    /**
     * 1、build查询参数
     */

    private void buildQueryParams(HttpRequestBase requestBase, SimpleHttpRequest request) throws URISyntaxException {

        URIBuilder uriBuilder = new URIBuilder(request.getUrl());

        if (request.getQuery() == null) {
            requestBase.setURI(uriBuilder.build());
            return;
        }

        Map<String, String> query = request.getQuery();
        for (String paramName : query.keySet()) {
            String paramValue = query.get(paramName);
            uriBuilder.addParameter(paramName, paramValue);
        }

        requestBase.setURI(uriBuilder.build());
    }

    /**
     * 2、build header param
     */

    private void buildHeaderParams(HttpRequestBase requestBase, SimpleHttpRequest request) throws URISyntaxException {

        if (request.getHeader() == null) {
            return;
        }

        Map<String, String> headers = request.getHeader();

        for (String headName : headers.keySet()) {
            String headValue = headers.get(headName);
            requestBase.addHeader(headName, headValue);
        }

    }


    private void buildEntityBody(HttpEntityEnclosingRequestBase httpMethod, SimpleHttpRequest request) {
        if (request.getFormData() != null) {
            List<BasicNameValuePair> nameValuePairList = new ArrayList<>();

            Map<String, String> formEntityMap = request.getFormData();

            if (!formEntityMap.isEmpty()) {
                for (String key : formEntityMap.keySet()) {
                    String value = formEntityMap.get(key);
                    BasicNameValuePair valuePair = new BasicNameValuePair(key, value);
                    nameValuePairList.add(valuePair);
                }
            }
            try {
                httpMethod.setEntity(new UrlEncodedFormEntity(nameValuePairList, "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else if (request.getJsonData() != null) {

            httpMethod.setEntity(new StringEntity(request.getJsonData(), ContentType.APPLICATION_JSON));

        } else if (request.getMultipartData() != null) {
            Map<String, Object> multData = request.getMultipartData();

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();


            if (multData != null && !multData.isEmpty()) {
                for (String name : multData.keySet()) {
                    Object value = multData.get(name);

                    if (value instanceof String) {

                        StringBody stringBody = new StringBody((String) value, ContentType.TEXT_PLAIN);
                        builder.addPart(name, stringBody);
                    } else if (value instanceof File) {
                        FileBody fileBody = new FileBody((File) value, ContentType.TEXT_XML);
                        builder.addPart(name, fileBody);
                    }
                }
            }

            httpMethod.setEntity(builder.build());
        }

    }


    private SimpleHttpResponse executeInternal(HttpRequestBase method) throws Exception {

        return executeInternal(method, null);
    }


    private SimpleHttpResponse executeInternal(HttpRequestBase method, HttpClientContext context) throws Exception {
        SimpleHttpResponse simpleHttpResponse = new SimpleHttpResponse();

        CloseableHttpClient httpClient = HttpClients.createDefault();

        CloseableHttpResponse response = httpClient.execute(method, context);

        HttpEntity entity = response.getEntity();

        int statusCode = response.getStatusLine().getStatusCode();

        Map<String, List<String>> headers = getResponseHeaders(response);

        String entityBody = EntityUtils.toString(entity, "utf-8");


        EntityUtils.consume(entity);

        simpleHttpResponse.setHeaders(headers);
        simpleHttpResponse.setStatusCode(statusCode);
        simpleHttpResponse.setMessageBody(entityBody);

        return simpleHttpResponse;

    }

    private String execute(HttpRequestBase httpMethod) throws Exception {
        SimpleHttpResponse simpleHttpResponse = executeInternal(httpMethod, null);

        int statusCode = simpleHttpResponse.getStatusCode();

        if (statusCode >= HttpStatus.SC_MULTIPLE_CHOICES) {
            throw new Exception("Status code rejected:" + statusCode);
        }

        return simpleHttpResponse.getMessageBody();
    }


    private Map<String, List<String>> getResponseHeaders(HttpResponse response) {
        Map<String, List<String>> respHeader = new HashMap<>();
        for (Header header : response.getAllHeaders()) {
            String headName = header.getName();
            List<String> values = respHeader.get(headName);

            if (values == null) {
                values = new ArrayList<>();
            }
            values.add(header.getValue());
            respHeader.put(headName, values);
        }

        return respHeader;
    }


    private void logRequest(HttpRequestBase method) {
        try {
            logger.info("-------------------Request-------------------");
            logger.info("请求头：" + method.getRequestLine().toString());
            Header[] headers = method.getAllHeaders();
            if (headers != null) {
                for (Header header : headers) {
                    logger.info(header.getName() + " = " + header.getValue());
                }
            }
        } catch (Exception e) {

        }
    }


}