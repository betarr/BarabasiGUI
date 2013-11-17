package sk.sochuliak.barabasi.controllers;

import sk.sochuliak.barabasi.gui.mainscreen.MainScreen;

public class ControllerService {

	private static AppController appController = null;	
	private static NetworkController networkController = null;
	private static DistributionController degreeDistributionController = null;
	private static DistributionController clusterDistributionController = null;
	
	public static void init(MainScreen mainScreen) {
		ControllerService.appController = new AppController(mainScreen);
		ControllerService.networkController = new NetworkController();
	}
	
	public static void registerDegreeDistributionController(DistributionController distributionController) {
		ControllerService.degreeDistributionController = distributionController;
	}
	
	public static void registerClusterDistributionController(DistributionController distributionController) {
		ControllerService.clusterDistributionController = distributionController;
	}
	
	public static AppController getAppController() {
		return ControllerService.appController;
	}
	
	public static NetworkController getNetworkController() {
		return ControllerService.networkController;
	}
	
	public static DistributionController getDegreeDistributionController() {
		return ControllerService.degreeDistributionController;
	}
	
	public static DistributionController getClusterDistributionController() {
		return ControllerService.clusterDistributionController;
	}
}
