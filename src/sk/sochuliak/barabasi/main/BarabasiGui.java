package sk.sochuliak.barabasi.main;

import org.apache.log4j.Logger;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import sk.sochuliak.barabasi.controllers.ControllerService;
import sk.sochuliak.barabasi.gui.MainGuiConfiguration;
import sk.sochuliak.barabasi.gui.Strings;
import sk.sochuliak.barabasi.gui.mainscreen.MainScreen;

public class BarabasiGui {
	
	private static final Logger logger = Logger.getLogger(BarabasiGui.class);

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		logger.info("Starting application");
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		MainScreen mainScreen = new MainScreen(Strings.APPLICATION_NAME, MainGuiConfiguration.APPLICATION_SIZE);
		ControllerService.init(mainScreen);
		logger.info("Application started");
	}
}
