package sk.sochuliak.barabasi.network;

import java.util.Random;

import sk.sochuliak.barabasi.utils.CommonUtils;
import sk.sochuliak.barabasi.utils.NetworkUtils;

public abstract class NetworkBase {

	protected NetworkBuildStatistics buildStatistics;
	
	protected int calculateNumberOfAllPossibleEdgesBetweenNodes(int numberOfNodes) {
		int count = ((numberOfNodes-1)*numberOfNodes) / 2;
		return count;
	}
	
	public int getNodeToConnectDegreeDriven(Network network) {
		return this.getNodeToConnectDegreeDriven(network.getNodesIds(), network);
	}
	
	public int getNodeToConnectDegreeDriven(int[] nodesIds, Network network) {
		int allNodesCount = nodesIds.length;
		if (allNodesCount == 0) {
			return -1;
		}
		if (allNodesCount == 1) {
			return nodesIds[0];
		}
		int allEdgesCount = network.getNumberOfEdges(nodesIds);
		
		double iteration = (double) allNodesCount / (double) (allEdgesCount*2);
		double randomValue = Math.random() * allNodesCount;
		int areaCounter = 0;
		for (int i = 0; i < allNodesCount; i++) {
			int candidateNodeId = nodesIds[i];
			int adjacentNodesCount = network.getAdjacentNodesCount(candidateNodeId);
			double rangeFrom = areaCounter * iteration;
			double rangeTo = (areaCounter + adjacentNodesCount) * iteration;
			if (randomValue >= rangeFrom && randomValue < rangeTo) {
				return candidateNodeId;
			}
			areaCounter += adjacentNodesCount;
		}
		return -1;
	}
	
	public int getNodeToConnectClusterDriven(Network network) {
		return this.getNodeToConnectClusterDriven(network.getNodesIds(), network);
	}

	public int getNodeToConnectClusterDriven(int[] nodesIds, Network network) {
		int allNodesCount = nodesIds.length;
		if (allNodesCount == 0) {
			return -1;
		}
		if (allNodesCount == 1) {
			return nodesIds[0];
		}
		
		if (allNodesCount == 2) {
			Random r = new Random();
			return nodesIds[r.nextInt(allNodesCount)];
		}
		
		double[] clusterRatios = NetworkUtils.calculateClusterRatios(nodesIds, network);
		double sumOfClusterRatios = CommonUtils.sumOfDoubleArray(clusterRatios);
		double sumOfClusterRatiosPlusOnes = sumOfClusterRatios + (double) allNodesCount;
		
		double randomValue = Math.random() * sumOfClusterRatiosPlusOnes;
		double areaCounter = 0d;
		for (int i = 0; i < nodesIds.length; i++) {
			int candidateNodeId = nodesIds[i];
			double candidatesClusterRatio = clusterRatios[i];
			double rangeFrom = areaCounter;
			double rangeTo = areaCounter + candidatesClusterRatio + 1d;
			if (randomValue >= rangeFrom && randomValue < rangeTo) {
				return candidateNodeId;
			}
			areaCounter = rangeTo;
		}
		return -1;
	}

	public int getNodeToConnectRandomDriven(Network network) {
		return this.getNodeToConnectRandomDriven(network.getNodesIds(), network);
	}

	public int getNodeToConnectRandomDriven(int[] nodesIds, Network network) {
		if (nodesIds.length == 0) {
			return -1;
		}
		Random r = new Random();
		return nodesIds[r.nextInt(nodesIds.length)];
	}

	protected NetworkBuildStatistics getBuildStatistics() {
		return buildStatistics;
	}

	protected void setBuildStatistics(NetworkBuildStatistics buildStatistics) {
		this.buildStatistics = buildStatistics;
	}
}
