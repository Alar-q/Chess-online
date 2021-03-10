package com.rat6.chessonline.enternet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static com.rat6.chessonline.enternet.Constants.safe_word;

public class InetIO {

    protected Socket socket;
    protected ServerSocket serverSocket;
    protected BufferedReader input;
    protected PrintWriter output;
    public boolean isConnected = false;

    protected boolean loadIO(){
        try {
            output = new PrintWriter(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //System.out.println("Requesting output to: " + socket.getInetAddress().getHostAddress());
            output.println("Hello");
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void send(String msg){
        try {
            output.println(msg);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String read(){
        try {
            if (input.ready()) {
                String serverStr = input.readLine();
                System.out.println("Server: " + serverStr);
                if (serverStr.equals(safe_word)) release();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    public void release(){
        try{
            if(output!=null) {
                output.println(safe_word);
                output.close();
            }
            if(input!=null)
                input.close();
            if(socket!=null)
                socket.close();
            if(serverSocket!=null)
                serverSocket.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
