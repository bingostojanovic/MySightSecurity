package com.justin.mysightsecurity.socket;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Button;
import android.widget.Toast;

import com.justin.mysightsecurity.R;
import com.justin.mysightsecurity.SuccessActivity;

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

                        Global.isConnected = true;
                        Button btn = Global.activity.findViewById(R.id.btn_scan);
                        btn.setEnabled(true);
                        //ui, db update
                        SQLiteDatabase db;
                        try {
                            db= Global.activity.openOrCreateDatabase("sight.db", Context.MODE_PRIVATE,null);
                            ContentValues val = new ContentValues();

//                        val.put("device_name", getArguments().getString("device_name"));
//                        val.put("device_id", getArguments().getString("device_id"));
                            val.put("ip_address", Global.newIP);
                            val.put("port_number", String.valueOf(Global.newPort));

                            db.insert("Device", null, val);
                            db.close();
                        }catch (Exception e) {
                            Toast.makeText(Global.activity, "Can not access database: "+ e.toString(), Toast.LENGTH_SHORT).show();
                        }
                        // Add device to DB
                        //
                        Intent intent = new Intent(Global.activity, SuccessActivity.class);
                        Global.activity.startActivity(intent);
                    }
                });

                //Global.updateConversationHandler.post(new updateUIThread(read));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
