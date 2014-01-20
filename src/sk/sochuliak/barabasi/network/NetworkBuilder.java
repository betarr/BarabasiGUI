package sk.sochuliak.barabasi.network;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

import org.apache.log4j.Logger;

import sk.sochuliak.barabasi.controllers.ControllerService;
import sk.sochuliak.barabasi.utils.CommonUtils;

public class NetworkBuilder extends SwingWorker<Void, Void> {
	
	private static Logger logger = Logger.getLogger(NetworkBuilder.class);
	
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
			if (firstNodeConnectingMethod == EdgeConnectingMethodRowConfig.DRIVEN_DEGREE) {
				adjacentNodeId = network.getNodeToConnectDegreeDriven();
			} else if (firstNodeConnectingMethod == EdgeConnectingMethodRowConfig.DRIVEN_CLUSTER) {
				adjacentNodeId = network.getNodeToConnectClusterDriven();
			} else if (firstNodeConnectingMethod == EdgeConnectingMethodRowConfig.DRIVEN_RANDOM) {
				adjacentNodeId = network.getNodeToConnectRandomDriven();
			} else {
				logger.error(String.format("Method driven %s is no valid!", String.valueOf(firstNodeConnectingMethod)));
				this.resultNetwork = null;
				this.updateProgress(nDouble, nDouble);
				return null;
			}
			
			if (adjacentNodeId == -1) {
				network.addNode(actualNodeId);
				continue;
			} else {
				List<Integer> nodesToConnect = new ArrayList<Integer>();
				List<EdgeConnectingMethodRowConfig> rowConfigs = config.getEdgeConnectingMethodRowConfigs();
				for (EdgeConnectingMethodRowConfig rowConfig : rowConfigs) {
					List<Integer> nodesToChooseFromList = null;
					if (rowConfig.getRange() == EdgeConnectingMethodRowConfig.RANGE_NEIGHBOR) {
						nodesToChooseFromList = CommonUtils.convertIntArrayToList(network.getAdjacentNodesIds(adjacentNodeId));
					} else if (rowConfig.getRange() == EdgeConnectingMethodRowConfig.RANGE_ALL) {
						nodesToChooseFromList = CommonUtils.convertIntArrayToList(network.getNodesIds());
						for (Integer nodeExcept : nodesToConnect) {
							nodesToChooseFromList.remove(new Integer(nodeExcept));
						}
						nodesToChooseFromList.remove(new Integer(adjacentNodeId));
					} else {
						logger.error(String.format("Unexpected EdgeConnectingMethodRowConfig range: %s!", String.valueOf(rowConfig.getRange())));
						this.resultNetwork = null;
						this.updateProgress(nDouble, nDouble);
						return null;
					}
					for (int j = 0; j < rowConfig.getNumberOfEdges(); j++) {
						if (!nodesToChooseFromList.isEmpty()) {
							int[] nodesToChooseFrom = CommonUtils.converIntListToArray(nodesToChooseFromList);
							int otherNodeConnectingMethod = rowConfig.getConnectingMethod();
							int otherAdjacentNodeId = -1;
							if (otherNodeConnectingMethod == EdgeConnectingMethodRowConfig.DRIVEN_DEGREE) {
								otherAdjacentNodeId = network.getNodeToConnectDegreeDriven(nodesToChooseFrom);
							} else if (otherNodeConnectingMethod == EdgeConnectingMethodRowConfig.DRIVEN_CLUSTER) {
								otherAdjacentNodeId = network.getNodeToConnectClusterDriven(nodesToChooseFrom);
							} else if (otherNodeConnectingMethod == EdgeConnectingMethodRowConfig.DRIVEN_RANDOM) {
								otherAdjacentNodeId = network.getNodeToConnectRandomDriven(nodesToChooseFrom);
							} else {
								logger.error(String.format("Method driven %s is not valid!", String.valueOf(otherNodeConnectingMethod)));
								this.resultNetwork = null;
								this.updateProgress(nDouble, nDouble);
								return null;
							}
							
							if (otherAdjacentNodeId == -1) {
								logger.warn("Adjacent node should not by -1!");
								continue;
							} else {
								nodesToConnect.add(otherAdjacentNodeId);
								nodesToChooseFromList.remove(new Integer(otherAdjacentNodeId));
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
		ControllerService.getAppController().dispozeNewNetworkProgressBarDialog();
		ControllerService.getNetworkController().addNetwork(config.getName(), this.resultNetwork);
		ControllerService.getAppController().updateDataInBasicPropertiesTable(config.getName());
	}

}
