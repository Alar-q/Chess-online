package com.rat6.chessonline.enternet.simple_client;

import com.rat6.chessonline.enternet.ConnectIO;
import com.rat6.chessonline.enternet.Constants;

import java.net.InetAddress;
import java.net.Socket;


public class SimpleClient extends ConnectIO{
    public boolean connect(String ip){
        try{
            System.out.println("Connecting. ");

            InetAddress address = InetAddress.getByName(ip);
            socket = new Socket(address, Constants.port);

            return isConnected=loadIO();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
