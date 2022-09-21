package com.justin.mysightsecurity;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import android.util.Log;

public class SocketClient extends Thread {
	private GalleryFragment gallery;
	private Socket mSocket;
	private static final String TAG = "socket";
	private String mIP = "192.168.123.1";
	private int mPort = 8888;
	
	public SocketClient(GalleryFragment _gallery, String ip, int port) {
	    mIP = ip;
	    mPort = port;
		gallery = _gallery;
		start();
	}
	
	public SocketClient() {
		start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();

		mPort = 554;//554 8554 7236
		for(int i=1; i<=255; i++){
			mIP = String.format("192.168.0.%d", i);
			try {
				mSocket = new Socket();
				mSocket.connect(new InetSocketAddress(mIP, mPort), 5000); // hard-code server address
				mSocket.close();
				gallery.onIPCameraListner.onIPCameraFind(mIP, mPort);
				break;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.e(TAG, e.toString());
			}
			finally {
				try {
					mSocket.close();
					mSocket = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		gallery.onIPCameraListner.onIPCameraFind("",-1);
	}
	
	public void close() {
		if (mSocket != null) {
			try {
				mSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
