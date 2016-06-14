package model;

import java.awt.MouseInfo;
import java.awt.PointerInfo;

public class SuperMouseEvent {
	private int x;
	private int y;
	private boolean isPressed;
	
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

	public boolean isPressed() {
		return isPressed;
	}

	public void setPressed(boolean isPressed) {
		this.isPressed = isPressed;
	}
	
	public SuperMouseEvent() {
		isPressed = false;
		x = (int) MouseInfo.getPointerInfo().getLocation().getX();
		y = (int) MouseInfo.getPointerInfo().getLocation().getY();
	}
}
