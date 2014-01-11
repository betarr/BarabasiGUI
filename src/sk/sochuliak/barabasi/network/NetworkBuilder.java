package sk.sochuliak.barabasi.network;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

import sk.sochuliak.barabasi.controllers.ControllerService;
import sk.sochuliak.barabasi.utils.CommonUtils;

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
		double nDouble = (double)config.getNumberOfNodes();
		
		for (int i = 0; i < config.getNumberOfNodes(); i++) {
			int actualNodeId = i;
			int adjacentNodeId = -1;
			int firstNodeConnectingMethod = config.getFirstEdgeConnecting();
			if (firstNodeConnectingMethod == EdgeConnectingMethodRowConfig.DEGREE_DRIVEN) {
				adjacentNodeId = network.getNodeToConnectDegreeDriven();
			} else if (firstNodeConnectingMethod == EdgeConnectingMethodRowConfig.CLUSTER_DRIVEN) {
				adjacentNodeId = network.getNodeToConnectClusterDriven();
			} else if (firstNodeConnectingMethod == EdgeConnectingMethodRowConfig.RANDOM_DRIVEN) {
				adjacentNodeId = network.getNodeToConnectRandomDriven();
			} else {
				System.err.println("Method driven '" + firstNodeConnectingMethod + "' is not valid!");
				this.resultNetwork = null;
				this.updateProgress(nDouble, nDouble);
				return null;
			}
			
			if (adjacentNodeId == -1) {
				network.addNode(actualNodeId);
				continue;
			} else {
				List<Integer> nodesToConnect = new ArrayList<Integer>();
				int[] adjacentNodes = network.getAdjacentNodesIds(adjacentNodeId);
				List<Integer> adjacentNodesList = CommonUtils.convertIntArrayToList(adjacentNodes);
				List<EdgeConnectingMethodRowConfig> rowConfigs = config.getEdgeConnectingMethodRowConfigs();
				for (EdgeConnectingMethodRowConfig rowConfig : rowConfigs) {
					for (int j = 0; j < rowConfig.getNumberOfEdges(); j++) {
						if (!adjacentNodesList.isEmpty()) {
							int[] adjacentNodesArray = CommonUtils.converIntListToArray(adjacentNodesList);
							int otherNodeConnectingMethod = rowConfig.getConnectingMethod();
							int otherAdjacentNodeId = -1;
							if (otherNodeConnectingMethod == EdgeConnectingMethodRowConfig.DEGREE_DRIVEN) {
								otherAdjacentNodeId = network.getNodeToConnectDegreeDriven(adjacentNodesArray);
							} else if (otherNodeConnectingMethod == EdgeConnectingMethodRowConfig.CLUSTER_DRIVEN) {
								otherAdjacentNodeId = network.getNodeToConnectClusterDriven(adjacentNodesArray);
							} else if (otherNodeConnectingMethod == EdgeConnectingMethodRowConfig.RANDOM_DRIVEN) {
								otherAdjacentNodeId = network.getNodeToConnectRandomDriven(adjacentNodesArray);
							} else {
								System.err.println("Method driven '" + firstNodeConnectingMethod + "' is not valid!");
								this.resultNetwork = null;
								this.updateProgress(nDouble, nDouble);
								return null;
							}
							
							if (otherAdjacentNodeId == -1) {
								continue;
							} else {
								nodesToConnect.add(otherAdjacentNodeId);
								adjacentNodesList.remove(new Integer(otherAdjacentNodeId));
							}
						}
					}
				}
				
				network.addNode(actualNodeId);
				network.addEdge(actualNodeId, adjacentNodeId);
				for (Integer nodeToConnect : nodesToConnect) {
					network.addEdge(actualNodeId, nodeToConnect);
				}
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
