package com.mycompany.a2.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point2D;
import com.mycompany.a2.interfaces.ICollider;
import com.mycompany.a2.interfaces.IDrawable;

public class Missile extends MoveableObject implements IDrawable, ICollider{

	private int fuelLevel;
	private int whichShip;   // 0: PS's missile | 1: NPS's missile
	private int height = 40;
	private int width = 15;
	
	public Missile(int dir, double x, double y, int whichShip) {
		this.setSize(50);
		fuelLevel = 100;
		this.setDirection(dir);
		this.setLocation(x, y);
		
		this.setSpeed(12);  // set missile speed as 15 
		this.whichShip = whichShip;
		if (whichShip == 0)
			this.setColor(ColorUtil.rgb(0, 0, 255));
		else
			this.setColor(ColorUtil.rgb(0, 255, 0));
	}

	
	public void setFuelLevel(int fuelLevel) {
		this.fuelLevel = fuelLevel;
	}
	
	public int getFuelLevel() {
		return this.fuelLevel;
	}
	
	public void setWhichShip(int whichShip)
	{
		this.whichShip = whichShip;
	}
	
	public int getWhichShip() {
		return this.whichShip;
	}
	
	public void move() {

		// do the bouncing and change the direction
		if (((this.getLocationX() + this.getSize()/2) >= MAX_X) || ((this.getLocationX() - this.getSize()/2) <= MIN_X)) {
			this.setDirection(360 - this.getDirection());
		}
		if (((this.getLocationY() + this.getSize()/2) >= MAX_Y) || ((this.getLocationY() - this.getSize()/2) <= MIN_Y)) {
			this.setDirection(180 - this.getDirection());
		}
		
		double ang = Math.toRadians(-(90 - this.getDirection()));
		double dX = Math.cos(ang) * this.getSpeed();
		double dY = Math.sin(ang) * this.getSpeed();
		this.setLocation((double) (this.getLocationX() + dX), (double)(this.getLocationY() + dY));

	}
	
	public void draw(Graphics g, Point2D pCmpRelPrnt, Point2D pCmpRelScrn) {
		g.setColor(this.getColor());
		
		// Location of Player Ship
		double locX = pCmpRelPrnt.getX() + this.getLocationX();
		double locY = pCmpRelPrnt.getY() + this.getLocationY();
		
		
		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform);
		gXform.rotate((float) Math.toRadians(this.getDirection()), 
				(float)(pCmpRelScrn.getX() + this.getLocationX()), 
				(float)(pCmpRelScrn.getY() + this.getLocationY()));
		g.setTransform(gXform);
	
		g.fillRect((int) (locX - width/2), (int)(locY - height/2), width, height);
		
		g.resetAffine();
	}
	
	public String toString() {
		if (this.whichShip == 0)
			return ("PS's Missile: " + super.toString() + " fuel = " + fuelLevel);
		else
			return ("NPS's Missile: " + super.toString() + " fuel = " + fuelLevel);
	}


	@Override
	public boolean collidesWith(ICollider otherObject) {
		// TODO Auto-generated method stub
		GameObject go = (GameObject) otherObject;
		boolean result = false;
		double dx = this.getLocationX() - go.getLocationX();
		double dy = this.getLocationY() - go.getLocationY();
		double distBetweenCentersSqr = (dx*dx + dy*dy);
		
		int thisRadius = this.getSize()/2;
		int otherRadius = this.getSize()/2;
		int radiiSqr = (thisRadius*thisRadius + 2*thisRadius*otherRadius
				+ otherRadius*otherRadius);
		if (distBetweenCentersSqr <= radiiSqr) { result = true;}
		return result;
	}


	@Override
	public void handleCollision(ICollider otherObject) {
		// TODO Auto-generated method stub
		if (this.whichShip == 0) {  // player ship's missile
			if (otherObject instanceof Asteroid) {
				((Asteroid) otherObject).setCollideCase(1);
			}
			else if (otherObject instanceof NonPlayerShip) {
				((NonPlayerShip) otherObject).setCollideCase(2);
			}
		}
		if (this.whichShip == 1)  // non player ship's missile
		if (otherObject instanceof PlayerShip) {
			((PlayerShip) otherObject).setCollideCase(3);
		}

	}

	
}
