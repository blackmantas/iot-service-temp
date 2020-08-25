package com.industry5.iot.temp;

import java.util.HashMap;
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

    private HttpResponse(Builder builder) {
        statusCode = builder.statusCode;
        message = builder.message;
        headers = builder.headers; // TODO: make `headers` immutable
        body = builder.body;
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

    // TODO write unit tests
    public static class Builder {
        private int statusCode;
        private String message;
        private final Map<String, String> headers; // HTTP headers to return
        private String body = ""; // TODO use null instead?? or Optional ??

        // TODO Create ENUM for HTTP response codes, and create constructor with it
        // Empty constructor since no required params
        public Builder() {
            headers = new HashMap<>();
        }

        public Builder(int code, String message) {
            this();
            statusCode(code).message(message);
        }

        public Builder ok() { return this.statusCode(200).message("OK"); }
        public Builder notFound() { return this.statusCode(404).message("Not Found"); }
        public Builder statusCode(int code) { statusCode = code; return this; }
        public Builder message(String message) { this.message = message; return this; }
        public Builder body(String body) {
            this.body = body;
            header("Content-length", String.valueOf(body.length()));
            return this;
        }

        public Builder headers(Map<String, String> val) { headers.putAll(val); return this; }
        public Builder header(String name, String value) { headers.put(name, value); return this; }
        public Builder contentType(String value) { headers.put("Content-type", value); return this; }

        public HttpResponse build() {
            assertValid();
            return new HttpResponse(this);
        }

        private void assertValid() {
            if (statusCode == 0) throw new IllegalStateException("statusCode must not be 0");
            if (message == null) throw new IllegalStateException("message must not be null");
        }
    }
}
