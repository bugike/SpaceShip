package com.mycompany.a2.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class NewCommand extends Command{
	
	public NewCommand() {
		super("New");
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getKeyEvent() != -1)
			System.out.println("New command is invoked...");
	}
}