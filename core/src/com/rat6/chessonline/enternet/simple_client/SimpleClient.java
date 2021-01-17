package com.rat6.chessonline.enternet.simple_client;

import com.rat6.chessonline.enternet.simple_server.SimpleServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import static com.rat6.chessonline.enternet.simple_server.SimpleServer.safe_word;

public class SimpleClient {


    private boolean isConnected;

    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;

    public SimpleClient(String ip){
        isConnected = connect(ip);
    }

    public boolean connect(String ip){
        try{
            System.out.println("Connecting. ");

            InetAddress address = InetAddress.getByName(ip);
            socket = new Socket(address, SimpleServer.port);
            System.out.print("Connection. ");

            output = new PrintWriter(socket.getOutputStream(), true);

            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Requesting output to: " + address.getHostAddress());
            output.println("Hello");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void update(){
        if(!isConnected){return;}
        try {
            if (input.ready()) {
                String serverStr = input.readLine();
                System.out.println("Server: " + serverStr);
                if (serverStr.equals(safe_word)) release();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void release(){
        try{
            output.println(safe_word);
            isConnected = false;
            input.close();
            output.close();
            socket.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean isConnected(){
        return isConnected;
    }
}
