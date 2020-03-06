package com.mycompany.a2.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.gameobjects.GameWorld;

public class IncreasePSSpeedCommand extends Command{
	private GameWorld gw;
	
	public IncreasePSSpeedCommand(GameWorld gw) {
		super("Increase Player Ship Speed");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getKeyEvent() != -1)
			gw.increaseSpeed();
	}
}
