package sk.sochuliak.barabasi.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import sk.sochuliak.barabasi.gui.mainscreen.BasicPropertiesTable;
import sk.sochuliak.barabasi.network.MapNetwork;
import sk.sochuliak.barabasi.network.Network;
import sk.sochuliak.barabasi.network.NetworkBuildConfiguration;
import sk.sochuliak.barabasi.network.NetworkStatistics;


public class NetworkController {
	
	private static final Logger logger = Logger.getLogger(NetworkController.class);
	
	private Map<String, Network> networks = new HashMap<String, Network>();
	
	private Map<String, Map<Integer, Double>> networksDegreeDistribution = null;
	private Map<String, Map<Integer, Double>> networksClusterDistribution = null;
	
	private Map<String, Map<Integer, Object>> networksProperties = new HashMap<String, Map<Integer, Object>>();
	
	public void createNetwork(String networkName, List<int[]> neighboringPairs) {
		logger.info(String.format("Creating network %s from neighboringPairs", networkName));
		Network network = new MapNetwork(networkName);
		for (int[] neighboringPair : neighboringPairs) {
			network.addNode(neighboringPair[0]);
			network.addNode(neighboringPair[1]);
			network.addEdge(neighboringPair[0], neighboringPair[1]);
		}
		this.addNetwork(networkName, network);
	}
	
	public void buildNetwork(NetworkBuildConfiguration config) {
		logger.info(String.format("Building new network with config %s", config));
		ControllerService.getAppController().showNewNetworkProgressBarAndBuildNetwork(config);
	}
	
	public int getTotalNodesCount(String networkName) {
		Map<Integer, Object> networkProperties = this.getNetworkProperties(networkName);
		if (networkProperties.get(BasicPropertiesTable.TOTAL_NODES_COUNT) == null) {
			return -1;
		}
		return (int) networkProperties.get(BasicPropertiesTable.TOTAL_NODES_COUNT);
	}
	
	public double getAverageNodeDegree(String networkName) {
		Map<Integer, Object> networkProperties = this.getNetworkProperties(networkName);
		if (networkProperties.get(BasicPropertiesTable.AVERAGE_NODE_DEGREE) == null) {
			return -1d;
		}
		return (double) networkProperties.get(BasicPropertiesTable.AVERAGE_NODE_DEGREE);
	}
	
	public double getAverageClusterRatio(String networkName) {
		Map<Integer, Object> networkProperties = this.getNetworkProperties(networkName);
		if (networkProperties.get(BasicPropertiesTable.AVERAGE_CLUSTER_RATIO) == null) {
			return -1d;
		}
		return (double) networkProperties.get(BasicPropertiesTable.AVERAGE_CLUSTER_RATIO);
	}
	
	public int getNumberOfNeighboringNodes(String networkName) {
		Map<Integer, Object> networkProperties = this.getNetworkProperties(networkName);
		if (networkProperties.get(BasicPropertiesTable.NUMBER_OF_NEIGHBORING_NODES) == null) {
			return -1;
		}
		return (int) networkProperties.get(BasicPropertiesTable.NUMBER_OF_NEIGHBORING_NODES);
	}

	public double getAverateDistance(String networkName) {
		Map<Integer, Object> networkProperties = this.getNetworkProperties(networkName);
		if (networkProperties.get(BasicPropertiesTable.AVERAGE_DISTANCE) == null) {
			return -1d;
		}
		return (double) networkProperties.get(BasicPropertiesTable.AVERAGE_DISTANCE);
	}
	
	public int getMaxNodeDegree(String networkName) {
		Map<Integer, Object> networkProperties = this.getNetworkProperties(networkName);
		if (networkProperties.get(BasicPropertiesTable.MAX_NODE_DEGREE) == null) {
			return -1;
		}
		return (int) networkProperties.get(BasicPropertiesTable.MAX_NODE_DEGREE);
	}
	
