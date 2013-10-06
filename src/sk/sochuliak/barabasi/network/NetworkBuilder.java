package sk.sochuliak.barabasi.network;

import java.awt.Toolkit;

import javax.swing.SwingWorker;

import sk.sochuliak.barabasi.controllers.ControllerService;

public class NetworkBuilder extends SwingWorker<Void, Void> {
	
	private NetworkBuildConfiguration config = null;
	private Network resultNetwork = null;
	
	public NetworkBuilder(NetworkBuildConfiguration config) {
		this.config = config;
	}

	@Override
	protected Void doInBackground() throws Exception {
		int progress = 0;
		this.setProgress(progress);
		
		Network network = this.config.getNetwork();
		double nDouble = (double)config.getNodesCount();
		
		for (int i = 0; i < config.getNodesCount(); i++) {
			int[] adjacentNodes;
			if (this.config.getMethodDriven() == NetworkBuildConfiguration.DEGREE_DRIVEN) {
				adjacentNodes = network.calculateAdjacentNodesDegreeDriven(config.getEdgesCount());
			} else if (this.config.getMethodDriven() == NetworkBuildConfiguration.CLUSTER_DRIVEN) {
				adjacentNodes = network.calculateAdjacentNodesClusterDriven(config.getEdgesCount());
			} else {
				System.err.println("Method driven '" + config.getMethodDriven() + "' is not valid");
				this.resultNetwork = null;
				this.updateProgress(nDouble, nDouble);
				return null;
			}
			
			network.addNode(i);
			for (int j = 0; j < adjacentNodes.length; j++) {
				network.addEdge(i, adjacentNodes[j]);
			}
			
			this.updateProgress((double)i+1, nDouble);
		}
		
		this.resultNetwork = network;
		return null;
	}
	
	private void updateProgress(double iDouble, double nDouble) {
		double progressDouble = iDouble / nDouble;
		progressDouble = progressDouble * (double) 100;
		this.setProgress(Math.min((int)progressDouble, 100));
	}
	
	@Override
	protected void done() {
		Toolkit.getDefaultToolkit().beep();
		ControllerService.getAppController().dispozeNewGraphProgressBarDialog();
		ControllerService.getNetworkController().setNetwork(this.resultNetwork);
		ControllerService.getAppController().updateDataInBasicPropertiesTable();
		ControllerService.getAppController().enableAnalysisMenuItems(true);
	}

}
