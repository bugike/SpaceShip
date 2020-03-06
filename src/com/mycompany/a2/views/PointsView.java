package com.mycompany.a2.views;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.a2.gameobjects.GameWorld;
import com.mycompany.a2.interfaces.IGameWorld;
import com.mycompany.a2.stylesetting.setStyleLabel;

public class PointsView extends Container implements Observer{

	private Label pointsValueLabel;
	private Label missileCountValueLabel;
	private Label timeValueLabel;
	private Label soundValueLabel;
	private Label livesValueLabel;
	
	public PointsView() {
		Label pointsTextLabel = new Label("Points:");
		new setStyleLabel(pointsTextLabel);   // change label color to black (or other colors)
		Label missileCountTextLabel = new Label("Missile count:");
		new setStyleLabel(missileCountTextLabel);
		Label timeTextLabel = new Label("Elapsed time:");
		new setStyleLabel(timeTextLabel);
		Label soundTextLabel = new Label("Sound:");
		new setStyleLabel(soundTextLabel);
		Label livesTextLabel = new Label("Lives:");
		new setStyleLabel(livesTextLabel);
		
		// initial value for label value
		pointsValueLabel = new Label("000");
		missileCountValueLabel = new Label("000");
		timeValueLabel = new Label("000");
		soundValueLabel = new Label("NA");
		livesValueLabel = new Label("000");
		
		Container myContainer = new Container();
		myContainer.setLayout(new BoxLayout(BoxLayout.X_AXIS));
		
		myContainer.add(pointsTextLabel);
		myContainer.add(pointsValueLabel);
		myContainer.add(missileCountTextLabel);
		myContainer.add(missileCountValueLabel);
		myContainer.add(timeTextLabel);
		myContainer.add(timeValueLabel);
		myContainer.add(soundTextLabel);
		myContainer.add(soundValueLabel);
		myContainer.add(livesTextLabel);
		myContainer.add(livesValueLabel);
		
		this.add(myContainer);
	}
	

	public void update(Observable observable, Object data) {
		IGameWorld gw = (IGameWorld) data;
		
		this.pointsValueLabel.setText("" + gw.getScore());
		this.missileCountValueLabel.setText("" + gw.getMissileCount());
		this.timeValueLabel.setText("" + (int)(gw.getTime()/50));
		this.livesValueLabel.setText("" + gw.getLives());
		
		if (gw.getSound() == true)
			this.soundValueLabel.setText("ON");
		else
			this.soundValueLabel.setText("OFF");
		
		this.repaint();
	}

	
	
}
