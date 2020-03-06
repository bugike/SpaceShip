package com.mycompany.a2.commands;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionEvent;

public class AboutCommand extends Command{
	
	public AboutCommand() {
		super("About");
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getKeyEvent() != -1) {
			TextArea aboutText = new TextArea(" Jiajun Zhang \n CSC133 \n Have fun! ");
			Command button = new Command("Have a nice day!");
			Dialog.show("About", aboutText, button);
			System.out.println("About command is invoked...");
		}
	}
}
