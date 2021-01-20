package com.rat6.chessonline.enternet.simple_server;

import com.rat6.chessonline.enternet.ConnectIO;
import com.rat6.chessonline.enternet.Constants;

import java.io.IOException;
import java.net.*;

import static com.rat6.chessonline.enternet.simple_server.HostAddress.getHostAddresses;

public class SimpleServer extends ConnectIO{

    private String ip;

    public SimpleServer(){
        new AcceptClientThread(serverSocket, socket);
    }

    public String getIp(){
        if(ip==null)
            return "";
        else
            return ip;
    }

    private synchronized void setConnected(boolean b){
        isConnected = b;
    }

    public synchronized boolean isConnected(){
        return isConnected;
    }




    private class AcceptClientThread implements Runnable {

        private ServerSocket serverSocket;
        private Socket socket;

        public AcceptClientThread(ServerSocket serverSocket, Socket socket){
            this.serverSocket = serverSocket;
            this.socket = socket;
            Thread t = new Thread(this);
            t.start();
        }

        @Override
        public void run(){
            try {
                serverSocket = new ServerSocket(Constants.port);
                ip = getHostAddresses()[0];
                System.out.println("Server listening on port: " + Constants.port + ". IP: "+ ip);
                socket = serverSocket.accept();
                System.out.println("client connected.");
                setConnected(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
