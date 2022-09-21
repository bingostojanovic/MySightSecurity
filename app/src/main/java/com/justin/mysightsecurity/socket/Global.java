package com.justin.mysightsecurity.socket;

import android.widget.TextView;
import android.os.Handler;
import java.net.ServerSocket;
import java.net.Socket;

public class Global {
    static public ServerSocket serverSocket;

    static public Handler updateConversationHandler;

    static public Thread serverThread = null;

    static public TextView text;

    static public int SERVERPORT = 6000;
}
