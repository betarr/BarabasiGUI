package sk.sochuliak.barabasi.controllers;

import java.util.Map;

import sk.sochuliak.barabasi.network.Network;
import sk.sochuliak.barabasi.network.NetworkAnalyse;


public class NetworkController {
	
	private Network network;
	
	public void setNetwork(Network network) {
		this.network = network;
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
}
