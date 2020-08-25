package com.industry5.iot.temp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.industry5.iot.temp.domain.Temperature;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.industry5.iot.temp.HttpParser.HEADER_CONTENT_LENGTH;

public class HttpThread extends Thread {
    private static final Logger log = new Logger(HttpThread.class);

    public static final String NL = "\n";

    private static final Pattern PING_PATTERN = Pattern.compile("^/ping/?(\\?.*)?$");
    private static final Pattern SINGLE_TEMP_PATTERN = Pattern.compile("^/temp/([a-zA-Z0-9-]+)/?(\\?.*)?$");
    private static final Pattern TEMP_LIST_PATTERN = Pattern.compile("^/temp/?(\\?.*)?$");

    private final Socket socket;
    private final Controller controller;
    private final HttpParser httpParser;
    private final String workerName;


    public HttpThread(String workerName, Socket socket, Controller controller, HttpParser httpParser) {
        super(workerName);
        this.workerName = workerName;
        log.debug("___:: Starting HTTP thread: " + currentThread().getName() + "(" + workerName + ") :: (" + socket + ", hashCode=" + socket.hashCode() + ")");
        this.socket = socket;
        this.controller = controller;
        this.httpParser = httpParser;
    }

    @Override
    public void run() {

        try (DataOutputStream dataOutput = new DataOutputStream(socket.getOutputStream());
             InputStreamReader inputStream = new InputStreamReader(socket.getInputStream());
             BufferedReader reader = new BufferedReader(inputStream)) {

            HttpRequest httpRequest = httpParser.parseHttpRequest(reader);
//            HttpRequest httpRequest = parseHttpRequest(reader);

            log.debug(httpRequest);

            HttpResponse resp = processHttpRequest(httpRequest);
            log.debug(resp);

            writeHttpResponse(resp, dataOutput);

        } catch (IOException e) {
            log.error("HttpThread.run(): [" + workerName + "]: " + e.getMessage());
            e.printStackTrace();
        } catch (EmptySocketException e) {
            log.info("Socket has no data: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private HttpResponse processHttpRequest(HttpRequest req) throws IOException {
        switch (req.getMethod()) {
            case GET:
                return routeGetRequest(req, controller);
            case POST:
                return routePostRequest(req, controller);
            case OPTIONS:
                return routeOptionsRequest(req, controller);
            default:
                throw new IOException("Unsupported HTTP method: " + req.getMethod());
        }
    }

    private HttpResponse routeGetRequest(HttpRequest req, Controller controller) {

        GsonBuilder gsonBuilder = new GsonBuilder(); // TODO : move out of this place
        Gson gson = gsonBuilder.serializeNulls().create();

        HttpResponse.Builder builder = new HttpResponse.Builder()
                .ok()
                .headers(defaultResponseHeaders());

        Matcher matcherTemp = SINGLE_TEMP_PATTERN.matcher(req.getPath());

        if (PING_PATTERN.matcher(req.getPath()).matches()) {
            builder.contentType("text/plain")
                    .body(controller.processPing());

        } else if (matcherTemp.matches()) {
            String tempId = matcherTemp.group(1);
            Temperature temperature = controller.getTemperature(tempId);
            builder.contentType("application/json")
                    .body(gson.toJson(temperature));
        } else if (TEMP_LIST_PATTERN.matcher(req.getPath()).matches()) {
            List<Temperature> temperatureList = controller.listTemperatures();
            builder.contentType("application/json")
                    .body(gson.toJson(temperatureList));
        } else {
            builder.notFound()
                    .contentType("text/plain")
                    .body("Resource not found: " + req.getPath());
        }

        HttpResponse resp = builder.build();
        return resp;
    }

    private HttpResponse routePostRequest(HttpRequest req, Controller controller) {

        GsonBuilder gsonBuilder = new GsonBuilder(); // TODO : move out of this place
        Gson gson = gsonBuilder.create();

        HttpResponse.Builder builder = new HttpResponse.Builder().ok().headers(defaultResponseHeaders());

        if (TEMP_LIST_PATTERN.matcher(req.getPath()).matches()) {
            Type listType = new TypeToken<ArrayList<Temperature>>() {
            }.getType();
            List<Temperature> temperatureList = new Gson().fromJson(req.getBody(), listType);

            if (temperatureList == null) { // TODO can GSON return empty list?
                temperatureList = Collections.emptyList();
            }

            List<Temperature> result = controller.createTemperature(temperatureList);
            builder.contentType("application/json").body(gson.toJson(result));
        } else {
            builder.contentType("text/plain").notFound().body("Resource not found: " + req.getPath());
        }

        HttpResponse resp = builder.build();

        return resp;
    }

    // experimental yet vulnerable method, for CORS testing only
    private HttpResponse routeOptionsRequest(HttpRequest req, Controller controller) {
        HttpResponse.Builder builder = new HttpResponse.Builder();

        builder.statusCode(204)
                .message("No content")
                .contentType("application/json")
                .headers(defaultResponseHeaders())
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-type");

        HttpResponse resp = builder.build();

        return resp;
    }

    private Map<String, String> defaultResponseHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Server", "Mantas Java HTTP Server 1.0");
        headers.put("Date", new Date().toString());
        headers.put("Access-Control-Allow-Origin", "*");    // HACK, for testing only
        return headers;
    }

    private void writeHttpResponse(HttpResponse resp, DataOutputStream out) throws IOException {

        out.writeBytes("HTTP/1.1 " + resp.getStatusCode() + " " + resp.getMessage() + NL);
        Map<String, String> headers = resp.getHeaders();
        for (String key : headers.keySet()) {
            String outputLine = key + ": " + headers.get(key);
            log.debug("header => " + outputLine);
            out.writeBytes(outputLine + NL);
        }
        out.writeBytes(NL);
        String responsePacket = resp.getBody() == null ? "" : resp.getBody();
        log.debug("responsePacket.length()=" + responsePacket.length());
        log.debug("responsePacket=" + responsePacket);
        out.writeBytes(responsePacket + NL);
        out.flush();
    }
}

