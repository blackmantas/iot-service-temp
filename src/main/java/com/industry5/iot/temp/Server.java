package com.industry5.iot.temp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public void startServer(int port) {
        ServerSocket serverSocket = null;
        Socket socket;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        while (true) {
            try {
                socket = serverSocket.accept();
//                new EchoThread(socket).start();
                new HttpThread(socket).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
