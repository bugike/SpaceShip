package com.mycompany.a2.stylesetting;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.plaf.Border;

public class setStyleButton {
	public setStyleButton(Button button) {
		button.getAllStyles().setBgTransparency(255);
		button.getAllStyles().setBgColor(ColorUtil.LTGRAY);
		button.getAllStyles().setFgColor(ColorUtil.BLACK);
		button.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));
	}
}
