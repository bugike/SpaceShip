package com.mycompany.a2.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.gameobjects.GameWorld;

public class PSMissileAsteroidCommand extends Command{
	private GameWorld gw;
	
	public PSMissileAsteroidCommand(GameWorld gw) {
		super("Player Ship Missile Hits Asteroid");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getKeyEvent() != -1)
			gw.PSMissileSKAsteroid();
	}
}