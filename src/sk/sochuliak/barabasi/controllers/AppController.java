package sk.sochuliak.barabasi.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFileChooser;

import sk.sochuliak.barabasi.analysisframes.ClusterDistributionFrame;
import sk.sochuliak.barabasi.analysisframes.DegreeDistributionFrame;
import sk.sochuliak.barabasi.analysisframes.GraphConfiguration;
import sk.sochuliak.barabasi.gui.Strings;
import sk.sochuliak.barabasi.gui.mainscreen.BMenuBar;
import sk.sochuliak.barabasi.gui.mainscreen.BasicPropertiesTable;
import sk.sochuliak.barabasi.gui.mainscreen.MainScreen;
import sk.sochuliak.barabasi.gui.newgraphdialog.NewGraphDialog;
import sk.sochuliak.barabasi.gui.newgraphdialog.NewGraphProgressBar;
import sk.sochuliak.barabasi.utils.NetworkImportExport;

public class AppController {
	
	private MainScreen mainScreen;
	
	private NewGraphProgressBar newGraphProgressBarDialog = null;
	
	private boolean degreeDistributionShowed = false;
	private boolean degreeDistributionLogShowed = false;
	private boolean clusterDistributionShowed = false;
	private boolean clusterDistributionLogShowed = false;

	public AppController(MainScreen mainScreen) {
		this.mainScreen = mainScreen;
	}

	public void closeApplication() {
		System.exit(0);
	}
	
	public void showNewGraphDialog() {
		NewGraphDialog dialog = new NewGraphDialog(this.mainScreen);
		dialog.setVisible(true);
	}
	
	public void showNewGraphProgressBarAndBuildNetwork(int growthManagement, int numberOfNodes, int numberOfEdges) {
		this.newGraphProgressBarDialog = new NewGraphProgressBar(this.mainScreen, growthManagement, numberOfNodes, numberOfEdges);
		this.newGraphProgressBarDialog.setVisible(true);
	}
	
	public void dispozeNewGraphProgressBarDialog() {
		if (this.newGraphProgressBarDialog != null) {
			this.newGraphProgressBarDialog.dispose();
		}
	}
	
