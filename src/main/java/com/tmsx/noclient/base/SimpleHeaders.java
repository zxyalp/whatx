package com.tmsx.noclient.base;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.util.*;

/**构建一个通用的Header
 *
 * @author yang.zhou
 * @date 2017/12/6
 */
public class SimpleHeaders {

    private Map<String, String> headers;

    public void setHeader(String name, String value) {
        headers.put(name, value);
    }

    public void setHeaders(Map<String, String> headersMap){
        this.headers = headersMap;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void removeHeader(String name){
        headers.remove(name);
    }

}
