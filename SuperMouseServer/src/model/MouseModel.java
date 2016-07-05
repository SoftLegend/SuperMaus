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
			e.printStackTrace();
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
	}
	
	public boolean keyAction(String keyData) {
		if (keyData.equals("bksp")) {
			mouseBot.keyPress(KeyEvent.VK_BACK_SPACE);
	        mouseBot.keyRelease(KeyEvent.VK_BACK_SPACE);
		} else {
			char keyChar = keyData.charAt(0);
			if (Character.isAlphabetic(keyChar)) {
				if (Character.isUpperCase(keyChar)) {
		            mouseBot.keyPress(KeyEvent.VK_SHIFT);
		        }
		        
		        mouseBot.keyPress(Character.toUpperCase(keyChar));
		        mouseBot.keyRelease(Character.toUpperCase(keyChar));
		
		        if (Character.isUpperCase(keyChar)) {
		        	mouseBot.keyRelease(KeyEvent.VK_SHIFT);
		        }
			} else {
				char[] specialCharList = new char[] {
						'+' ,
						'-' , 
						'*' , 
						'%' , 
						'>' , 
						'<' , 
						'=' ,
						'_' ,
						'.' , 
						',' , 
						':' ,
						';' ,
						'\'' ,
						'\"' ,
						'`' ,
						'~' ,
						'!' ,
						'@' ,
						'#' , 
						'$' ,
						'^' ,
						'&' ,
						'(' ,
						')' ,
						'/' ,
						'\\' ,
						'?' ,
						'[' ,
						']' , 
						'{' ,
						'}' ,
						'|' ,
						' '
					};
				
				int[] specialCharVkList = new int[] {
						KeyEvent.VK_EQUALS,
						100000 + KeyEvent.VK_MINUS , 
						KeyEvent.VK_8 , 
						KeyEvent.VK_5 , /*'%'*/
						KeyEvent.VK_PERIOD , 
						KeyEvent.VK_COMMA , 
						100000 + KeyEvent.VK_EQUALS ,
						KeyEvent.VK_MINUS ,
						100000 + KeyEvent.VK_PERIOD , 
						100000 + KeyEvent.VK_COMMA , 
						KeyEvent.VK_SEMICOLON ,
						100000 + KeyEvent.VK_SEMICOLON ,
						100000 + KeyEvent.VK_QUOTE ,
						KeyEvent.VK_QUOTE ,
						100000 + KeyEvent.VK_BACK_QUOTE ,
						KeyEvent.VK_BACK_QUOTE /*'~'*/ ,
						KeyEvent.VK_1 ,
						KeyEvent.VK_2 ,
						KeyEvent.VK_3 , 
						KeyEvent.VK_4 ,
						KeyEvent.VK_6 ,
						KeyEvent.VK_7 ,
						KeyEvent.VK_9 ,
						KeyEvent.VK_0 ,
						100000 + KeyEvent.VK_SLASH ,
						100000 + KeyEvent.VK_BACK_SLASH ,
						KeyEvent.VK_SLASH /*'?'*/ ,
						100000 + KeyEvent.VK_OPEN_BRACKET ,
						100000 + KeyEvent.VK_CLOSE_BRACKET , 
						KeyEvent.VK_OPEN_BRACKET ,
						KeyEvent.VK_CLOSE_BRACKET ,
						KeyEvent.VK_BACK_SLASH /*'|'*/ ,
						100000 + KeyEvent.VK_SPACE
					};
				for (int i = 0; i < specialCharList.length; i++)  {
					// Detect special char matching
					System.out.println(keyChar + " = " + specialCharList[i]);
					String keyCharStr = "" + keyChar;
					String specialCharStr = "" + specialCharList[i];
					if (keyCharStr.equalsIgnoreCase(specialCharStr)) {
						try {
							System.out.println("In " + keyCharStr + " = " + specialCharStr);
							// If the character must be simulated with the shift key
							// simulate the shift key along with the corresponding key
							if (specialCharVkList[i] < 100000) {
								mouseBot.keyPress(KeyEvent.VK_SHIFT);
								mouseBot.keyPress(specialCharVkList[i]);
								mouseBot.keyRelease(specialCharVkList[i]);
								mouseBot.keyRelease(KeyEvent.VK_SHIFT);
								break;
							} 
							// Else just simulate the base key
							else {
								int baseKey = specialCharVkList[i] % 100000;
								mouseBot.keyPress(baseKey);
								mouseBot.keyRelease(baseKey);
								break;
							}
							
						} catch (Exception ex) {
							return false;
						}
						
					} 
				} // end loop through special chars
		        
			} // end special char processing
	        
		} // end special char filter
		return true;
	}
	
}
