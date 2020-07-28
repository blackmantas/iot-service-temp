package com.industry5.iot.temp;

import com.industry5.iot.temp.repo.TemperatureRepository;

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

        Controller controller = new Controller(new TemperatureRepository());

        while (true) {
            try {
                socket = serverSocket.accept();
//                new EchoThread(socket).start();
                new HttpThread(socket, controller).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
