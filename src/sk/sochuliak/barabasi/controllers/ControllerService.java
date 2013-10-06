package sk.sochuliak.barabasi.controllers;

import sk.sochuliak.barabasi.gui.mainscreen.MainScreen;

public class ControllerService {

	private static AppController appController = null;	
	private static NetworkController networkController = null;
	private static DegreeDistributionController degreeDistributionController = null;
	private static ClusterDistributionController clusterDistributionController = null;
	
	public static void init(MainScreen mainScreen) {
		ControllerService.appController = new AppController(mainScreen);
		ControllerService.networkController = new NetworkController();
	}
	
	public static void registerDegreeDistributionController(DegreeDistributionController degreeDistributionController) {
		ControllerService.degreeDistributionController = degreeDistributionController;
	}
	
	public static void registerClusterDistributionController(ClusterDistributionController clusterDistributionController) {
		ControllerService.clusterDistributionController = clusterDistributionController;
	}
	
	public static AppController getAppController() {
		return ControllerService.appController;
	}
	
	public static NetworkController getNetworkController() {
		return ControllerService.networkController;
	}
	
	public static DegreeDistributionController getDegreeDistributionController() {
		return ControllerService.degreeDistributionController;
	}
	
	public static ClusterDistributionController getClusterDistributionController() {
		return ControllerService.clusterDistributionController;
	}
}
