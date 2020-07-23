package com.industry5.iot.temp;

import java.io.*;
import java.net.Socket;

public class EchoThread extends Thread {
    protected Socket socket;

    public EchoThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inp = null;
        BufferedReader brinp = null;
        DataOutputStream out = null;
        InputStreamReader in;
        OutputStream outp;

        try {
            inp = socket.getInputStream();
            outp = socket.getOutputStream();

            in = new InputStreamReader(inp);
            brinp = new BufferedReader(in);
            out = new DataOutputStream(outp);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        String line;
        while (true) {
            try {
                line = brinp.readLine();
                System.out.println(line);

                if ((line == null) || line.equalsIgnoreCase("QUIT")) {
                    socket.close();
                    return;
                } else {
                    out.writeBytes(line + "\n\r");
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

        }

    }
}
