package com.mycompany.a2.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.gameobjects.GameWorld;

public class TurnRightMLCommand extends Command{
	private GameWorld gw;
	
	public TurnRightMLCommand(GameWorld gw) {
		super("Turn Right Missile Launcher");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getKeyEvent() != -1)
			gw.PSMissileLauncherTurnRight();
	}
}