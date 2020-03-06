package com.mycompany.a2.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.gameobjects.GameWorld;

public class LoadPSCommand extends Command{
	private GameWorld gw;
	
	public LoadPSCommand(GameWorld gw) {
		super("Load Missile To Player Ship");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getKeyEvent() != -1)
			gw.loadPSMissile();
	}
}