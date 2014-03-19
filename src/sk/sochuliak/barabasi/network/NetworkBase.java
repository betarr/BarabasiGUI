package sk.sochuliak.barabasi.network;

import java.util.Random;

import sk.sochuliak.barabasi.utils.CommonUtils;
import sk.sochuliak.barabasi.utils.NetworkUtils;
import sk.sochuliak.barabasi.utils.TaskTimeCounter;

public abstract class NetworkBase {

	private Random random = new Random();
	
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
	
	public int getNodeToConnectDegreeDrivenNewWay(int[] availableNodes, Network network) {
		int result = -1;
		double allNodesDegree = (double) (network.getNumberOfEdges() * 2);
		while (result == -1) {
			int randomNodeIdIndex = random.nextInt(availableNodes.length);
			int randomNodeId = availableNodes[randomNodeIdIndex];
			double nodeDegree = (double) network.getAdjacentNodesCount(randomNodeId);
			double probability = ((double) nodeDegree + 1) / allNodesDegree;
			if (probability > random.nextDouble()) {
				result = randomNodeId;
			}
		}
		return result;
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
		
//		if (allNodesCount == 2) {
//			return nodesIds[this.random.nextInt(allNodesCount)];
//		}
		
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
		return nodesIds[this.random.nextInt(nodesIds.length)];
	}
	
	public double getAverageDistance(Network network) {
		TaskTimeCounter.getInstance().startTask("Calculating average distance for network");
		int numberOfDistances = 10;
		int[] distances = new int[numberOfDistances];
		
		int[] nodesIds = network.getNodesIds();
		if (nodesIds.length == 0 || nodesIds.length == 1) {
			return -1;
		}
		if (nodesIds.length == 1) {
			return 0;
		}
		
		
		for (int i = 0; i < numberOfDistances; i++) {
			int startNodeId = nodesIds[this.random.nextInt(nodesIds.length)];
			int endNodeId = nodesIds[this.random.nextInt(nodesIds.length)];
			
			distances[i] = NetworkStatistics.distanceBetweenNodes(network, startNodeId, endNodeId, true);	
		}
		
		int distancesSum = 0;
		for (int distance : distances) {
			distancesSum += distance;
		}
		TaskTimeCounter.getInstance().endTask("Calculating average distance for network");
		return (double) distancesSum / (double) numberOfDistances;
	}
}
