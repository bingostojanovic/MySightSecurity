package com.justin.mysightsecurity.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

class CommunicationThread implements Runnable {

    private Socket clientSocket;

    private BufferedReader input;

    public CommunicationThread(Socket clientSocket) {

        this.clientSocket = clientSocket;

        try {

            this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {

        while (!Thread.currentThread().isInterrupted()) {

            try {

                String read = input.readLine();

                Global.updateConversationHandler.post(new updateUIThread(read));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
