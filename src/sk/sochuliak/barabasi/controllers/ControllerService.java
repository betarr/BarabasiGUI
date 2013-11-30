package sk.sochuliak.barabasi.controllers;

import sk.sochuliak.barabasi.gui.mainscreen.MainScreen;

public class ControllerService {

	private static AppController appController = null;	
	private static NetworkController networkController = null;
	private static DistributionController degreeDistributionController = null;
	private static DistributionController degreeDistributionLogController = null;
	private static DistributionController clusterDistributionController = null;
	private static DistributionController clusterDistributionLogController = null;
	
	public static void init(MainScreen mainScreen) {
		ControllerService.appController = new AppController(mainScreen);
		ControllerService.networkController = new NetworkController();
	}
	
	public static void registerDegreeDistributionController(DistributionController distributionController) {
		ControllerService.degreeDistributionController = distributionController;
	}
	
	public static void registerDegreeDistributionLogController(DistributionController distributionLogController) {
		ControllerService.degreeDistributionLogController = distributionLogController;
	}
	
	public static void registerClusterDistributionController(DistributionController distributionController) {
		ControllerService.clusterDistributionController = distributionController;
	}
	
	public static void registerClusterDistributionLogController(DistributionController distributionLogController) {
		ControllerService.clusterDistributionLogController = distributionLogController;
	}
	
	public static AppController getAppController() {
		return ControllerService.appController;
	}
	
	public static NetworkController getNetworkController() {
		return ControllerService.networkController;
	}

	public static DistributionController getDegreeDistributionController() {
		return degreeDistributionController;
	}

	public static DistributionController getDegreeDistributionLogController() {
		return degreeDistributionLogController;
	}

	public static DistributionController getClusterDistributionController() {
		return clusterDistributionController;
	}

	public static DistributionController getClusterDistributionLogController() {
		return clusterDistributionLogController;
	}
}
