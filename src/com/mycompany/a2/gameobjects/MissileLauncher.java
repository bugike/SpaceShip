package com.mycompany.a2.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point2D;
import com.mycompany.a2.interfaces.IDrawable;

public class MissileLauncher extends MoveableObject implements IDrawable{
	
	public MissileLauncher(int dir, double x, double y, int speed) {
		this.setColor(ColorUtil.BLACK);
		this.setLocation(x, y);
		this.setDirection(dir);
		this.setSpeed(speed);
	}
	

	public void draw(Graphics g, Point2D pCmpRelPrnt, Point2D pCmpRelScrn) {
		g.setColor(this.getColor());
		int x = (int) (pCmpRelPrnt.getX() + this.getLocationX());
		int y = (int) (pCmpRelPrnt.getY() + this.getLocationY());
			
		double angle = Math.toRadians(90 - this.getDirection());
		double deltaX = Math.cos(angle);
		double deltaY = Math.sin(angle);
		g.drawLine(x, y, (int) (x + (50 * deltaX)), (int) (y - (50 * deltaY)));
	}
}