package sk.sochuliak.barabasi.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sk.sochuliak.barabasi.network.MapNetwork;
import sk.sochuliak.barabasi.network.Network;
import sk.sochuliak.barabasi.network.NetworkAnalyse;
import sk.sochuliak.barabasi.network.NetworkBuildConfiguration;


public class NetworkController {
	
	private Map<String, Network> networks = new HashMap<String, Network>();
	
	private Map<String, Integer> networksTotalNodesCount = new HashMap<String, Integer>();
	private Map<String, Double> networksAverageNodeDegree = new HashMap<String, Double>();
	private Map<String, Double> networksAverageClusterRatio = new HashMap<String, Double>();
	private Map<String, Map<Integer, Double>> networksDegreeDistribution = null;
	private Map<String, Map<Integer, Double>> networksClusterDistribution = null;
	private Map<String, List<int[]>> networksPairsOfNeighboringNodes = null;
	
	public void createNetwork(String networkName, List<int[]> neighboringPairs) {
		Network network = new MapNetwork();
		for (int[] neighboringPair : neighboringPairs) {
			network.addNode(neighboringPair[0]);
			network.addNode(neighboringPair[1]);
			network.addEdge(neighboringPair[0], neighboringPair[1]);
		}
		this.addNetwork(networkName, network);
	}
	
	public void buildNetwork(NetworkBuildConfiguration config) {
		ControllerService.getAppController().showNewGraphProgressBarAndBuildNetwork(config);
	}
	
	public int getTotalNodesCount(String networkName) {
		if (this.networksTotalNodesCount.get(networkName) == null) {
			int totalNodesCount = this.getNetwork(networkName).getNumberOfNodes();
			this.networksTotalNodesCount.put(networkName, totalNodesCount);
		}
		return this.networksTotalNodesCount.get(networkName);
	}
	
	public double getAverageNodeDegree(String networkName) {
		if (this.networksAverageNodeDegree.get(networkName) == null) {
			double averageNodeDegree = this.getNetwork(networkName).getAverageNodeDegree();
			this.networksAverageNodeDegree.put(networkName, averageNodeDegree);
		}
		return this.networksAverageNodeDegree.get(networkName);
	}
	
	public double getAverageClusterRatio(String networkName) {
		if (this.networksAverageClusterRatio.get(networkName) == null) {
			double averageClusterRatio = this.getNetwork(networkName).getAverageClusterRatio();
			this.networksAverageClusterRatio.put(networkName, averageClusterRatio);
		}
		return this.networksAverageClusterRatio.get(networkName);
	}
	
	public Map<Integer, Double> getNetworkDegreeDistribution(String networkName) {
		if (this.networksDegreeDistribution == null) {
			this.networksDegreeDistribution = new HashMap<String, Map<Integer, Double>>();
		}
		if (this.networksDegreeDistribution.get(networkName) == null) {
			Map<Integer, Double> degreeDistribution = NetworkAnalyse.getStandardizedDegreeDistribution(this.getNetwork(networkName));
			this.networksDegreeDistribution.put(networkName, degreeDistribution);
		}
		return this.networksDegreeDistribution.get(networkName);
	}
	
	public Map<Integer, Double> getNetworkClusterDistribution(String networkName) {
		if (this.networksClusterDistribution == null) {
			this.networksClusterDistribution = new HashMap<String, Map<Integer, Double>>();
		}
		if (this.networksClusterDistribution.get(networkName) == null) {
			Map<Integer, Double> clusterDistribution = NetworkAnalyse.getClusterDistribution(this.getNetwork(networkName));
			this.networksClusterDistribution.put(networkName, clusterDistribution);
		}
		return this.networksClusterDistribution.get(networkName);
	}

	public List<int[]> getPairsOfNeighboringNodes(String networkName) {
		if (this.networksPairsOfNeighboringNodes == null) {
			this.networksPairsOfNeighboringNodes = new HashMap<String, List<int[]>>();
		}
		if (this.networksPairsOfNeighboringNodes.get(networkName) == null) {
			List<int[]> pairsOfNeighboringNodes = this.getNetwork(networkName) != null ? this.getNetwork(networkName).getPairsOfNeighboringNodes() : new ArrayList<int[]>(0);
			this.networksPairsOfNeighboringNodes.put(networkName, pairsOfNeighboringNodes);
		}
		return this.networksPairsOfNeighboringNodes.get(networkName);
	}
	
	public void removeNetwork(String networkName) {
		this.networks.remove(networkName);
		this.networksTotalNodesCount.remove(networkName);
		this.networksAverageNodeDegree.remove(networkName);
		this.networksAverageClusterRatio.remove(networkName);
		if (this.networksDegreeDistribution != null) {
			this.networksDegreeDistribution.remove(networkName);
		}
		if (this.networksClusterDistribution != null) {
			this.networksClusterDistribution.remove(networkName);
		}
		if (this.networksPairsOfNeighboringNodes != null) {
			this.networksPairsOfNeighboringNodes.remove(networkName);
		}
		ControllerService.getAppController().enableAnalysisMenuItems(!this.networks.isEmpty());
	}
	
	public void addNetwork(String networkName, Network network) {
		this.networks.put(networkName, network);
		ControllerService.getAppController().addNetworkToGraphList(networkName);
		ControllerService.getAppController().enableAnalysisMenuItems(!this.networks.isEmpty());
	}
	
	private Network getNetwork(String networkName) {
		return this.networks.get(networkName);
	}
	
	public List<String> getNetworkNames() {
		return new ArrayList<String>(this.networks.keySet());
	}
}
