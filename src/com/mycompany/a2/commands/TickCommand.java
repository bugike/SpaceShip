package com.mycompany.a2.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.gameobjects.GameWorld;

public class TickCommand extends Command{
	private GameWorld gw;
	
	public TickCommand(GameWorld gw) {
		super("Run A Tick Command");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getKeyEvent() != -1)
			gw.clockTicked();
	}
}
