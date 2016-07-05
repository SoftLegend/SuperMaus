package model;

import java.awt.Frame;
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
	private int sensitivity;
	private Socket phone;
	
	// Open Server
	public HostModel(ServerGui gui, MouseModel model, SuperMouseEvent mEvent) {
		try {
			serverGui = gui;
			mouseModel = model;
			sMouseEvent = mEvent;
			host = new ServerSocket(7863);
			System.out.println("Server Name " + InetAddress.getLocalHost().getHostAddress());
		} catch (IOException ex) {
			System.out.println("Cannot establish server \n" + ex);
			
		}
	}
	
	
	public void acceptClient() {
		
		try {
			phone = host.accept();
			fromPhone = new DataInputStream(phone.getInputStream());
			toPhone = new DataOutputStream(phone.getOutputStream());
			isConnectedToPhone = true;
			
			System.out.println("Client accepted");
			beginConnection();
			
		} catch (IOException e) {
			serverGui.setStatusLbl("Cannot establish connection! Please check your Internet connection", true);
			e.printStackTrace();
		}
		
	}
	
	public void beginConnection() {
		String phoneName = "";
		try {
			toPhone.writeUTF("CONNECTED:" + InetAddress.getLocalHost().getHostName());
			while (isConnectedToPhone) {
				// Processing Phone Signals from here
				System.out.println("Before Reading");
				sensitivity = serverGui.getSensitivityValue();
				String fromPhoneSignal = fromPhone.readUTF();
				System.out.println("From Client: " + fromPhoneSignal);
				if (fromPhoneSignal.contains("Identity:")) {
					phoneName = fromPhoneSignal.replace("Identity:", "");
					serverGui.displaySuccessfulConnectionState(phoneName);
					serverGui.activateDisconnectBtn(phone, phoneName);
					serverGui.minimizeWindow();
					
				} 
				// Process Mouse Move events
				else if (fromPhoneSignal.contains("Data:")) {
					String[] data = fromPhoneSignal.replace("Data:", "").split(":");
					
					if (!data[0].equals("0")) {
						int dataChange = Integer.parseInt(data[0]);
						sMouseEvent.setX(sMouseEvent.getX() + ((dataChange < 0) ? dataChange - sensitivity : dataChange + sensitivity));
					} else {
						sMouseEvent.setCurrentWindowXCursorPosition();
					}
					
					if (!data[1].equals("0")) {
						int dataChange = Integer.parseInt(data[1]);
						sMouseEvent.setY(sMouseEvent.getY() + ((dataChange < 0) ? dataChange - sensitivity : dataChange + sensitivity));
					} else {
						sMouseEvent.setCurrentWindowYCursorPosition();
					}
					
					System.out.println("Data: " + data + " " + data[2].equals("1"));
					sMouseEvent.setPressCode(Integer.parseInt(data[2]));
					mouseModel.mouseAction(sMouseEvent);
					System.out.println("Is Connected: " + isConnectedToPhone);
				}
				// Process Key Events
				else if (fromPhoneSignal.contains("Key:")) {
					String key = fromPhoneSignal.replace("Key:", "");
					boolean successCode = mouseModel.keyAction(key);
					if (!successCode) {
						serverGui.setStatusLbl("Unrecognized character " + key + 
								"! We are sorry if you are not using English keyboard", true);
					} else {
						serverGui.setStatusLbl("", false);
					}
				}
				// Process gesture events 
				else if (fromPhoneSignal.contains("Gest:")) {
					String action = fromPhoneSignal.split(":")[1];

					if (action.equals("Left")) {
						sMouseEvent.setPressCode(1);
						mouseModel.mouseAction(sMouseEvent);
						sMouseEvent.setPressCode(0);
						mouseModel.mouseAction(sMouseEvent);
					} else if (action.equals("Right")) {
						sMouseEvent.setPressCode(2);
						mouseModel.mouseAction(sMouseEvent);
					}
				}
			}
		} catch (IOException ex) {
			isConnectedToPhone = false;
			if (!phoneName.equals("")) {
				serverGui.setStatusLbl("Disconnected to " + phoneName, true);
				serverGui.displayMainPage();
			}
			
		} catch (Exception ex) {
			serverGui.setStatusLbl("Server error! Please check you Internet connection", true);
			ex.printStackTrace();
		}
	}
	
	
}
