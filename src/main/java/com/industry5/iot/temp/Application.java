package com.industry5.iot.temp;

public class Application {

    public static final int PORT = 1978;

    public static void main(String[] args) {
        Server server = new Server();
        server.startServer(PORT);
    }

}
