package com.mycompany.a2.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point2D;
import com.mycompany.a2.interfaces.ICollider;
import com.mycompany.a2.interfaces.IDrawable;
import com.mycompany.a2.interfaces.ISteerable;
import com.mycompany.a2.views.Game;

public class PlayerShip extends MoveableObject implements ISteerable, IDrawable, ICollider{
	private int missileCount;
	SteerableMissileLauncher sml;
	private int height = 80; // set the height of the triangle of the player ship as 20
	private int base = 60;  // set the base width of the triangle of the player ship as 16
	
	public PlayerShip() {
		this.setSize(80);
		this.setLocation(Game.getMapWidth()/2, Game.getMapHeight()/2);
		this.setDirection(0);
		this.setSpeed(0);  // initial speed of 0
		this.setColor(ColorUtil.BLUE);
		sml = new SteerableMissileLauncher(this.getDirection(), this.getLocationX(), 
				this.getLocationY(), this.getSpeed());
		this.missileCount = 10;
		
	}

	
	public int getMissileCount() {
		return this.missileCount;
	}
	
	public void setMissileCount(int missileCount) {
		this.missileCount = missileCount;
	}
	
	// turn left by 15 degree
	public void turnLeft() {
		if ((this.getDirection() - 15) < 0)
			this.setDirection(this.getDirection() + 360 - 15);
		else
			this.setDirection(this.getDirection() -15);
	}
	
	// turn right by 15 degree
	public void turnRight() {
		if ((this.getDirection() + 15) >= 360)
			this.setDirection(this.getDirection() + 15 - 360);
		else
			this.setDirection(this.getDirection() + 15);
	}
	
	public void MLTurnRight() {
		sml.turnRight();
	}
	
	public void MLTurnLeft() {
		sml.turnLeft();
	}
	
	public int getMLDir() {
		return sml.getDirection();
	}
	
	public void jump() {
		this.setLocation(Game.getMapWidth()/2 , Game.getMapHeight()/2);
	}

	public void move() {
		// when player ship reaches to the edge, it jumps back to the middle of the map
		if (((this.getLocationX() + this.getSize()/2) >= MAX_X) || ((this.getLocationX() - this.getSize()/2) <= MIN_X)
				|| ((this.getLocationY() + this.getSize()/2) >= MAX_Y) || ((this.getLocationY() - this.getSize()/2) <= MIN_Y))
			this.jump();
		
		double ang = Math.toRadians(-(90 - this.getDirection()));
		double dX = Math.cos(ang) * this.getSpeed();
		double dY = Math.sin(ang) * this.getSpeed();
		this.setLocation((double) (this.getLocationX() + dX), (double)(this.getLocationY() + dY));
		sml.setLocation((double) (this.getLocationX()), (double)(this.getLocationY()));
	}
	
	
	public void draw(Graphics g, Point2D pCmpRelPrnt, Point2D pCmpRelScrn) {
		g.setColor(this.getColor());
		
		// Location of Player Ship
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
		
		g.drawPolygon(xPoints, yPoints, 3);
		
		g.resetAffine();
		
		// draw missile launcher
		sml.draw(g, pCmpRelPrnt, pCmpRelScrn);
		
	}
	
	
	
	public String toString() {
		return ("Player Ship: " + super.toString() + " Missile = "
				+ this.missileCount + " MissileLauncher dir = "
				+ sml.getDirection());
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
			if (((Missile) otherObject).getWhichShip() == 1){ // hit by non player ship's missile
				((Missile) otherObject).setCollideCase(3);
			}
		}
		else if (otherObject instanceof Asteroid) {
			((Asteroid) otherObject).setCollideCase(4);
		}
		else if (otherObject instanceof NonPlayerShip) {
			((NonPlayerShip) otherObject).setCollideCase(5);
		}
		if (otherObject instanceof SpaceStation) {  // reload when hit the space station
			((SpaceStation) otherObject).setCollideCase(8);
		}
	}

	
}
