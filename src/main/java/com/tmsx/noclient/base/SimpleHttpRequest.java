package com.tmsx.noclient.base;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Http 请求封装
 *
 * @author yang.zhou
 * @date 2017/10/27
 */
public class SimpleHttpRequest {

    private String url;
    private Map<String, String> query;
    private Map<String, String> header;

    /**
     * form data
     */
    private Map<String, String> formData;

    /**
     * json data
     */
    private JSONObject jsonData;

    /**
     * multipart
     */
    private Map<String, Object> multipartData;


    public SimpleHttpRequest addQuery(String key, String value) {
        if (query == null) {
            query = new HashMap<>();
        }

        if (key == null || value == null) {
            return this;
        }

        query.put(key, value);
        return this;

    }


    public SimpleHttpRequest addHeader(String key, String value) {
        if (header == null) {
            header = new HashMap<>();
        }

        if (key == null || value == null) {
            return this;
        }
        header.put(key, value);
        return this;
    }

    public SimpleHttpRequest addHeadersMap(Map<String, String> headersMap) {
        this.header = headersMap;
        return this;
    }


    public SimpleHttpRequest addFormData(String key, String value) {
        if (formData == null) {
            formData = new HashMap<>();
        }

        if (key == null || value == null) {
            return this;
        }

        formData.put(key, value);

        return this;

    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getQuery() {
        return query;
    }

    public void setQuery(Map<String, String> query) {
        this.query = query;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public Map<String, String> getFormData() {
        return formData;
    }

    public void setFormData(Map<String, String> formData) {
        this.formData = formData;
    }

    public String getJsonData() {
        return jsonData.toJSONString();
    }

    public void setJsonData(JSONObject jsonData) {
        this.jsonData = jsonData;
    }

    public Map<String, Object> getMultipartData() {
        return multipartData;
    }

    public void setMultipartData(Map<String, Object> multipartData) {
        this.multipartData = multipartData;
    }
}
