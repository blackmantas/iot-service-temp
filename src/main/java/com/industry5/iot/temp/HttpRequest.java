package com.industry5.iot.temp;

import java.util.List;
import java.util.Map;

public class HttpRequest {

    private Map<String, String> headers;
    private String body;
    private HttpMethod method;
    private String path;

    public HttpRequest(Map<String, String> headers, String body, HttpMethod method, String path) {
        this.headers = headers;
        this.body = body;
        this.method = method;
        this.path = path;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "headers=" + headers +
                ", body='" + body + '\'' +
                ", method=" + method +
                ", path='" + path + '\'' +
                '}';
    }
}
