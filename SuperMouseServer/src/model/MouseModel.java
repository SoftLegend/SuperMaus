package model;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MouseModel {

	// Virtual Mouse Controller in desktop
	private Robot mouseBot;
	private SuperMouseEvent sme;

	public MouseModel(SuperMouseEvent event) {
		try {
			mouseBot = new Robot();
			sme = event;
		} catch (Exception e) {
			// Instantiate class for handling mouse event being sent from mobile phone to computer
		}
	}
	
	public void mouseAction(SuperMouseEvent sme) {
		int pressing = sme.getPressCode();
		
		
		// If the user is pressing the mouse, trigger the mouse press signal
		if (pressing == 1) {
			if (!sme.isPressedAlready()) {
				mouseBot.mousePress(InputEvent.BUTTON1_MASK);
				
				sme.setIsPressedAlready(true);
			}
			else {
				mouseBot.delay(100);
			}
		} else if (pressing == 0) {
			mouseBot.mouseRelease(InputEvent.BUTTON1_MASK);
			sme.setIsPressedAlready(false);
		} else if (pressing == 2) {
			mouseBot.mousePress(InputEvent.BUTTON3_MASK);
			mouseBot.delay(200);
			mouseBot.mouseRelease(InputEvent.BUTTON3_MASK);
		} 
		
		// Move the mouse to the position getting from the phone
		mouseBot.mouseMove(sme.getX(), sme.getY());
		
		// If the user is pressing the mouse, hold the press until receiving the release signal
//		while (pressing == true) {
//			mouseBot.delay(10);
//			pressing = sme.isPressed();
//		}
		
		// Release the mouse press event
		//mouseBot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public void keyAction(char keyChar) {
		
        if (Character.isUpperCase(keyChar)) {
            mouseBot.keyPress(KeyEvent.VK_SHIFT);
        }
        mouseBot.keyPress(Character.toUpperCase(keyChar));
        mouseBot.keyRelease(Character.toUpperCase(keyChar));

        if (Character.isUpperCase(keyChar)) {
        	mouseBot.keyRelease(KeyEvent.VK_SHIFT);
        }
	}
	
}
