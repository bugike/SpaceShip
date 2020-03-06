package com.mycompany.a2.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point2D;
import com.mycompany.a2.interfaces.IDrawable;
import com.mycompany.a2.views.Game;


public abstract class GameObject implements IDrawable{
	private Point2D location;
	private int color;
	private int size;
	private int collideCase = -1;

	protected double MIN_X = 0;
	protected double MAX_X = Game.getMapWidth();
	protected double MIN_Y = 0;
	protected double MAX_Y = Game.getMapHeight();
		
	public void setCollideCase(int collideCase) {
		this.collideCase = collideCase;
	}
	
	public int getCollideCase() {
		return this.collideCase;
	}
	
	public void setLocation(double x, double y) {
		this.location = new Point2D(x,y);
	}
	
	public double getLocationX() {
		return location.getX();
	}	
	
	public double getLocationY() {
		return location.getY();
	}
	
	public void setColor(int color) {
		this.color = color;
	}
	
	public int getColor() {
		return this.color;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public int getSize() {
		return this.size;
	}
	

	
	public String toString() {
		String loc = " loc = (" + Math.round(this.getLocationX()) + "," + Math.round(this.getLocationY()) + ")";
		String col = " color = " +  "[" + ColorUtil.red(this.getColor()) + "," + ColorUtil.green(this.getColor()) + "," + ColorUtil.blue(this.getColor()) + "] ";
		
		return loc + col;
	}
}
