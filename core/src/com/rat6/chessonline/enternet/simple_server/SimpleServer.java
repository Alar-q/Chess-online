package com.rat6.chessonline.enternet.simple_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.*;

public class SimpleServer {
    private ServerSocket serverSocket;
    private Socket client;

    private final int port = 15432;
    private BufferedReader input;
    private PrintWriter output;

    public static void main(String[] args) {
        SimpleServer server = new SimpleServer();
        server.startSession();
    }

    private void startSession(){
        try {
            BufferedReader sysIn = new BufferedReader(new InputStreamReader(System.in));
            serverSocket = new ServerSocket(port);
            System.out.println("Server listening on port: "+port+". IP: "+ getHostAddresses()[0]);

            client = serverSocket.accept();
            System.out.println("client connected.");

            output = new PrintWriter(client.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println("Reader and writer created. ");
            send2client("Welcome");

            String inString;
            // read the command from the client
            while  (true) {
                if(sysIn.ready()){
                    String sCmnd = sysIn.readLine();

                    send2client(sCmnd);

                    if(sCmnd.equals("stop")) break;
                }
                if (input.ready()) {
                    inString = input.readLine();

                    if(inString.equals("stop")) break;

                    System.out.println("Client: " + inString);

                }
            }
        } catch (Exception e){e.printStackTrace();}
        finally{
            try{
                input.close();
                output.close();
                client.close();
                serverSocket.close();
            }catch(IOException e){e.printStackTrace();}
        }
    }

    private void send2client(String message){
        System.out.println("Server: " + message);
        output.println(message);
    }



    public static String[] getHostAddresses() {
        Set<String> HostAddresses = new HashSet<>();
        try {

            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            List<NetworkInterface> list = Collections.list(en);

            for (NetworkInterface ni : list) {
                if (!ni.isLoopback() && ni.isUp() && ni.getHardwareAddress() != null) {
                    for (InterfaceAddress ia : ni.getInterfaceAddresses()) {
                        if (ia.getBroadcast() != null) {  //If limited to IPV4
                            HostAddresses.add(ia.getAddress().getHostAddress());
                        }
                    }
                }
            }
        } catch (SocketException e) { }

        return HostAddresses.toArray(new String[0]);
    }
}
