package model;

import java.awt.MouseInfo;
import java.awt.PointerInfo;

public class SuperMouseEvent {
	private int x;
	private int y;
	private boolean isPressedAlready;
	private int pressCode;
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getPressCode() {
		return pressCode;
	}

	public void setPressCode(int isPressed) {
		this.pressCode = isPressed;
	}
	
	public boolean isPressedAlready() {
		return isPressedAlready;
	}

	public void setIsPressedAlready(boolean isPressedAlready) {
		this.isPressedAlready = isPressedAlready;
	}
	
	public SuperMouseEvent() {
		pressCode = 0;
		x = (int) MouseInfo.getPointerInfo().getLocation().getX();
		y = (int) MouseInfo.getPointerInfo().getLocation().getY();
	}
	
	public void setCurrentWindowXCursorPosition() {
		x = (int) MouseInfo.getPointerInfo().getLocation().getX();
	}
	
	public void setCurrentWindowYCursorPosition() {
		y = (int) MouseInfo.getPointerInfo().getLocation().getY();
	}
}
