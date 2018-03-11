package com.tmsx.noclient.base;


import org.apache.http.StatusLine;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Http Response
 *
 * @author yang.zhou
 * @date 2017/10/27
 */
public class SimpleHttpResponse {

    private int statusCode;
    private StatusLine statusLine;
    private Map<String, List<String>> headers = new HashMap<>();
    private String messageBody;

    public int getStatusCode() {
        return statusLine.getStatusCode();
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusLine() {
        return statusLine.toString();
    }

    public void setStatusLine(StatusLine statusLine) {
        this.statusLine = statusLine;
    }

    public Map<String, List<String>> getAllHeaders() {
        return headers;
    }

    public void setAllHeaders(Map<String, List<String>> headersMap) {
        this.headers = headersMap;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String showResponseHeaders() {

        StringBuilder stringBuilder = new StringBuilder("Response Headers:: \n");

        if (!StringUtils.isEmpty(this.headers)) {
            for (Map.Entry<String, List<String>> entry : this.headers.entrySet()) {
                stringBuilder.append(entry.getKey()).append(": ");
                for (String item : entry.getValue()) {
                    stringBuilder.append(item);
                    if (entry.getValue().size() > 1) {
                        stringBuilder.append(", ");
                    }
                }
                stringBuilder.append("\n");
            }
        } else {
            stringBuilder.append("< 空 >\n");
        }

        return stringBuilder.toString();
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder("General:\n");

        stringBuilder.append("Status Code: ").append(getStatusLine()).append("\n").append(showResponseHeaders());
        if (!StringUtils.isEmpty(getMessageBody())) {
            stringBuilder.append("Response Body: \n").append(getMessageBody());
        }
        return stringBuilder.toString();
    }
}
