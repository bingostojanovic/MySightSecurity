package com.justin.mysightsecurity.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread implements Runnable {

    public void run() {
        Socket socket = null;
        try {
            Global.serverSocket = new ServerSocket(Global.SERVERPORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (!Thread.currentThread().isInterrupted()) {
            try {

                socket = Global.serverSocket.accept();

                CommunicationThread commThread = new CommunicationThread(socket);
                new Thread(commThread).start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}