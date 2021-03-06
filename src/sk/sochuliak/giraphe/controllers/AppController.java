package sk.sochuliak.giraphe.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import sk.sochuliak.giraphe.gui.Strings;
import sk.sochuliak.giraphe.gui.analysisframes.ClusterDistributionFrame;
import sk.sochuliak.giraphe.gui.analysisframes.DegreeDistributionFrame;
import sk.sochuliak.giraphe.gui.analysisframes.NetworkConfiguration;
import sk.sochuliak.giraphe.gui.infodialog.AboutDialog;
import sk.sochuliak.giraphe.gui.mainscreen.BMenuBar;
import sk.sochuliak.giraphe.gui.mainscreen.BasicPropertiesTable;
import sk.sochuliak.giraphe.gui.mainscreen.MainScreen;
import sk.sochuliak.giraphe.gui.networkselectdialog.NetworkSelectDialog;
import sk.sochuliak.giraphe.gui.newnetworkdialog.NewNetworkDialog;
import sk.sochuliak.giraphe.gui.newnetworkdialog.NewNetworkProgressBar;
import sk.sochuliak.giraphe.main.Configuration;
import sk.sochuliak.giraphe.network.NetworkBuildConfiguration;
import sk.sochuliak.giraphe.utils.NetworkImportExport;
import sk.sochuliak.giraphe.utils.NetworkImportObject;
import sk.sochuliak.giraphe.utils.TaskTimeCounter;

public class AppController {
	
	private static final Logger logger = Logger.getLogger(AppController.class);
	
	private MainScreen mainScreen;
	
	private NewNetworkProgressBar newNetworkProgressBarDialog = null;
	
	private String selectedNetworkName = null;
	
	public AppController(MainScreen mainScreen) {
		this.mainScreen = mainScreen;
	}

	public void closeApplication() {
		logger.info("Closing application");
		System.exit(0);
	}
	
	public void showNewNetworkDialog() {
		logger.info("Showing new network dialog");
		NewNetworkDialog dialog = new NewNetworkDialog(this.mainScreen);
		dialog.setVisible(true);
	}
	
	public void showNewNetworkProgressBarAndBuildNetwork(
			NetworkBuildConfiguration config) {
		logger.info("Showing new network building progress bar");
		this.newNetworkProgressBarDialog = new NewNetworkProgressBar(this.mainScreen, config);
		this.newNetworkProgressBarDialog.setVisible(true);
	}
	
	public void dispozeNewNetworkProgressBarDialog() {
		logger.info("Dispozing progress bar dialog");
		if (this.newNetworkProgressBarDialog != null) {
			this.newNetworkProgressBarDialog.dispose();
		}
	}
	
	public void showExportNetwork() {
		logger.info("Showing network export dialog");
		List<String> networkNames = ControllerService.getNetworkController().getNetworkNames();
		final NetworkSelectDialog dialog = new NetworkSelectDialog(this.mainScreen, networkNames, true);
		dialog.setOkButtonActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String selectedNetworkName = dialog.getSelectedNetworkName();
				if (selectedNetworkName != null) {
					logger.info(String.format("Network %s will be exported", selectedNetworkName));
					exportNetwork(selectedNetworkName);
					dialog.dispose();
				} else {
					logger.info("No network were selected to export");
					JOptionPane.showMessageDialog(
							mainScreen,
							Strings.NETWORK_SELECT_DIALOG_NO_SELECTION,
							Strings.ERROR,
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		dialog.setVisible(true);
	}
	
	public void exportNetwork(String networkName) {
		JFileChooser fc = new JFileChooser(NetworkImportExport.DEFAULT_DIRECTORY);
		int returnValue = fc.showSaveDialog(this.mainScreen);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			if (!file.getName().endsWith(Configuration.FILE_EXTENSION)) {
				file = new File(fc.getSelectedFile()+Configuration.FILE_EXTENSION);
			}
			TaskTimeCounter.getInstance().startTask("Exporting network " + networkName);
			NetworkImportExport.DEFAULT_DIRECTORY = file.getParent();
			List<int[]> pairsOfNeighboringNodes = ControllerService.getNetworkController().getPairsOfNeighboringNodes(networkName);
			NetworkImportExport.export(file, networkName, pairsOfNeighboringNodes);
			TaskTimeCounter.getInstance().endTask("Exporting network " + networkName);
		}
	}
	
	public void importNetwork() {
		JFileChooser fc = new JFileChooser(NetworkImportExport.DEFAULT_DIRECTORY);
		fc.setMultiSelectionEnabled(true);
		int returnValue = fc.showOpenDialog(this.mainScreen);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File[] files = fc.getSelectedFiles();
			NetworkImportExport.DEFAULT_DIRECTORY = files[0].getParent();
			for (File file : files) {
				TaskTimeCounter.getInstance().startTask("Importing from file " + file.getName());
				NetworkImportObject importObject = NetworkImportExport.importFromFile(file);
				if (isNetworkWithName(importObject.getName())) {
					logger.warn(String.format("Network %s will not be import because there exists another network in app with given name.", importObject.getName()));
					showWarningMessage(Strings.ALREADY_EXISTS_NETWORK + " " + importObject.getName());
				} else if (!NetworkImportObject.isInstanceValid(importObject)) {
					logger.warn(String.format("Network %s will not be import it is not valid.", importObject.getName()));
					showWarningMessage(Strings.INVALID_NETWORK_IMPORTING + " " + importObject.getName());
				} else {
					ControllerService.getNetworkController().createNetwork(importObject.getName(), importObject.getNeighboringPairs());
					this.updateDataInBasicPropertiesTable(importObject.getName());
				}
				TaskTimeCounter.getInstance().endTask("Importing from file " + file.getName());
			}
		}
	}
	
