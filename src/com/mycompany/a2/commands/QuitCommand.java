package com.mycompany.a2.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.gameobjects.GameWorld;

public class QuitCommand extends Command{
	private GameWorld gw;
	
	public QuitCommand(GameWorld gw) {
		super("Quit");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getKeyEvent() != -1)
			gw.quit();
	}
}
