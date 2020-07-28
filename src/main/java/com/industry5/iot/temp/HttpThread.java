package com.industry5.iot.temp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.industry5.iot.temp.domain.Temperature;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class HttpThread extends Thread {
    public static final String NL = "\n";

    private final Socket socket;
    private final Controller controller;

    public HttpThread(Socket socket, Controller controller) {
        System.out.println("Starting HTTP thread: " + currentThread().getName());
        this.socket = socket;
        this.controller = controller;
    }

    @Override
    public void run() {

        try (DataOutputStream dataOutput = new DataOutputStream(socket.getOutputStream());
             InputStreamReader inputStream = new InputStreamReader(socket.getInputStream());
             BufferedReader reader = new BufferedReader(inputStream)) {

            HttpRequest httpRequest = parseHttpRequest(reader);
            System.out.println(httpRequest);

            HttpResponse resp = processHttpRequest(httpRequest);
            System.out.println(resp);

            writeHttpResponse(resp, dataOutput);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private HttpRequest parseHttpRequest(BufferedReader reader) throws IOException {

        List<String> list = new ArrayList<>();
        HttpMethod method;
        String path;

        String firstLine = reader.readLine();

        StringTokenizer tokenizer = new StringTokenizer(firstLine);
        method = HttpMethod.valueOf(tokenizer.nextToken());
        path = tokenizer.nextToken();

        while (true) {
            String line = reader.readLine();
            if (line == null) {
                socket.close();
                throw new IOException("Error: socket closed");
            }
            if (line.trim().length() == 0) {
                break;
            }
            list.add(line);
        }
        return new HttpRequest(list, null, method, path);
    }

    private HttpResponse processHttpRequest(HttpRequest req) throws IOException {
        switch (req.getMethod()) {
            case GET:
                return routeGetRequest(req, controller);
            default:
                throw new IOException("Unsupported HTTP method: " + req.getMethod());
        }
    }

    private HttpResponse routeGetRequest(HttpRequest req, Controller controller) {

        GsonBuilder builder = new GsonBuilder(); // TODO : move out of this place
        Gson gson = builder.serializeNulls().create();

        int responseCode = 200;
        String message = "OK";
        String body;
        String contentType = "application/json";

        if ("/ping".equals(req.getPath())) {
            contentType = "text/plain";
            body = controller.processPing();
        } else if ("/temp".equals(req.getPath())) {
//            body = gson.toJson(controller.listTemperatures().toArray(), Temperature[].class);
            body = gson.toJson(controller.listTemperatures());
        } else {
            responseCode = 404;
            message = "Not Found";
            body = "Resource not found: " + req.getPath();
        }

        //String body = "HTML page :) " + req.getPath();
        List<String> headersList = new ArrayList<>();
        headersList.add("Server: Mantas Java HTTP Server 1.0");
        headersList.add("Date:" + new Date());
        headersList.add("Content-type: " + contentType);
        headersList.add("Content-length: " + body.length());
        HttpResponse resp = new HttpResponse(responseCode, message, headersList, body);

        return resp;
    }

    private void writeHttpResponse(HttpResponse resp, DataOutputStream out) throws IOException {

        out.writeBytes("HTTP/1.1 " + resp.getStatusCode() + " " + resp.getMessage() + NL);
        for (String header : resp.getHeaders()) {
            out.writeBytes(header + NL);
        }
        out.writeBytes(NL);
        out.flush();
        out.writeBytes(resp.getBody() + NL);
        out.flush();
    }
}
