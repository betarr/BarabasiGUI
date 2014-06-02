package sk.sochuliak.giraphe.utils;

import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class GuiUtils {

	public static GridBagConstraints setPropsToGridBagContraints(
			GridBagConstraints constrains, int x, int y, int width, int height) {
		constrains.gridx = x;
		constrains.gridy = y;
		constrains.gridwidth = width;
		constrains.gridheight = height;
		return constrains;
	}
	
	public static JMenuItem buildJMenuItem(String label, KeyStroke keyStroke, ActionListener listener) {
		JMenuItem jMenuItem = new JMenuItem(label);
		jMenuItem.setAccelerator(keyStroke);
		jMenuItem.addActionListener(listener);
		return jMenuItem;
	}
}
