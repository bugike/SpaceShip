package com.mycompany.a2.views;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.UITimer;
import com.mycompany.a2.commands.*;
import com.mycompany.a2.gameobjects.GameWorld;
import com.mycompany.a2.stylesetting.setStyleButton;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;

import java.lang.String;
import java.util.Observable;
import java.util.Observer;

public class Game extends Form implements Runnable{
	private GameWorld gw;
	private PointsView pv;
	private static MapView mv;
	
	public Game() {
		gw = new GameWorld();
		pv = new PointsView();
		mv = new MapView(gw);
		gw.addObserver(mv);
		gw.addObserver(pv);
		display();
		UITimer timer = new UITimer(this);
		timer.schedule(20, true, this);
		this.show();
	}
	
	private void display() {
		setLayout(new BorderLayout());
		
		// Side menu setting
		Toolbar sideMenu = new Toolbar();
		setToolbar(sideMenu);
		sideMenu.setTitle("Asteroid Game");
				
		NewCommand itemNew = new NewCommand();
		SaveCommand itemSave = new SaveCommand();
		UndoCommand itemUndo = new UndoCommand();
		AboutCommand itemAbout = new AboutCommand();
		QuitCommand itemQuit = new QuitCommand(gw);
		// Sound CheckBox
		SoundCommand itemSound = new SoundCommand(gw);
		CheckBox soundCheckBox = new CheckBox("Sound");
		soundCheckBox.getAllStyles().setBgTransparency(255);
		soundCheckBox.setCommand(itemSound);
		
		sideMenu.addComponentToSideMenu(soundCheckBox);
		sideMenu.addCommandToSideMenu(itemNew);
		sideMenu.addCommandToSideMenu(itemSave);
		sideMenu.addCommandToSideMenu(itemUndo);
		sideMenu.addCommandToSideMenu(itemAbout);
		sideMenu.addCommandToSideMenu(itemQuit);
		
		
		// Add PointsView
		pv.getAllStyles().setBgTransparency(255);
		//pv.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));
		add(BorderLayout.NORTH, pv);
		
		// Add MapView
		mv.getAllStyles().setBgTransparency(255);
		mv.getAllStyles().setFgColor(ColorUtil.WHITE);   // set background color as WHITE
		mv.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.BLACK));
		add(BorderLayout.CENTER,mv);
		
		
		// Control Panel
		Container controlPanel = new Container();
		controlPanel.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		
		Label cmd = new Label("Commands");
		cmd.getAllStyles().setBgTransparency(255);
		controlPanel.add(cmd);
		
		// Add Asteroid Button
		Button addAsteroid = new Button();
		new setStyleButton(addAsteroid);  // Change Button Style
		AddAsteroidCommand aac = new AddAsteroidCommand(gw);
		addAsteroid.setCommand(aac);
		controlPanel.add(addAsteroid);
		
		// Add NPS Button
		Button addNPS = new Button();
		new setStyleButton(addNPS);  // Change Button Style
		AddNPSCommand anc = new AddNPSCommand(gw);
		addNPS.setCommand(anc);
		controlPanel.add(addNPS);
		
		// Add Space Station
		Button addSpaceStation = new Button();
		new setStyleButton(addSpaceStation);  // Change Button Style
		AddSpaceStationCommand assc = new AddSpaceStationCommand(gw);
		addSpaceStation.setCommand(assc);
		controlPanel.add(addSpaceStation);
		
		// Add Player Ship
		Button addPS = new Button();
		new setStyleButton(addPS);  // Change Button Style
		AddPlayerShipCommand apsc = new AddPlayerShipCommand(gw);
		addPS.setCommand(apsc);
		controlPanel.add(addPS);
		
		// PS fire
		Button psFire = new Button();
		new setStyleButton(psFire);  // Change Button Style
		PlayerShipFireCommand psfc = new PlayerShipFireCommand(gw);
		psFire.setCommand(psfc);
		controlPanel.add(psFire);
		
		// Jump hyperspace
		Button jump = new Button();
		new setStyleButton(jump);  // Change Button Style
		JumpCommand jc = new JumpCommand(gw);
		jump.setCommand(jc);
		controlPanel.add(jump);
		
		add(BorderLayout.WEST, controlPanel);
		
		// Key Binding
		// Increase Player Ship Speed
		IncreasePSSpeedCommand ipssc = new IncreasePSSpeedCommand(gw);
		addKeyListener('i',ipssc);
		addKeyListener(-91,ipssc);
		
		// Decrease Player Ship Speed
		DecreasePSSpeedCommand dpssc = new DecreasePSSpeedCommand(gw);
		addKeyListener('d',dpssc);
		addKeyListener(-92,dpssc);
		
		// Turn left Player Ship
		TurnLeftPSCommand tlpsc = new TurnLeftPSCommand(gw);
		addKeyListener('l',tlpsc);
		addKeyListener(-93,tlpsc);
		
		// Turn Right Player Ship
		TurnRightPSCommand trpsc = new TurnRightPSCommand(gw);
		addKeyListener('r',trpsc);
		addKeyListener(-94,trpsc);
		
		// Turn Left Missile Launcher
		TurnLeftMLCommand tlmlc = new TurnLeftMLCommand(gw);
		addKeyListener(44,tlmlc);
		
		// Turn Right Missile Launcher
		TurnRightMLCommand trmlc = new TurnRightMLCommand(gw);
		addKeyListener(46,trmlc);
		
		// Player Ship Firing (Command declared above)
		addKeyListener(-90,psfc);
		
		// Non Player Ship Firing
		NonPlayerShipFireCommand npsfc = new NonPlayerShipFireCommand(gw);
		addKeyListener('L',npsfc);
		
		// Jump hyperspace
		addKeyListener('j', jc);
		
		// Load Missiles to Player Ship
		LoadPSCommand lpsc = new LoadPSCommand(gw);
		addKeyListener('n', lpsc);
		
		// Player Ship Missile hits Asteroid
		PSMissileAsteroidCommand psmac = new PSMissileAsteroidCommand(gw);
		addKeyListener('k', psmac);
		
		// Player Ship Missile hits Non Player Ship
		PSMissileNPSCommand psmnpsc = new PSMissileNPSCommand(gw);
		addKeyListener('e', psmnpsc);
		
		// Non Player Ship Missile hits Player Ship
		NPSMissilePSCommand npsmpsc = new NPSMissilePSCommand(gw);
		addKeyListener('E', npsmpsc);
		
		// Player Ship hits Asteroid
		PSAsteroidCommand psac = new PSAsteroidCommand(gw);
		addKeyListener('c', psac);
		
		// Player Ship hits Non Player Ship
		PSNPSCommand psnpsc = new PSNPSCommand(gw);
		addKeyListener('h', psnpsc);
		
		// Asteroid hits another Asteroid
		AsteroidAsteroidCommand aacmd = new AsteroidAsteroidCommand(gw);
		addKeyListener('x', aacmd);
		
		// Asteroid hits Non Player Ship
		AsteroidNPSCommand anpsc = new AsteroidNPSCommand(gw);
		addKeyListener('I', anpsc);
		
		// Run a Tick Command
		TickCommand tc = new TickCommand(gw);
		addKeyListener('t', tc);
		
		// Quit Command
		addKeyListener('Q', itemQuit);
		
		this.show();

		
		
		
	}
	
	public static int getMapWidth() {
		return mv.getWidth();
	}
	
	public static int getMapHeight() {
		return mv.getHeight();
	}
	
	public static int getMapOriginX() {
		return mv.getX();
	}
	
	public static int getMapOriginY() {
		return mv.getY();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		gw.clockTicked();
		gw.handleCollideObjects();
		mv.repaint();
	}
}
