package com.mycompany.a2.gameobjects;

import java.util.Random;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point2D;
import com.mycompany.a2.interfaces.ICollider;
import com.mycompany.a2.interfaces.IDrawable;
import com.mycompany.a2.interfaces.IMoveable;


public class Asteroid extends MoveableObject implements IDrawable, ICollider{
	private Random r = new Random();
	double ang, dX, dY;
	
	public Asteroid() {
		super();
		this.setColor(ColorUtil.rgb(230, 0, 0));
		final int MIN_SIZE = 70;
		final int MAX_SIZE = 100;
		this.setSize(r.nextInt(MAX_SIZE - MIN_SIZE + 1) + MIN_SIZE);
		double loc_X = r.nextDouble() * (MAX_X - MIN_X - this.getSize()) + (this.getSize()/2);
		double loc_Y = r.nextDouble() * (MAX_Y - MIN_Y - this.getSize()) + (this.getSize()/2);
		this.setLocation(loc_X, loc_Y);
		ang = Math.toRadians(-(90 - this.getDirection()));
		dX = Math.cos(ang) * this.getSpeed();
		dY = Math.sin(ang) * this.getSpeed();
	}
	
	public void asteroidRotate() {
		if ((this.getDirection() + 10) >= 360)
			this.setDirection(this.getDirection() + 10 - 360);
		else
			this.setDirection(this.getDirection() + 10);
	}
	
	public void draw(Graphics g, Point2D pCmpRelPrnt, Point2D pCmpRelScrn) {
		g.setColor(this.getColor());
		
		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform);
		gXform.rotate((float) Math.toRadians(this.getDirection()), 
				(float)(pCmpRelScrn.getX() + this.getLocationX()), 
				(float)(pCmpRelScrn.getY() + this.getLocationY()));
		g.setTransform(gXform);
		
		// set location X and Y as center and draw a rectangle
		g.fillRect((int) (pCmpRelPrnt.getX() + this.getLocationX() - (this.getSize()/2)), 
				(int) (pCmpRelPrnt.getY() + this.getLocationY() - (this.getSize()/2)), 
				this.getSize(), this.getSize());  
		
		g.resetAffine();
	}
	
	public void move() {
		// do the bouncing
		if (((this.getLocationX() + this.getSize()/2) >= MAX_X) || ((this.getLocationX() - this.getSize()/2) <= MIN_X))
			dX = -dX;
		if (((this.getLocationY() + this.getSize()/2) >= MAX_Y) || ((this.getLocationY() - this.getSize()/2) <= MIN_Y))
			dY = -dY;
		
		this.setLocation((double) (this.getLocationX() + dX), (double)(this.getLocationY() + dY));
	}
	
	public String toString() {
		return ("Asteroid: " + super.toString() + " size = " + this.getSize());
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
		
		if (otherObject instanceof Missile) {
			if (((Missile) otherObject).getWhichShip() == 0){ // hit by player ship's missile
				((Missile) otherObject).setCollideCase(1);
			}
		}
		else if (otherObject instanceof PlayerShip) {
			((PlayerShip) otherObject).setCollideCase(4);
		}
		else if (otherObject instanceof Asteroid) {
			((Asteroid) otherObject).setCollideCase(6);
		}
		else if (otherObject instanceof NonPlayerShip) {
			((NonPlayerShip) otherObject).setCollideCase(7);
		}
	}

	
}
