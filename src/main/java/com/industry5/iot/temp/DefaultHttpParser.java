package com.industry5.iot.temp;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class DefaultHttpParser implements HttpParser {

    private static final Logger log = new Logger(DefaultHttpParser.class);

    @Override
    public HttpRequest parseHttpRequest(BufferedReader reader) throws IOException, EmptySocketException {

        Map<String, String> headers = new HashMap<>();

        String firstLine = reader.readLine();

        if (firstLine == null) {
            throw new EmptySocketException("Error: no data in socket (worker=" + Thread.currentThread().getName() + ")");
        }

        StringTokenizer tokenizer = new StringTokenizer(firstLine);
        HttpMethod method = HttpMethod.valueOf(tokenizer.nextToken());
        String path = tokenizer.nextToken();

        StringBuilder requestBody = new StringBuilder();
        int contentLength = 0;

        String line = reader.readLine();
        while (line != null) {
            if (line.trim().length() == 0) {    //empty line found, below might be request body (payload)
                contentLength = Integer.parseInt(headers.getOrDefault(HEADER_CONTENT_LENGTH, "0"));
                if (contentLength == 0) {
                    break;
                }
                requestBody = readHttpBody(reader, contentLength);
                break;
            }
                String[] parts = line.split(": ", 2);
                headers.put(parts[0], parts[1]);

            line = reader.readLine();
        }

        log.debug(headers);

        return new HttpRequest(headers, requestBody.toString(), method, path);

    }

    private StringBuilder readHttpBody(BufferedReader reader, int contentLength) throws IOException {

        StringBuilder body = new StringBuilder();

        char[] payload = new char[contentLength];
        int len = reader.read(payload, 0, contentLength);
        body.append(payload, 0, len);

        return body;
    }
}