	public void exportGraph() {
		JFileChooser fc = new JFileChooser(NetworkImportExport.DEFAULT_DIRECTORY);
		int returnValue = fc.showSaveDialog(this.mainScreen);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			NetworkImportExport.DEFAULT_DIRECTORY = file.getParent();
			List<int[]> pairsOfNeighboringNodes = ControllerService.getNetworkController().getPairsOfNeighboringNodes();
			NetworkImportExport.export(file, pairsOfNeighboringNodes);
		}
	}
	
	public void importGraph() {
		JFileChooser fc = new JFileChooser(NetworkImportExport.DEFAULT_DIRECTORY);
		int returnValue = fc.showOpenDialog(this.mainScreen);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			NetworkImportExport.DEFAULT_DIRECTORY = file.getParent();
			List<int[]> neighboringPairs = NetworkImportExport.importFromFile(file);
			if (neighboringPairs.size() != 0) {
				ControllerService.getNetworkController().createNetwork(neighboringPairs);
				this.updateDataInBasicPropertiesTable();
				this.enableAnalysisMenuItems(true);
			}
		}
	}
	
	public void enableAnalysisMenuItems(boolean enable) {
		BMenuBar.onGraphBuilded();
	}
	
	public void updateDataInBasicPropertiesTable() {
		double totalNodesCount = ControllerService.getNetworkController().getTotalNodesCount();
		double averageNodeDegree = ControllerService.getNetworkController().getAverageNodeDegree();
		double averageClusterRatio = ControllerService.getNetworkController().getAverageClusterRatio();
		
		this.mainScreen.getBasicPropertiesPanel().getBasicPropertiesTable().setValue(BasicPropertiesTable.TOTAL_NODES_COUNT, totalNodesCount);
		this.mainScreen.getBasicPropertiesPanel().getBasicPropertiesTable().setValue(BasicPropertiesTable.AVERAGE_NODE_DEGREE, averageNodeDegree);
		this.mainScreen.getBasicPropertiesPanel().getBasicPropertiesTable().setValue(BasicPropertiesTable.AVERAGE_CLUSTER_RATIO, averageClusterRatio);
	}
	
	public void showDegreeDistributionDialog(boolean useLogScale) {
		Map<Integer, Double> degreeDistribution = ControllerService.getNetworkController().getNetworkDegreeDistribution();
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
		
		String graphTitle = useLogScale ? Strings.DEGREE_DISTRIBUTION_LOG_GRAPH_TITLE
				: Strings.DEGREE_DISTRIBUTION_GRAPH_TITLE;
		Map<String, List<double[]>> data = new HashMap<String, List<double[]>>();
		data.put(graphTitle, points);
		
		GraphConfiguration config = GraphConfiguration.getInstance()
				.setTitle(graphTitle)
				.setxAxisLabel(Strings.DEGREE_DISTRIBUTION_GRAPH_X_AXIS_LABEL)
				.setyAxisLabel(Strings.DEGREE_DISTRIBUTION_GRAPH_Y_AXIS_LABEL)
				.setData(data);
		
		DegreeDistributionFrame frame = new DegreeDistributionFrame(graphTitle, this.mainScreen, config, useLogScale);
		if (useLogScale) {
			ControllerService.registerDegreeDistributionLogController(new DistributionController(frame));
		} else {
			ControllerService.registerDegreeDistributionController(new DistributionController(frame));
		}
		frame.setVisible(true);
	}
	
	public void showClusterDistributionDialog(boolean useLogScale) {
		Map<Integer, Double> clusterDistribution = ControllerService.getNetworkController().getNetworkClusterDistribution();
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
		
		String graphTitle = useLogScale ? Strings.CLUSTER_DISTRIBUTION_LOG_GRAPH_TITLE
				: Strings.CLUSTER_DISTRIBUTION_GRAPH_TITLE;
		Map<String, List<double[]>> data = new HashMap<String, List<double[]>>();
		data.put(graphTitle, points);
		
		GraphConfiguration config = GraphConfiguration.getInstance()
				.setTitle(graphTitle)
				.setxAxisLabel(Strings.CLUSTER_DISTRIBUTION_GRAPH_X_AXIS_LABEL)
				.setyAxisLabel(Strings.CLUSTER_DISTRIBUTION_GRAPH_Y_AXIS_LABEL)
				.setData(data);
		
		ClusterDistributionFrame frame = new ClusterDistributionFrame(graphTitle, this.mainScreen, config, useLogScale);
		if (useLogScale) {
			ControllerService.registerClusterDistributionLogController(new DistributionController(frame));
		} else {
			ControllerService.registerClusterDistributionController(new DistributionController(frame));
		}
		frame.setVisible(true);
	}

	public boolean isDegreeDistributionShowed() {
		return degreeDistributionShowed;
	}

	public void setDegreeDistributionShowed(boolean degreeDistributionShowed) {
		this.degreeDistributionShowed = degreeDistributionShowed;
	}

	public boolean isDegreeDistributionLogShowed() {
		return degreeDistributionLogShowed;
	}

	public void setDegreeDistributionLogShowed(boolean degreeDistributionLogShowed) {
		this.degreeDistributionLogShowed = degreeDistributionLogShowed;
	}

	public boolean isClusterDistributionShowed() {
		return clusterDistributionShowed;
	}

	public void setClusterDistributionShowed(boolean clusterDistributionShowed) {
		this.clusterDistributionShowed = clusterDistributionShowed;
	}

	public boolean isClusterDistributionLogShowed() {
		return clusterDistributionLogShowed;
	}

	public void setClusterDistributionLogShowed(boolean clusterDistributionLogShowed) {
		this.clusterDistributionLogShowed = clusterDistributionLogShowed;
	}
}
