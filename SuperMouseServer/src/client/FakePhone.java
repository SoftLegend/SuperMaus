package client;

import java.util.concurrent.TimeUnit;

import model.SuperMouseEvent;

// A test thread to simulate the drag or click event
public class FakePhone extends Thread {
	SuperMouseEvent mouseEvent;
	
	public FakePhone(SuperMouseEvent event) {
		mouseEvent = event;
	}
	
	@Override
	public void run() {
		int i = 0;
		try {
			long firstTime = System.currentTimeMillis();
			while (true) {
				// Fake mouse press signal for 100 milli
				// To simulate mouse move only, set the param to false
				mouseEvent.setPressed(true);
				long secondTime = System.currentTimeMillis();
				
				// To simulate drag event, change the time to 2000
				// To simulate click event, change the time to 200
				if (secondTime - firstTime > 200) {
					// Fake mouse release signal
					mouseEvent.setPressed(false);
					firstTime = System.currentTimeMillis();
				}
				
				
				
				
				sleep(100); 
			}
		} catch (Exception ex) {
			
		}
		
		
	}
	
}
