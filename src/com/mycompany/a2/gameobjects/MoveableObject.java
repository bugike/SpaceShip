package com.mycompany.a2.gameobjects;

import java.util.Random;

import com.mycompany.a2.interfaces.IMoveable;

public abstract class MoveableObject extends GameObject implements IMoveable{
	private int speed, direction;
	private Random r = new Random();
	
	public MoveableObject() {
		this.setSpeed();
		this.setDirection();
	};
	
	public void setSpeed() {
		this.speed = r.nextInt(5) + 4; // set maximum speed from 4 - 8 (except missiles)
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void setDirection() {
		this.direction = r.nextInt(360);   // set random direction
	}
	
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public void move() {
		double ang = Math.toRadians(-(90 - this.getDirection()));
		double dX = Math.cos(ang) * this.getSpeed();
		double dY = Math.sin(ang) * this.getSpeed();
		this.setLocation((double) (this.getLocationX() + dX), (double)(this.getLocationY() + dY));
	}
	
	public String toString() {
		String s = super.toString();
		s += " Direction = " + this.getDirection() + " Speed = " + this.getSpeed();
		return s;
	}
}
