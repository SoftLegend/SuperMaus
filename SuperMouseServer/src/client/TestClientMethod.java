package client;
import java.io.IOException;

import client.TestClient;

public class TestClientMethod {
	TestClient clientUtil;
	
	public TestClientMethod(TestClient testClient) {
		clientUtil = testClient;
	}
	
	public void action1() throws IOException {
		clientUtil.getOutStream().writeUTF("Data:0:0:1");
		String msg = clientUtil.getInStream().readUTF();
		System.out.println("Return Msg for action 1: " + msg);
	}
	
	public void action2() throws IOException {
		clientUtil.getOutStream().writeUTF("Data:50:50:1");
		String msg = clientUtil.getInStream().readUTF();
		System.out.println("Return Msg for action 2: " + msg);
	}
}
