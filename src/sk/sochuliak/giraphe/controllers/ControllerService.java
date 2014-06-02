package sk.sochuliak.giraphe.controllers;

import java.util.HashMap;
import java.util.Map;

import sk.sochuliak.giraphe.gui.mainscreen.MainScreen;

public class ControllerService {

	private static AppController appController = null;	
	private static NetworkController networkController = null;
	
	private static Map<String, DistributionController> degreeDistributionControllers = null;
	private static Map<String, DistributionController> degreeDistributionLogControllers = null;
	private static Map<String, DistributionController> clusterDistributionControllers = null;
	private static Map<String, DistributionController> clusterDistributionLogControllers = null;
	
	public static void init(MainScreen mainScreen) {
		ControllerService.appController = new AppController(mainScreen);
		ControllerService.networkController = new NetworkController();
	}
	
	public static void registerDegreeDistributionController(String networkName, DistributionController distributionController) {
		if (ControllerService.degreeDistributionControllers == null) {
			ControllerService.degreeDistributionControllers = new HashMap<String, DistributionController>();
		}
		ControllerService.degreeDistributionControllers.put(networkName, distributionController);
	}
	
	public static void unregisterDegreeDistributionController(String networkName) {
		ControllerService.degreeDistributionControllers.remove(networkName);
	}
	
	public static void registerDegreeDistributionLogController(String networkName, DistributionController distributionLogController) {
		if (ControllerService.degreeDistributionLogControllers == null) {
			ControllerService.degreeDistributionLogControllers = new HashMap<String, DistributionController>();
		}
		ControllerService.degreeDistributionLogControllers.put(networkName, distributionLogController);
	}
	
	public static void unregisterDegreeDistriubtionLogController(String networkName) {
		ControllerService.degreeDistributionLogControllers.remove(networkName);
	}
	
	public static void registerClusterDistributionController(String networkName, DistributionController distributionController) {
		if (ControllerService.clusterDistributionControllers == null) {
			ControllerService.clusterDistributionControllers = new HashMap<String, DistributionController>();
		}
		ControllerService.clusterDistributionControllers.put(networkName, distributionController);
	}
	
	public static void unregisterClusterDistributionController(String networkName) {
		ControllerService.clusterDistributionControllers.remove(networkName);
	}
	
	public static void registerClusterDistributionLogController(String networkName, DistributionController distributionLogController) {
		if (ControllerService.clusterDistributionLogControllers == null) {
			ControllerService.clusterDistributionLogControllers = new HashMap<String, DistributionController>();
		}
		ControllerService.clusterDistributionLogControllers.put(networkName, distributionLogController);
	}
	
	public static void unregisterClusterDistributionLogController(String networkName) {
		ControllerService.clusterDistributionLogControllers.remove(networkName);
	}
	
	public static AppController getAppController() {
		return ControllerService.appController;
	}
	
	public static NetworkController getNetworkController() {
		return ControllerService.networkController;
	}

	public static DistributionController getDegreeDistributionController(String networkName) {
		if (degreeDistributionControllers != null) {
			return degreeDistributionControllers.get(networkName);
		}
		return null;
	}

	public static DistributionController getDegreeDistributionLogController(String networkName) {
		if (degreeDistributionLogControllers != null) {
			return degreeDistributionLogControllers.get(networkName);
		}
		return null;
	}

	public static DistributionController getClusterDistributionController(String networkName) {
		if (clusterDistributionControllers != null) {
			return clusterDistributionControllers.get(networkName);
		}
		return null;
	}

	public static DistributionController getClusterDistributionLogController(String networkName) {
		if (clusterDistributionLogControllers != null) {
			return clusterDistributionLogControllers.get(networkName);
		}
		return null;
	}
}
