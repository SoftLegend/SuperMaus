package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestClient {

	public static void main(String[] args) {
		String hostAddr = "10.9.34.160";
		Socket socket;
		try {
			socket = new Socket(hostAddr, 7863);
			DataInputStream inStream = new DataInputStream(socket.getInputStream());
			DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
			String inData = inStream.readUTF();
			System.out.println("In Data " + inData);
			outStream.writeUTF("Identity: SuperMan");
		} catch (UnknownHostException e) {
			
			System.out.println("Unknown Host " + e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
