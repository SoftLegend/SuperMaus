package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HostModel {
	private DataInputStream fromPhone;
	private DataOutputStream toPhone;
	private ServerSocket host;
	private boolean isConnectedToPhone = false;
	
	// Open Server
	public HostModel() {
		try {
			host = new ServerSocket(7863);
			System.out.println("Server Name " + InetAddress.getLocalHost().getHostAddress());
		} catch (IOException ex) {
			System.out.println("Cannot connect to server \n" + ex);
		}
	}
	
	
	public void acceptClient() {
		Socket phone;
		try {
			phone = host.accept();
			fromPhone = new DataInputStream(phone.getInputStream());
			toPhone = new DataOutputStream(phone.getOutputStream()); 
			isConnectedToPhone = true;
			System.out.println("Client accepted");
			beginConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void beginConnection() {
		
		try {
			toPhone.writeUTF("CONNECTED");
			while (isConnectedToPhone) {
				// Processing Phone Signals from here
				String fromPhoneSignal = fromPhone.readUTF();
				System.out.println("From Client: " + fromPhoneSignal);
			}
		} catch (Exception ex) {
			
		}
	}
}
