package com.industry5.iot.temp;

import java.util.List;

public class HttpResponse {
    private int statusCode;
    private String message;
    private List<String> headers;
    private String body;

    public HttpResponse(int statusCode, String message, List<String> headers, String body) {
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

    public List<String> getHeaders() {
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
