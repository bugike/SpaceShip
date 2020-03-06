package com.mycompany.a2.gameobjects;

public abstract class FixObject extends GameObject{

	private int uniqueID;
	private static int counter = 0;	
	
	public void setID() {
		this.uniqueID = counter;
		counter++;
	}
	
	public int getID(){
		return this.uniqueID;
	}
	
}
