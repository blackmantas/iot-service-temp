package com.industry5.iot.temp;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class HttpThread extends Thread {
    private final Socket socket;
    private List<String> list;

    public HttpThread(Socket socket) {
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
                list = new ArrayList<>();

                while (true) {
                    line = brinp.readLine();
                    if (line == null) {
                        socket.close();
                        return;
                    }
                    if (line.trim().length() == 0) {
                        break;
                    }
                    list.add(line);
                }
                for (int i = 0; i < list.size(); i++) {
                    String s = list.get(i);
                    System.out.println(s);
                }
                String firstLine = list.get(0);
                StringTokenizer parse = new StringTokenizer(firstLine);
                String method = parse.nextToken().toUpperCase();

                String path = parse.nextToken().toLowerCase();

                System.out.println(path);
                System.out.println(method);

                 /*else{
                    out.writeBytes(line + "\n\r");
                    out.flush();
                }*/
//                System.out.println(line);

            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

        }

    }
}
