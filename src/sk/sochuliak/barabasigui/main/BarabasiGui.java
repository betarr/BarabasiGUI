package sk.sochuliak.barabasigui.main;

import sk.sochuliak.barabasigui.gui.MainGuiConfiguration;
import sk.sochuliak.barabasigui.gui.Strings;
import sk.sochuliak.barabasigui.gui.mainscreen.MainScreen;

public class BarabasiGui {

	public static void main(String[] args) {
		MainScreen mainScreen = new MainScreen(Strings.APPLICATION_NAME, MainGuiConfiguration.APPLICATION_SIZE);
	}
}
