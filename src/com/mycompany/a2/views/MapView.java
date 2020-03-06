package com.mycompany.a2.views;

import java.util.Observable;
import java.util.Observer;

import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point2D;
import com.mycompany.a2.gameobjects.GameObject;
import com.mycompany.a2.gameobjects.GameWorld;
import com.mycompany.a2.interfaces.IGameWorld;
import com.mycompany.a2.interfaces.IIterator;

public class MapView extends Container implements Observer{
	private GameWorld gw;
	
	public MapView(GameWorld gw) {
		this.gw = gw;
	}
	

	@Override
	public void update(Observable observable, Object data) {
		IGameWorld igw = (IGameWorld) data;
		
		this.repaint();
		
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Point2D pCmpRelPrnt = new Point2D(this.getX(), this.getY());
		Point2D pCmpRelScrn = new Point2D(this.getAbsoluteX(), this.getAbsoluteY());
		IIterator theElements = gw.getWorldObjects().getIterator();
		while (theElements.hasNext()) {
			GameObject go = (GameObject) theElements.getNext();
			go.draw(g, pCmpRelPrnt, pCmpRelScrn);
		}
		
	}
}
