package com.mycompany.a2;

import java.util.Observable;

import com.mycompany.a2.gameobjects.GameWorld;
import com.mycompany.a2.interfaces.IGameWorld;

public class GameWorldProxy extends Observable implements IGameWorld{
	
	private GameWorld gw;
	
	public GameWorldProxy(GameWorld gw) {
		this.gw = gw;
	}
	
	public int getScore() {
		return gw.getScore();
	}

	public int getTime() {
		return gw.getTime();
	}

	public int getLives() {
		return gw.getLives();
	}

	public boolean getSound() {
		return gw.getSound();
	}

	public int getMissileCount() {
		return gw.getMissileCount();
	}

}
