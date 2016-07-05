import java.awt.AWTException;
import java.io.IOException;

import model.HostModel;
import model.MouseModel;
import model.SuperMouseEvent;
import view.ServerGui;

public class Main {

	public static void main(String[] args) throws InterruptedException, IOException, AWTException {
		
		ServerGui serverGui = new ServerGui();
		HostModel server;
		// Convert the state of the cursor in mobile phone to the state of desktop's cursor 
		SuperMouseEvent mouseEvent = new SuperMouseEvent();
		
		MouseModel mouseHandler = new MouseModel(mouseEvent);
		try {
			System.out.println("Run");
			server = new HostModel(serverGui, mouseHandler, mouseEvent);
			while (true)
				server.acceptClient();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
}
