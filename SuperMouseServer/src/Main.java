import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.swing.KeyStroke;

import client.FakePhone;
import model.HostModel;
import model.MouseModel;
import model.QRCodeModel;
import model.SuperMouseEvent;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import view.ServerGui;

public class Main {

	public static void main(String[] args) throws InterruptedException, IOException, AWTException {
		
		ServerGui serverGui = new ServerGui();
		HostModel server;
		TimeUnit.SECONDS.sleep(3);
		
		// Convert the state of the cursor in mobile phone to the state of desktop's cursor 
		SuperMouseEvent mouseEvent = new SuperMouseEvent();
		
		// The handler to monitor the mouse drag, click, and move event
		MouseModel mouseHandler = new MouseModel(mouseEvent);
		Robot bot = new Robot();
		System.out.println();
//		int key = 0;
		//while (key != KeyEvent.VK_ESCAPE) {
//			key = System.in.read();
//			bot.keyPress(KeyEvent.getExtendedKeyCodeForChar('a'));
//			bot.keyRelease(KeyEvent.getExtendedKeyCodeForChar('a'));
		//}
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
