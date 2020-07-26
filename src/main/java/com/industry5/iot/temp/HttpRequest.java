package com.industry5.iot.temp;

import java.util.List;

public class HttpRequest {

    private List<String> headers;
    private String body;
    private HttpMethod method;
    private String path;

    public HttpRequest(List<String> headers, String body, HttpMethod method, String path) {
        this.headers = headers;
        this.body = body;
        this.method = method;
        this.path = path;
    }

    public List<String> getHeaders() {
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
