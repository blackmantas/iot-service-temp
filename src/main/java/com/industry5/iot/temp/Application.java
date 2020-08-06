package com.industry5.iot.temp;

public class Application {

   public static final int PORT = 2000;

    public static void main(String[] args) {
        System.out.println("Simple HTTP/TCP server started.");
        System.out.println("Copyright (c) 2020 Mantas");

        // initial settings
        Logger.setLevel(Logger.Level.DEBUG);

        // start server
        Server server = new Server();
        server.startServer(PORT);
    }

}
