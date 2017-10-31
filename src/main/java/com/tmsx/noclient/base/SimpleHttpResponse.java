package com.tmsx.noclient.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Http Response
 *
 * @author yang.zhou
 * @date 2017/10/27
 */
public class SimpleHttpResponse {

    private int statusCode;
    private Map<String, List<String>> headers = new HashMap<>();
    private String messageBody;

    public SimpleHttpResponse() {

    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }
}
