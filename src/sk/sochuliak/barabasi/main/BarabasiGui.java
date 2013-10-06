package sk.sochuliak.barabasi.main;

import sk.sochuliak.barabasi.controllers.ControllerService;
import sk.sochuliak.barabasi.gui.MainGuiConfiguration;
import sk.sochuliak.barabasi.gui.Strings;
import sk.sochuliak.barabasi.gui.mainscreen.MainScreen;

public class BarabasiGui {

	public static void main(String[] args) {
		MainScreen mainScreen = new MainScreen(Strings.APPLICATION_NAME, MainGuiConfiguration.APPLICATION_SIZE);
		ControllerService.init(mainScreen);
	}
}