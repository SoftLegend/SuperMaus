package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientMain {
	public static void main(String[] args) throws IOException {
		TestClient tc = new TestClient();
		TestClientMethod tcm = new TestClientMethod(tc);
		
		tc.connect();
		tcm.action1();
		tcm.action2();
	}
}
