package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestClient {
	private DataInputStream inStream;
	private DataOutputStream outStream;
	private Socket socket;
	
	public DataInputStream getInStream() {
		return inStream;
	}
	public DataOutputStream getOutStream() {
		return outStream;
	}
	public Socket getSocket() {
		return socket;
	}
	
	public void connect() {
		String hostAddr = "192.168.2.102";
		try {
			socket = new Socket(hostAddr, 7863);
			inStream = new DataInputStream(socket.getInputStream());
			outStream = new DataOutputStream(socket.getOutputStream());
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
