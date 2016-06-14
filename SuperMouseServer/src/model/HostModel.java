package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import view.ServerGui;

public class HostModel {
	private DataInputStream fromPhone;
	private DataOutputStream toPhone;
	private ServerSocket host;
	private boolean isConnectedToPhone = false;
	private ServerGui serverGui;
	// Open Server
	public HostModel(ServerGui gui) {
		try {
			serverGui = gui;
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
				if (fromPhoneSignal.contains("Identity: ")) {
					serverGui.displaySuccessfulConnectionState(fromPhoneSignal.replace("Identity: ", ""));
				}
			}
		} catch (Exception ex) {
			
		}
	}
	
	
}
