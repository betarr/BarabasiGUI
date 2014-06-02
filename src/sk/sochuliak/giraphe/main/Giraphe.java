package sk.sochuliak.giraphe.main;

import org.apache.log4j.Logger;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import sk.sochuliak.giraphe.controllers.ControllerService;
import sk.sochuliak.giraphe.gui.MainGuiConfiguration;
import sk.sochuliak.giraphe.gui.Strings;
import sk.sochuliak.giraphe.gui.mainscreen.MainScreen;
import sk.sochuliak.giraphe.utils.TaskTimeCounter;

public class Giraphe {
	
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(Giraphe.class);

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		TaskTimeCounter.getInstance().startTask("Starting application");
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		MainScreen mainScreen = new MainScreen(Strings.APPLICATION_NAME, MainGuiConfiguration.APPLICATION_SIZE);
		ControllerService.init(mainScreen);
		TaskTimeCounter.getInstance().endTask("Starting application");
	}
}
