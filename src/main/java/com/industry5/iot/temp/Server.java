package com.industry5.iot.temp;

import com.industry5.iot.temp.repo.TemperatureRepository;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final Logger log = new Logger(Server.class);

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
        HttpParser httpParser = new DefaultHttpParser();

        int workerSeq = 0;
        while (true) {
            try {
                socket = serverSocket.accept();
                String workerName = "Worker " + workerSeq + "-" + System.nanoTime();
                workerSeq++;
//                new EchoThread(socket).start();
                new HttpThread(workerName, socket, controller, httpParser).start();
            } catch (IOException e) {
                log.error("ERROR in Server: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
