package com.justin.mysightsecurity.socket;

import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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
                String read = input.readLine();//{ip:"192.168.2.30", port:"554"}
                String jsonStr = read;//"{\"ip\":\"192.168.2.30\", \"port\":\"554\"}";
                String ip="", port="";
                JSONObject obj = null;
                try {
                    obj = new JSONObject(jsonStr);
                    ip = obj.getString("ip");
                    port = obj.getString("port");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Add IP Camera Address
                Global.newIP = ip;
                Global.newPort = port;
                Global.activity.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(Global.activity, "New IP Camera:"+Global.newIP+" Port:"+Global.newPort, Toast.LENGTH_LONG).show();
                    }
                });

                //Global.updateConversationHandler.post(new updateUIThread(read));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
