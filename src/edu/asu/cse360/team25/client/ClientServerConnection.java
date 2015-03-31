package edu.asu.cse360.team25.client;

import java.io.IOException;
import java.net.Socket;

import edu.asu.cse360.team25.protocol.Connection;

public abstract class ClientServerConnection extends Connection {

	protected Thread t; // receiving thread

	
	public ClientServerConnection(String serverAddress, int listeningPort) throws IOException {
		super();
		
		socket = new Socket(serverAddress, listeningPort);
		in = socket.getInputStream();
		out = socket.getOutputStream();
		
		t = new ClientServerReceivingThread();
	}


	public void disconnect() throws IOException {
		
		socket.shutdownOutput();
	}
	
	public void startReceiving() {
		
		t.start();
	}

	public void waitForReceivingThread() throws InterruptedException {
		
		t.join();
	}
	
	protected abstract void dispatchReceivedMsg(String msg) throws IOException;

	protected class ClientServerReceivingThread extends Thread {
		@Override
		public void run() {

			try {

				while (true) {
					
					String msg = receiveMsg();
					if(msg == null)
						break;
					
					dispatchReceivedMsg(msg);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// clean up this connection
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
}






















