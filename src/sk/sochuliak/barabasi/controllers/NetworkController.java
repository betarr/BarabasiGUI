package sk.sochuliak.barabasi.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sk.sochuliak.barabasi.network.MapNetwork;
import sk.sochuliak.barabasi.network.Network;
import sk.sochuliak.barabasi.network.NetworkAnalyse;


public class NetworkController {
	
	private Network network;
	
	public void setNetwork(Network network) {
		this.network = network;
	}
	
	public void createNetwork(List<int[]> neighboringPairs) {
		this.network = new MapNetwork();
		for (int[] neighboringPair : neighboringPairs) {
			this.network.addNode(neighboringPair[0]);
			this.network.addNode(neighboringPair[1]);
			this.network.addEdge(neighboringPair[0], neighboringPair[1]);
		}
	}
	
	public void buildNetwork(int growthManagement, int numberOfNodes, int numberOfEdges) {
		ControllerService.getAppController().showNewGraphProgressBarAndBuildNetwork(growthManagement, numberOfNodes, numberOfEdges);
	}
	
	public int getTotalNodesCount() {
		return this.network.getNumberOfNodes();
	}
	
	public double getAverageNodeDegree() {
		return this.network.getAverageNodeDegree();
	}
	
	public double getAverageClusterRatio() {
		return this.network.getAverageClusterRatio();
	}
	
	public Map<Integer, Double> getNetworkDegreeDistribution() {
		return NetworkAnalyse.getStandardizedDegreeDistribution(this.network);
	}
	
	public Map<Integer, Double> getNetworkClusterDistribution() {
		return NetworkAnalyse.getClusterDistribution(this.network);
	}

	public List<int[]> getPairsOfNeighboringNodes() {
		return this.network != null ? this.network.getPairsOfNeighboringNodes() : new ArrayList<int[]>(0);
	}
}
