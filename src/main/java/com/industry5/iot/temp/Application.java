package com.industry5.iot.temp;

public class Application {

   public static final int PORT = 1978;

    public static void main(String[] args) {
        System.out.println("Simple HTTP/TCP server started.");
        System.out.println("Copyright (c) 2020 Mantas");
        Server server = new Server();
        server.startServer(PORT);
    }

}
