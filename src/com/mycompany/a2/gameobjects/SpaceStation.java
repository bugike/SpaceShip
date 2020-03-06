package com.mycompany.a2.gameobjects;

import java.util.Random;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point2D;
import com.mycompany.a2.interfaces.ICollider;
import com.mycompany.a2.interfaces.IDrawable;

public class SpaceStation extends FixObject implements IDrawable, ICollider{
	
	private int blinkRate, blinkRateReset;
	private boolean blinkLight;
	private Random r = new Random();
	
	public SpaceStation() {
		this.setSize(80);
		blinkLight = true;
		this.setColor(ColorUtil.YELLOW);
		this.setID();
		this.blinkRate = r.nextInt(21) + 40; // blink rate from 40 to 60
		this.blinkRateReset = this.blinkRate;
		double loc_X = r.nextDouble() * (MAX_X - MIN_X - this.getSize()) + (this.getSize()/2);
		double loc_Y = r.nextDouble() * (MAX_Y - MIN_Y - this.getSize()) + (this.getSize()/2);
		this.setLocation(loc_X, loc_Y);
	}
	
	public int getBlinkRate() {
		return this.blinkRate;
	}
	
	public void setBlinkRate(int blinkRate) {
		this.blinkRate = blinkRate;
	}
	
	public int getBlinkRateReset() {
		return this.blinkRateReset;
	}
	
	public void setBlinkLight(boolean blinkLight) {
		this.blinkLight = blinkLight;
	}
	
	public boolean getBlinkLight() {
		return blinkLight;
	}
	
	public void draw(Graphics g, Point2D pCmpRelPrnt, Point2D pCmpRelScrn) {
		
		if (this.blinkLight) {
			g.setColor(ColorUtil.YELLOW);
			g.fillArc((int) (pCmpRelPrnt.getX() + this.getLocationX() - this.getSize()/2), 
					(int) (pCmpRelPrnt.getY() + this.getLocationY() - this.getSize()/2), 
					this.getSize(), this.getSize(), 0, 360);
		}
		else {
			g.setColor(ColorUtil.GRAY);
			g.fillArc((int) (pCmpRelPrnt.getX() + this.getLocationX() - this.getSize()/2), 
					(int) (pCmpRelPrnt.getY() + this.getLocationY() - this.getSize()/2), 
					this.getSize(), this.getSize(), 0, 360);
		}
	}
	
	public String toString() {
		String s = "Space Station: ";
		s += super.toString();
		s += "rate = " + getBlinkRate() + " ";
		return s;
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
		if (otherObject instanceof PlayerShip) {
			((PlayerShip) otherObject).setCollideCase(8);
		}
	}
}
