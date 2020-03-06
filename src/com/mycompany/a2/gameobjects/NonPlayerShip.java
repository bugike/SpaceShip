package com.mycompany.a2.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point2D;
import com.mycompany.a2.interfaces.ICollider;
import com.mycompany.a2.interfaces.IDrawable;

import java.util.Random;

public class NonPlayerShip extends MoveableObject implements IDrawable, ICollider{
	private int missileCount;
	private Random r = new Random();
	MissileLauncher ml;
	private int height, base;
	private int fireCount;
	
	public NonPlayerShip(){
		this.setColor(ColorUtil.GREEN);
		
		int sizeIndicator = r.nextInt(2);  // 0 is small  | 1 is large
		if (sizeIndicator == 0) {
			this.height = 80;
			this.base = 60;
			this.setSize(80);
			
		}
		else {
			this.height = 120;
			this.base = 100;
			this.setSize(120);
		}
		
		double loc_X = r.nextDouble() * (MAX_X - MIN_X - this.getSize()) + (this.getSize()/2);
		double loc_Y = r.nextDouble() * (MAX_Y - MIN_Y - this.getSize()) + (this.getSize()/2);
		this.setLocation(loc_X, loc_Y);
		
		ml = new MissileLauncher(this.getDirection(), this.getLocationX(), 
				this.getLocationY(), this.getSpeed());
		
		this.missileCount = 5;
		this.setFireCount(r.nextInt(201) + 300);   //
	}
	
	
	public void setFireCount(int fireCount) {
		this.fireCount = fireCount;
	}
	
	public int getFireCount() {
		return this.fireCount;
	}
	
	public int getNPSMissileCount() {
		return this.missileCount;
	}
	
	public void setNPSMissileCount(int missileCount) {
		this.missileCount = missileCount;
	}
	
	public void NPSlauncherMove() {
		ml.move();
	}
	
	public void move() {

		// do the bouncing and change the direction
		if (((this.getLocationX() + this.getSize()/2) >= MAX_X) || ((this.getLocationX() - this.getSize()/2) <= MIN_X)) {
			this.setDirection(360 - this.getDirection());
			ml.setDirection(this.getDirection());
		}
		if (((this.getLocationY() + this.getSize()/2) >= MAX_Y) || ((this.getLocationY() - this.getSize()/2) <= MIN_Y)) {
			this.setDirection(180 - this.getDirection());
			ml.setDirection(this.getDirection());
		}
		
		double ang = Math.toRadians(-(90 - this.getDirection()));
		double dX = Math.cos(ang) * this.getSpeed();
		double dY = Math.sin(ang) * this.getSpeed();
		this.setLocation((double) (this.getLocationX() + dX), (double)(this.getLocationY() + dY));
		ml.setLocation((double) (this.getLocationX()), (double)(this.getLocationY()));

	}
		
	
	public void draw(Graphics g, Point2D pCmpRelPrnt, Point2D pCmpRelScrn) {
		
		g.setColor(this.getColor());

		// Location of Non Player Ship
		double locX = pCmpRelPrnt.getX() + this.getLocationX();
		double locY = pCmpRelPrnt.getY() + this.getLocationY();
						
		//  x of points of the triangle
		int xPoints[] = {(int)locX, (int)(locX + base/2), (int)(locX - base/2)};
		int yPoints[] = {(int)(locY - height/2), (int)(locY + height/2), (int)(locY + height/2)};
				
		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform);
		gXform.rotate((float) Math.toRadians(this.getDirection()), 
				(float)(pCmpRelScrn.getX() + this.getLocationX()), 
				(float)(pCmpRelScrn.getY() + this.getLocationY()));
		g.setTransform(gXform);
				
		g.fillPolygon(xPoints, yPoints, 3);

		g.resetAffine();
		
		ml.draw(g, pCmpRelPrnt, pCmpRelScrn);
		
				
	}
	
	
	
	public String toString() {
		return ("NPS: " + super.toString() + " size = " + this.getSize()
		 + " Missile = " + this.missileCount + " Missile Launcher dir = " + ml.getDirection());
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
				((Missile) otherObject).setCollideCase(2);
			}
		}
		else if (otherObject instanceof PlayerShip) {
			((PlayerShip) otherObject).setCollideCase(5);
		}
		else if (otherObject instanceof Asteroid) {
			((Asteroid) otherObject).setCollideCase(7);
		}
	}
}
