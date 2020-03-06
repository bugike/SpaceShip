package com.mycompany.a2.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.gameobjects.GameWorld;

public class DecreasePSSpeedCommand extends Command{
	private GameWorld gw;
	
	public DecreasePSSpeedCommand(GameWorld gw) {
		super("Decrease Player Ship Speed");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getKeyEvent() != -1)
			gw.decreaseSpeed();
	}
}
