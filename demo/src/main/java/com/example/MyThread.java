package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MyThread extends Thread {

    Socket s;

    MyThread(Socket s) {
        this.s = s;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            String s1 = "";
            do {
                s1 = in.readLine();
                System.out.println(s1);

                // se s1 È riga vuota significa che la richiesta È terminata
                // quindi inizio a rispondere
                // per ora si risponde sempre con 404
                // farÒ una writebytes cosi:

                // HTTP/1.1 404 Not found
                // Content-Length: 0
                // riga vuota

            } while (!s1.equals(""));
            out.writeBytes("HTTP/1.1 404 Not found\r\n" +
                                    "Content-Length: 0\r\n"
                                    + "\r\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}