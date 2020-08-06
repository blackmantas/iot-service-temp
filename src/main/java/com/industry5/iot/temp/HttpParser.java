package com.industry5.iot.temp;

import java.io.BufferedReader;
import java.io.IOException;

public interface HttpParser {

    String HEADER_CONTENT_LENGTH = "Content-Length";

    HttpRequest parseHttpRequest(BufferedReader reader) throws IOException, EmptySocketException;
}
