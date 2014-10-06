package com.lclion.midigui;
import javax.swing.JButton;
import java.awt.Color;

public final class FactoryPianoButtons {
	/**
	 * @wbp.factory
	 * @wbp.factory.parameter.source arg0 "1"
	 */
	public static JButton createPianoWhiteKey(String arg0) {
		JButton button = new JButton(arg0);
		button.setSelected(true);
		button.setEnabled(true);
		button.setBackground(Color.GRAY);
		button.setForeground(Color.WHITE);
		return button;
	}
}