package com.tmsx.noclient.base;

import com.alibaba.fastjson.JSONObject;
import com.tmsx.noclient.context.HttpContext;
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
import org.springframework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
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

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    private static HttpClientUtils httpClientUtils = null;

    private HttpClientUtils() {
    }

    public static HttpClientUtils getInstance() {
        if (httpClientUtils == null) {
            synchronized (HttpClientUtils.class) {
                if (httpClientUtils == null) {
                    httpClientUtils = new HttpClientUtils();
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
            response = execute(httpMethod);

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
     * Http Get请求
     */
    public SimpleHttpResponse doGet(String url, String token) {
        SimpleHttpRequest request = getBasicRequest(token);
        request.setUrl(url);
        return doGet(request);
    }


    public SimpleHttpResponse doGet(String url) {
        SimpleHttpRequest request = getBasicRequest();
        request.setUrl(url);
        return doGet(request);
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

            HttpEntity entity = httpMethod.getEntity();

            entity.writeTo(new FileOutputStream(new File("post.json")));
            response = execute(httpMethod);

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


    public SimpleHttpResponse doPost(String httUrl, Map<String, String> paramMap) {
        SimpleHttpRequest request = getBasicRequest();
        request.setUrl(httUrl);
        request.setFormData(paramMap);
        return doPost(request);
    }


    public SimpleHttpRequest getBasicRequest(String aToken) {

        SimpleHttpRequest request = new SimpleHttpRequest();
        request.addHeader("Cookie", aToken);
        return request;
    }

    public SimpleHttpRequest getBasicRequest() {
        return getBasicRequest(null);
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
     *
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

            httpMethod.setEntity(new UrlEncodedFormEntity(nameValuePairList, Charset.forName("utf-8")));

        } else if (request.getJsonData() != null) {
            logger.info(new StringEntity(request.getJsonData(), ContentType.APPLICATION_JSON).toString());
            httpMethod.setEntity(new StringEntity(request.getJsonData(), ContentType.APPLICATION_JSON));
        } else if (request.getMultipartData() != null) {
            Map<String, Object> multiData = request.getMultipartData();

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();

            if (multiData != null && !multiData.isEmpty()) {
                for (String name : multiData.keySet()) {
                    Object value = multiData.get(name);

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


    private SimpleHttpResponse execute(HttpRequestBase method) throws Exception {

        return execute(method, null, null);
    }

    private SimpleHttpResponse execute(HttpRequestBase method, HttpClientContext context) throws Exception{
        return execute(method, context, null);
    }

    private SimpleHttpResponse execute(HttpRequestBase method, RequestConfig config) throws Exception{
        return execute(method, null, config);
    }

    private SimpleHttpResponse execute(HttpRequestBase method, HttpClientContext context,
                                       RequestConfig config) throws Exception {

        SimpleHttpResponse simpleHttpResponse = new SimpleHttpResponse();

        CloseableHttpClient httpClient = HttpClients.createDefault();

        method.setConfig(config);

        CloseableHttpResponse response = httpClient.execute(method, context);

        HttpEntity entity = response.getEntity();

        StatusLine statusLine = response.getStatusLine();

        String entityBody = EntityUtils.toString(entity, "utf-8");

        EntityUtils.consume(entity);

        simpleHttpResponse.setAllHeaders(paresRespnseHeaders(response));
        simpleHttpResponse.setStatusLine(statusLine);
        simpleHttpResponse.setMessageBody(entityBody);

        return simpleHttpResponse;

    }


    private Map<String, List<String>> paresRespnseHeaders(HttpResponse response) {
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
