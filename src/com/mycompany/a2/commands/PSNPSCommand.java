package com.mycompany.a2.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.gameobjects.GameWorld;

public class PSNPSCommand extends Command{
	private GameWorld gw;
	
	public PSNPSCommand(GameWorld gw) {
		super("Player Ship Hits Non Player Ship");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getKeyEvent() != -1)
			gw.PSHitNPS();
	}
}
