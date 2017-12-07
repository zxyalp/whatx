package com.tmsx.noclient.base;

import com.alibaba.fastjson.JSONObject;
import com.tmsx.noclient.context.HttpContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * HTTP 请求通用客户端封装
 *
 * @author yang.zhou
 * @date 2017/10/30
 */
public class HttpClientUtils {

    private static final Log logger = LogFactory.getLog(HttpClientUtils.class);

    private static HttpClientUtils httpClientUtils = null;

    @Autowired
    private HttpClientContext httpClientContext;


    @Autowired
    private RequestConfig requestConfig;

    private HttpClientUtils() {

    }

    public static HttpClientUtils getInstance() {
        if (httpClientUtils == null) {
            synchronized(HttpClientUtils.class){
                if (httpClientUtils == null){
                    httpClientUtils =  new HttpClientUtils();
                }
            }
        }
        return httpClientUtils;
    }

    /**
     * Http GET接口
     */
    public SimpleHttpResponse doGet(SimpleHttpRequest request) {
        SimpleHttpResponse response;
        try {
            HttpGet httpMethod = new HttpGet();

            buildQueryParams(httpMethod, request);

            buildHeaderParams(httpMethod, request);

            response = executeInternal(httpMethod);

        } catch (URISyntaxException u) {
            logger.error("URISyntaxException.", u);
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


    public SimpleHttpResponse doPost(String httpUrl, JSONObject jsonObject) {
        SimpleHttpRequest request = getBasicRequest();
        request.setUrl(httpUrl);
        request.setJsonData(jsonObject);
        request.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
        return doPost(request);
    }


    public SimpleHttpResponse doPost(String httUrl, Map<String, String> paramMap){
        SimpleHttpRequest request = getBasicRequest();
        request.setUrl(httUrl);
        request.setFormData(paramMap);
        return doPost(request);
    }


    public SimpleHttpRequest getBasicRequest() {
        return new SimpleHttpRequest();
    }


    /**
     * 1、build query param
     */

    private void buildQueryParams(HttpRequestBase requestBase, SimpleHttpRequest request) throws URISyntaxException {

        URIBuilder uriBuilder = new URIBuilder(request.getUrl());

        if (StringUtils.isEmpty(request.getQuery())) {
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


    /**
     * 3、build body
     * @param httpMethod
     * @param request
     */

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

        return executeInternal(method, httpClientContext, requestConfig);
    }


    private SimpleHttpResponse executeInternal(HttpRequestBase method, HttpClientContext context,
                                               RequestConfig requestConfig) throws Exception {

        SimpleHttpResponse simpleHttpResponse = new SimpleHttpResponse();

        CloseableHttpClient httpClient = HttpClients.createDefault();

        method.setConfig(requestConfig);

        CloseableHttpResponse response = httpClient.execute(method, context);

        HttpEntity entity = response.getEntity();

        StatusLine statusLine = response.getStatusLine();

        String entityBody = EntityUtils.toString(entity, "utf-8");

        EntityUtils.consume(entity);

        simpleHttpResponse.setAllHeaders(headersToMap(response));
        simpleHttpResponse.setStatusLine(statusLine);
        simpleHttpResponse.setMessageBody(entityBody);

        return simpleHttpResponse;

    }

    private String execute(HttpRequestBase httpMethod) throws Exception {

        SimpleHttpResponse simpleHttpResponse = executeInternal(httpMethod);

        int statusCode = simpleHttpResponse.getStatusCode();

        if (statusCode >= HttpStatus.SC_MULTIPLE_CHOICES) {
            throw new Exception("Status code rejected:" + statusCode);
        }

        return simpleHttpResponse.getMessageBody();
    }


    private Map<String, List<String>> headersToMap(HttpResponse response) {
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
}
