package sk.sochuliak.barabasigui.gui.mainscreen;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import sk.sochuliak.barabasigui.gui.Strings;

public class BMenuBar extends JMenuBar {
	
	private static final long serialVersionUID = 1L;

	public BMenuBar() {
		this.add(this.buildBMenuBar());
	}

	private JMenu buildBMenuBar() {
		JMenu menu = new JMenu(Strings.MENU_PROGRAM);
		
		

		return menu;
	}
}
