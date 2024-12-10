package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.InputStream;
import java.io.FileInputStream;

public class MyThread extends Thread {

    Socket s;

    MyThread(Socket s) {
        this.s = s;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            String firstLine = in.readLine();
            String s1 = "";
            String[] request = firstLine.split(" ");

            String method = request[0];
            String resource = request[1];
            String version = request[2];

            do {
                s1 = in.readLine();
                System.out.println(s1);

            } while (!s1.isEmpty());

            if (resource.equals("/")) {
                resource = "/index.html";
            }

            File file = new File("httdocs" + resource);
            if (file.exists()) {

                out.writeBytes("HTTP/1.1 200 ok\n");
                out.writeBytes("Content-Type: text/html\n");
                out.writeBytes("Content-Length:" + file.length() + " \n");
                InputStream input = new FileInputStream(file);

                byte[] buf = new byte[8192];
                int n;
                while ((n = input.read(buf)) != -1) {
                    out.write(buf, 0, n);
                }
                input.close();

            } else {
                String resopnseBody = "not found ";
                out.writeBytes("HTTP/1.1 404 not found\n");
                out.writeBytes("Content-Type: text/HTML\n");
                out.writeBytes("Content-Length:" + resopnseBody.length() + " \n");
                out.writeBytes("\n");
                out.writeBytes(resopnseBody);
            }
            s.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}