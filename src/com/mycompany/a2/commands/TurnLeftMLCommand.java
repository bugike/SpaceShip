package com.mycompany.a2.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.gameobjects.GameWorld;

public class TurnLeftMLCommand extends Command{
	private GameWorld gw;
	
	public TurnLeftMLCommand(GameWorld gw) {
		super("Turn Left Missile Launcher");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getKeyEvent() != -1)
			gw.PSMissileLauncherTurnLeft();
	}
}