	public void enableAnalysisMenuItems(boolean enable) {
		if (enable) {
			BMenuBar.onNetworkExists();
		} else {
			BMenuBar.onNetworkDoesNotExist();
		}
	}
	
	public void updateDataInBasicPropertiesTable(String networkName) {
		this.selectedNetworkName = networkName;
		if (networkName == null) {
			this.mainScreen.getBasicPropertiesPanel().getBasicPropertiesTable().clearValues();
		} else {
			BasicPropertiesTable propertiesTable = this.mainScreen.getBasicPropertiesPanel().getBasicPropertiesTable();
			
			int totalNodesCount = ControllerService.getNetworkController().getTotalNodesCount(networkName);
			propertiesTable.setValue(BasicPropertiesTable.TOTAL_NODES_COUNT, totalNodesCount != -1 ? totalNodesCount : "");
			
			double averageNodeDegree = ControllerService.getNetworkController().getAverageNodeDegree(networkName);
			propertiesTable.setValue(BasicPropertiesTable.AVERAGE_NODE_DEGREE, averageNodeDegree != -1d ? averageNodeDegree : "");
			
			double averageClusterRatio = ControllerService.getNetworkController().getAverageClusterRatio(networkName);
			propertiesTable.setValue(BasicPropertiesTable.AVERAGE_CLUSTER_RATIO, averageClusterRatio != -1d ? averageClusterRatio : "");
			
			double averageDistance = ControllerService.getNetworkController().getAverateDistance(networkName);
			propertiesTable.setValue(BasicPropertiesTable.AVERAGE_DISTANCE, averageDistance != -1d ? averageDistance : "");
			
			int numberOfNeihboringNodes = ControllerService.getNetworkController().getNumberOfNeighboringNodes(networkName);
			propertiesTable.setValue(BasicPropertiesTable.NUMBER_OF_NEIGHBORING_NODES, numberOfNeihboringNodes != -1 ? numberOfNeihboringNodes : "");
			
			int maxNodeDegree = ControllerService.getNetworkController().getMaxNodeDegree(networkName);
			propertiesTable.setValue(BasicPropertiesTable.MAX_NODE_DEGREE, maxNodeDegree != -1 ? maxNodeDegree : "");
		}
	}
	
	public void updatePropertyInPropertiesTable(int propertyId, Object value) {
		BasicPropertiesTable propertiesTable = this.mainScreen.getBasicPropertiesPanel().getBasicPropertiesTable();
		propertiesTable.setValue(propertyId, value);
	}
	
	public void addNetworkToNetworkList(String networkName) {
		logger.info(String.format("Adding %s network to network list", networkName));
		this.mainScreen.getNetworkList().addNetworkNameToList(networkName);
	}
	
	public void removeNetwork(String networkName) {
		logger.info(String.format("Removing %s network from appliacation", networkName));
		this.mainScreen.getNetworkList().removeNetworkNameFromList(networkName);
		ControllerService.getNetworkController().removeNetwork(networkName);
	}
	
	public boolean isNetworkWithName(String networkName) {
		logger.info(String.format("Checking if network %s is existing in application", networkName));
		return this.mainScreen.getNetworkList().isNetworkWithName(networkName);
	}
	
