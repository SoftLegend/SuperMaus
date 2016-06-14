import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import model.HostModel;
import model.MouseModel;
import model.QRCodeModel;
import model.SuperMouseEvent;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import server.FakePhone;

public class Main {

	public static void main(String[] args) throws InterruptedException, IOException {
		
		TimeUnit.SECONDS.sleep(3);
		
		// Convert the state of the cursor in mobile phone to the state of desktop's cursor 
		SuperMouseEvent mouseEvent = new SuperMouseEvent();
		
		// The handler to monitor the mouse drag, click, and move event
		MouseModel mouseHandler = new MouseModel(mouseEvent);
		//FakePhone fakePhone = new FakePhone(mouseEvent);
		
		try {
			int key = 0;
			System.out.println("Run");
			while (key != KeyEvent.VK_ESCAPE) {
				key = System.in.read();
				System.out.println("Key " + key);
				// Press A, S, W, D to move the cursor left, down, up or right
				if (key == 97) {
					mouseEvent.setX(mouseEvent.getX() - 10);
				}
				
				else if (key == 100) {
					mouseEvent.setX(mouseEvent.getX() + 10);
				}
				
				else if (key == 119) {
					mouseEvent.setY(mouseEvent.getY() - 10);
				}
				
				else if (key == 115) {
					mouseEvent.setY(mouseEvent.getY() + 10);
				}
				
				// Press c to generate QR
				else if (key == 99) {
					String path = QRCodeModel.generateQrImg();
					HostModel server = new HostModel();
					server.acceptClient();
				}
				mouseHandler.mouseAction(mouseEvent);
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
//		fakePhone.start();
//		
//		// Test mouse press and move events
//		for (int i = 0; i < 10; i++) {
//			mouseHandler.mouseAction(mouseEvent);
//			mouseEvent.setX(mouseEvent.getX() + 20);
//			mouseEvent.setY(mouseEvent.getY() + 20);
//			System.out.println(i + " exec " + mouseEvent.getX() + " " + mouseEvent.getY() + " " + mouseEvent.isPressed());
//			TimeUnit.MILLISECONDS.sleep(200);
//		}
		System.exit(0);
	}
}
