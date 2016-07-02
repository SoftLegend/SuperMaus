package model;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
	private SuperMouseEvent sMouseEvent;
	private MouseModel mouseModel;
	private boolean isConnectedToPhone = false;
	private ServerGui serverGui;
	// Open Server
	public HostModel(ServerGui gui, MouseModel model, SuperMouseEvent mEvent) {
		try {
			serverGui = gui;
			mouseModel = model;
			sMouseEvent = mEvent;
			host = new ServerSocket(7863);
			System.out.println("Server Name " + InetAddress.getLocalHost().getHostAddress());
			serverGui.setQRCodeImg(InetAddress.getLocalHost().getHostAddress().toString());
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
			toPhone.writeUTF("CONNECTED:" + InetAddress.getLocalHost().getHostName());
			while (isConnectedToPhone) {
				// Processing Phone Signals from here
				System.out.println("Before Reading");
				String fromPhoneSignal = fromPhone.readUTF();
				System.out.println("From Client: " + fromPhoneSignal);
				if (fromPhoneSignal.contains("Identity:")) {
					serverGui.displaySuccessfulConnectionState(fromPhoneSignal.replace("Identity:", ""));
				} 
				else if (fromPhoneSignal.contains("Action1")) {
					System.out.println("Action 1 Response");
					toPhone.writeUTF("Action1Response");
				}
				else if (fromPhoneSignal.contains("Data:")) {
					String[] data = fromPhoneSignal.replace("Data:", "").split(":");
					
					if (!data[0].equals("0")) {
						sMouseEvent.setX(sMouseEvent.getX() + Integer.parseInt(data[0]));
					} else {
						sMouseEvent.setCurrentWindowXCursorPosition();
					}
					
					if (!data[1].equals("0")) {
						sMouseEvent.setY(sMouseEvent.getY() + Integer.parseInt(data[1]));
					} else {
						sMouseEvent.setCurrentWindowYCursorPosition();
					}
					
					System.out.println("Data: " + data + " " + data[2].equals("1"));
					sMouseEvent.setPressCode(Integer.parseInt(data[2]));
					mouseModel.mouseAction(sMouseEvent);
					System.out.println("Is Connected: " + isConnectedToPhone);
				}
				//System.out.println("Is Connected: " + isConnectedToPhone);
			}
		} catch (Exception ex) {
			isConnectedToPhone = false;
		}
	}
	
	
}
