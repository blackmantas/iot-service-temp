package com.industry5.iot.temp;

public class Application2 {

    public static final int PORT = 1977;

    public static void main(String[] args) {
        Server server = new Server();
        server.startServer(PORT);
    }

}
