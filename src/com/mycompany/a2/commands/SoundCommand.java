package com.mycompany.a2.commands;

import com.codename1.ui.Command;
import com.codename1.ui.CheckBox;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a2.gameobjects.GameWorld;
import com.mycompany.a2.sound.BGSound;

public class SoundCommand extends Command{
	private GameWorld gw;
	private BGSound bgSound;
	
	public SoundCommand(GameWorld gw) {
		super("Sound");
		this.gw = gw;
		bgSound = new BGSound("background.wav");
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getKeyEvent() != -1) {
			if (((CheckBox) e.getComponent()).isSelected()) {
				gw.setSound(true);
				bgSound.play();	
			}
		
			else {
				gw.setSound(false);
				bgSound.pause();
			}
		}
	}
}