	public Map<Integer, Double> getNetworkDegreeDistribution(String networkName) {
		if (this.networksDegreeDistribution == null) {
			this.networksDegreeDistribution = new HashMap<String, Map<Integer, Double>>();
		}
		if (this.networksDegreeDistribution.get(networkName) == null) {
			Map<Integer, Double> degreeDistribution = NetworkStatistics.getStandardizedDegreeDistribution(this.getNetwork(networkName));
			this.networksDegreeDistribution.put(networkName, degreeDistribution);
		}
		return this.networksDegreeDistribution.get(networkName);
	}
	
	public Map<Integer, Double> getNetworkClusterDistribution(String networkName) {
		if (this.networksClusterDistribution == null) {
			this.networksClusterDistribution = new HashMap<String, Map<Integer, Double>>();
		}
		if (this.networksClusterDistribution.get(networkName) == null) {
			Map<Integer, Double> clusterDistribution = NetworkStatistics.getClusterDistribution(this.getNetwork(networkName));
			this.networksClusterDistribution.put(networkName, clusterDistribution);
		}
		return this.networksClusterDistribution.get(networkName);
	}

	public List<int[]> getPairsOfNeighboringNodes(String networkName) {
		return this.getNetwork(networkName).getPairsOfNeighboringNodes();
	}
	
	public void removeNetwork(String networkName) {
		this.networks.remove(networkName);
		this.networksProperties.remove(networkName);
		if (this.networksDegreeDistribution != null) {
			this.networksDegreeDistribution.remove(networkName);
		}
		if (this.networksClusterDistribution != null) {
			this.networksClusterDistribution.remove(networkName);
		}
		ControllerService.getAppController().enableAnalysisMenuItems(!this.networks.isEmpty());
	}
	
	public void addNetwork(String networkName, Network network) {
		this.networks.put(networkName, network);
		ControllerService.getAppController().addNetworkToNetworkList(networkName);
		ControllerService.getAppController().enableAnalysisMenuItems(!this.networks.isEmpty());
	}
	
	private Network getNetwork(String networkName) {
		return this.networks.get(networkName);
	}
	
	public List<String> getNetworkNames() {
		return new ArrayList<String>(this.networks.keySet());
	}
	
	private Map<Integer, Object> getNetworkProperties(String networkName) {
		if (this.networksProperties.get(networkName) == null) {
			this.networksProperties.put(networkName, new HashMap<Integer, Object>());
		}
		return this.networksProperties.get(networkName);
	}
	
	public void refreshNetworkProperty(int propertyId) {
		String networkName = ControllerService.getAppController().getSelectedNetworkName();
		if (networkName == null) {
			return;
		}
		Object value = null;
		Network network = this.getNetwork(networkName);
		switch (propertyId) {
			case BasicPropertiesTable.TOTAL_NODES_COUNT : {
				value = network.getNumberOfNodes();
				break;
			}
			case BasicPropertiesTable.AVERAGE_NODE_DEGREE : {
				value = NetworkStatistics.getAverageNodeDegree(network);
				break;
			}
			case BasicPropertiesTable.AVERAGE_CLUSTER_RATIO : {
				value = NetworkStatistics.getAverageClusteRatios(network);
				break;
			}
			case BasicPropertiesTable.AVERAGE_DISTANCE : {
				value = NetworkStatistics.getAverageDistanceBetweenNodes(network, false);
				break;
			}
			case BasicPropertiesTable.NUMBER_OF_NEIGHBORING_NODES : {
				value = network.getPairsOfNeighboringNodes().size();
				break;
			}
			case BasicPropertiesTable.MAX_NODE_DEGREE : {
				value = NetworkStatistics.getMaxNodeDegree(network);
				break;
			}
		}
		Map<Integer, Object> networkProperties = this.getNetworkProperties(networkName);
		networkProperties.put(propertyId, value);
		ControllerService.getAppController().updatePropertyInPropertiesTable(propertyId, value);
	}
}
