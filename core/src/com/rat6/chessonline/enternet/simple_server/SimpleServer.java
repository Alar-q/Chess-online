package com.rat6.chessonline.enternet.simple_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.*;

import static com.rat6.chessonline.enternet.simple_server.HostAddress.getHostAddresses;

public class SimpleServer {
    public static final int port = 15432;
    public final static String safe_word = "stop";

    private ServerSocket serverSocket;
    private Socket client;

    public boolean connected = false, available = false;

    private BufferedReader input;
    private PrintWriter output;

    private String ip;

    public class TT extends Thread{
        @Override
        public void run(){
            try {
                client = serverSocket.accept();
                connected = true;
            } catch (IOException e) {
                System.out.println("LOL ty daun");
                e.printStackTrace();
            }
        }
    }

    private TT tt;

    public static void main(String[] args) {
        SimpleServer server = new SimpleServer();
        server.startSession();
    }
    public SimpleServer(){
        startSession();
    }

    private boolean startSession(){
        try {
            serverSocket = new ServerSocket(port);
            ip = getHostAddresses()[0];
            System.out.println("Server listening on port: "+port+". IP: "+ ip);

            tt = new TT();
            tt.start();

            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void connect() {
        try {

            System.out.println("client connected.");

            output = new PrintWriter(client.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println("Reader and writer created. ");

            send2client("Hello");

            available = true;
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void send2client(String message){
        System.out.println("Server: " + message);
        output.println(message);
    }

    public void update(){
        try {
            if (input.ready()) {
                String inString = input.readLine();

                if (inString.equals(safe_word)) release();

                System.out.println("Client: " + inString);

            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void release(){
        try{
            output.println(safe_word);
            input.close();
            output.close();
            client.close();
            serverSocket.close();
            tt.stop();
        }catch(IOException e){e.printStackTrace();}
    }

    public String getIp(){
        if(ip==null)
            return "";
        else
            return ip;
    }
}
