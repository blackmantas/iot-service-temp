package com.industry5.iot.temp;

import java.util.List;
import java.util.Map;

public class HttpResponse {
    private int statusCode;
    private String message;
    private Map<String, String> headers;
    private String body;

    public HttpResponse(int statusCode, String message, Map<String, String> headers, String body) {
        this.statusCode = statusCode;
        this.message = message;
        this.headers = headers;
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", headers=" + headers +
                ", body='" + body + '\'' +
                '}';
    }
}
