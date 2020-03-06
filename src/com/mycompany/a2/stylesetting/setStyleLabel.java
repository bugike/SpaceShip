package com.mycompany.a2.stylesetting;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Label;


public class setStyleLabel {
	public setStyleLabel(Label label) {
		// Change label color to black
		label.getAllStyles().setFgColor(ColorUtil.BLACK);
	}
}