	public void showDegreeDistributionDialog(final boolean useLogScale) {
		logger.info("Showing available networks for degree distribution");
		List<String> networkNames = ControllerService.getNetworkController().getNetworkNames();
		final NetworkSelectDialog dialog = new NetworkSelectDialog(this.mainScreen, networkNames, false);
		dialog.setOkButtonActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				List<String> selectedNetworks = dialog.getSelectedNetworkNames();
				if (!selectedNetworks.isEmpty()) {
					List<String> alreadyShownDistributions = new ArrayList<String>();
					for (String networkName : selectedNetworks) {
						if (useLogScale && isDegreeDistributionLogShowed(networkName)) {
							alreadyShownDistributions.add(networkName);
						} else if (!useLogScale && isDegreeDistributionShowed(networkName)) {
							alreadyShownDistributions.add(networkName);
						} else {
							showDegreeDistributionDialog(networkName, useLogScale);
						}
					}
					if (!alreadyShownDistributions.isEmpty()) {
						StringBuffer messageSb = new StringBuffer();
						messageSb.append(Strings.ALREADY_SHOWN_DISTRIBUTION).append(":\n");
						for (String alreadyShownDistributionNetwork : alreadyShownDistributions) {
							messageSb.append(alreadyShownDistributionNetwork).append("\n");
						}
						JOptionPane.showMessageDialog(
								mainScreen,
								messageSb.toString(),
								Strings.WARNING,
								JOptionPane.WARNING_MESSAGE);
					}
					dialog.dispose();
				} else {
					JOptionPane.showMessageDialog(
							mainScreen,
							Strings.NETWORK_SELECT_DIALOG_NO_SELECTION,
							Strings.ERROR,
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		dialog.setVisible(true);
	}
	
	public void showDegreeDistributionDialog(String networkName, boolean useLogScale) {
		logger.info(String.format("Showing %s degree distribution for network %s", new Object[]{useLogScale ? "log" : "", networkName}));
		Map<Integer, Double> degreeDistribution = ControllerService.getNetworkController().getNetworkDegreeDistribution(networkName);
		Set<Integer> degrees = degreeDistribution.keySet();
		List<Integer> degreesList = new ArrayList<Integer>(degrees);
		Collections.sort(degreesList);
		
		List<double[]> points = new ArrayList<double[]>();
		for (int i = 0; i < degreesList.size(); i++) {
			double x = (double) degreesList.get(i);
			double y = degreeDistribution.get(degreesList.get(i));
			if (useLogScale) {
				x = (x == 0) ? 0 : Math.log(x);
				y = (y == 0) ? 0 : Math.log(y);
			}
			points.add(new double[]{x, y});
		}
		
		String networkTitle = useLogScale ? Strings.DEGREE_DISTRIBUTION_LOG_TITLE
				: Strings.DEGREE_DISTRIBUTION_TITLE;
		Map<String, List<double[]>> data = new HashMap<String, List<double[]>>();
		data.put(networkTitle, points);
		
		NetworkConfiguration config = NetworkConfiguration.getInstance()
				.setTitle(networkName + " - " + networkTitle)
				.setxAxisLabel(Strings.DEGREE_DISTRIBUTION_X_AXIS_LABEL)
				.setyAxisLabel(Strings.DEGREE_DISTRIBUTION_Y_AXIS_LABEL)
				.setData(data);
		
		DegreeDistributionFrame frame = new DegreeDistributionFrame(networkTitle, this.mainScreen, networkName, config, useLogScale);
		if (useLogScale) {
			ControllerService.registerDegreeDistributionLogController(networkName, new DistributionController(frame));
		} else {
			ControllerService.registerDegreeDistributionController(networkName, new DistributionController(frame));
		}
		frame.setVisible(true);
	}
	
	public void showClusterDistributionDialog(final boolean useLogScale) {
		logger.info("Showing available networks for cluster distribution");
		List<String> networkNames = ControllerService.getNetworkController().getNetworkNames();
		final NetworkSelectDialog dialog = new NetworkSelectDialog(this.mainScreen, networkNames, false);
		dialog.setOkButtonActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				List<String> selectedNetworks = dialog.getSelectedNetworkNames();
				if (!selectedNetworks.isEmpty()) {
					List<String> alreadyShownDistributions = new ArrayList<String>();
					for (String networkName : selectedNetworks) {
						if (useLogScale && isClusterDistributionLogShowed(networkName)) {
							alreadyShownDistributions.add(networkName);
						} else if (!useLogScale && isClusterDistributionShowed(networkName)) {
							alreadyShownDistributions.add(networkName);
						} else {
							showClusterDistributionDialog(networkName, useLogScale);
						}
					}
					if (!alreadyShownDistributions.isEmpty()) {
						StringBuffer messageSb = new StringBuffer();
						messageSb.append(Strings.ALREADY_SHOWN_DISTRIBUTION).append(":\n");
						for (String alreadyShownDistributionNetwork : alreadyShownDistributions) {
							messageSb.append(alreadyShownDistributionNetwork).append("\n");
						}
						JOptionPane.showMessageDialog(
								mainScreen,
								messageSb.toString(),
								Strings.WARNING,
								JOptionPane.WARNING_MESSAGE);
					}
					dialog.dispose();
				} else {
					JOptionPane.showMessageDialog(
							mainScreen,
							Strings.NETWORK_SELECT_DIALOG_NO_SELECTION,
							Strings.ERROR,
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		dialog.setVisible(true);
	}
	
	public void showClusterDistributionDialog(String networkName, boolean useLogScale) {
		logger.info(String.format("Showing %s cluster distribution for network %s", new Object[]{useLogScale ? "log" : "", networkName}));
		TaskTimeCounter.getInstance().startTask(String.format("Showing %s cluster distribution for network %s", new Object[]{useLogScale ? "log" : "", networkName}));
		Map<Integer, Double> clusterDistribution = ControllerService.getNetworkController().getNetworkClusterDistribution(networkName);
		Set<Integer> degrees = clusterDistribution.keySet();
		List<Integer> degreesList = new ArrayList<Integer>(degrees);
		Collections.sort(degreesList);
		
		List<double[]> points = new ArrayList<double[]>();
		for (int i = 0; i < degreesList.size();i++) {
			double x = (double) degreesList.get(i);
			double y = clusterDistribution.get(degreesList.get(i));
			if (useLogScale) {
				x = (x == 0) ? 0 : Math.log(x);
				y = (y == 0) ? 0 : Math.log(y);
			}
			points.add(new double[]{x, y});
		}
		
		String networkTitle = useLogScale ? Strings.CLUSTER_DISTRIBUTION_LOG_TITLE
				: Strings.CLUSTER_DISTRIBUTION_TITLE;
		Map<String, List<double[]>> data = new HashMap<String, List<double[]>>();
		data.put(networkTitle, points);
		
		NetworkConfiguration config = NetworkConfiguration.getInstance()
				.setTitle(networkName + " - " + networkTitle)
				.setxAxisLabel(Strings.CLUSTER_DISTRIBUTION_X_AXIS_LABEL)
				.setyAxisLabel(Strings.CLUSTER_DISTRIBUTION_Y_AXIS_LABEL)
				.setData(data);
		
		ClusterDistributionFrame frame = new ClusterDistributionFrame(networkTitle, this.mainScreen, networkName, config, useLogScale);
		if (useLogScale) {
			ControllerService.registerClusterDistributionLogController(networkName, new DistributionController(frame));
		} else {
			ControllerService.registerClusterDistributionController(networkName, new DistributionController(frame));
		}
		frame.setVisible(true);
		TaskTimeCounter.getInstance().endTask(String.format("Showing %s cluster distribution for network %s", new Object[]{useLogScale ? "log" : "", networkName}));
	}
	
	public void showAboutProjectDialog() {
		AboutDialog.showDialog(this.mainScreen, Strings.MENU_INFO_ABOUT_SOFTWARE, Strings.MENU_INFO_ABOUT_SOFTWARE_TEXT);
	}
	
	public void showAboutAuthorDialog() {
		AboutDialog.showDialog(this.mainScreen, Strings.MENU_INFO_ABOUT_AUTHOR, Strings.MENU_INFO_ABOUT_AUTHOR_TEXT);
	}
	
	public boolean isDegreeDistributionShowed(String networkName) {
		logger.info(String.format("Checking if degree distribution of network %s is shown", networkName));
		return ControllerService.getDegreeDistributionController(networkName) != null;
	}

	public boolean isDegreeDistributionLogShowed(String networkName) {
		logger.info(String.format("Checking if log degree distribution of network %s is shown", networkName));
		return ControllerService.getDegreeDistributionLogController(networkName) != null;
	}

	public boolean isClusterDistributionShowed(String networkName) {
		logger.info(String.format("Checking if cluster distribution of network %s is shown", networkName));
		return ControllerService.getClusterDistributionController(networkName) != null;
	}

	public boolean isClusterDistributionLogShowed(String networkName) {
		logger.info(String.format("Checking if log cluster distribution of network %s is shown", networkName));
		return ControllerService.getClusterDistributionLogController(networkName) != null;
	}
	
	public MainScreen getMainScreen() {
		return this.mainScreen;
	}
	
	public void showWarningMessage(String message) {
		JOptionPane.showMessageDialog(
				this.getMainScreen(),
				message,
				Strings.WARNING,
				JOptionPane.WARNING_MESSAGE);
	}

	public String getSelectedNetworkName() {
		return this.selectedNetworkName;
	}
}
