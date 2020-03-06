package com.mycompany.a2.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class SaveCommand extends Command{
	
	public SaveCommand() {
		super("Save");
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getKeyEvent() != -1)
			System.out.println("Save command is invoked...");
	}
}
