package sk.sochuliak.barabasi.network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import sk.sochuliak.barabasi.utils.CommonUtils;
import sk.sochuliak.barabasi.utils.NetworkUtils;

public abstract class NetworkBase {

	protected NetworkBuildStatistics buildStatistics;
	
	protected int calculateNumberOfAllPossibleEdgesBetweenNodes(int numberOfNodes) {
		int count = ((numberOfNodes-1)*numberOfNodes) / 2;
		return count;
	}
	
	protected int[] calculateAdjacentNodesDegreeDriven2(int nodesCountToCalculate, Network network) {		
		int[] allNodesIds = network.getNodesIds();
		int allNodesCount = allNodesIds.length;
		
		if (allNodesCount <= nodesCountToCalculate) {
			int[] result = new int[allNodesCount];
			for (int i = 0; i < allNodesCount; i++) {
				result[i] = allNodesIds[i];
			}
			return result;
		}
		
		int[] result = new int[nodesCountToCalculate];
		Arrays.fill(result, -1);
		int allEdgesCount = network.getNumberOfEdges();
		
		int numberOfCalculatedNodes = 0;
		double iteration = (double) allNodesCount / (double) (allEdgesCount*2);
		while (numberOfCalculatedNodes != nodesCountToCalculate) {
			double randomValue = Math.random() * allNodesCount;
			int areaCounter = 0;
			for (int i = 0; i < allNodesCount; i++) {
				int candidateNodeId = allNodesIds[i];
				int adjacentNodesCount = network.getAdjacentNodesCount(candidateNodeId);
				double rangeFrom = areaCounter * iteration;
				double rangeTo = (areaCounter + adjacentNodesCount) * iteration;
				if (randomValue >= rangeFrom && randomValue < rangeTo) {
					if (!NetworkUtils.isNodeIdInNodesIdsArray(candidateNodeId, result)) {
						result[numberOfCalculatedNodes] = candidateNodeId;
						numberOfCalculatedNodes++;
					}
					break;
				}
				areaCounter += adjacentNodesCount;
			}
		}
		return result;
	}
	
	protected int[] calculateAdjacentNodesDegreeDriven(int nodesCountToCalculate, Network network) {
		int[] allNodesIds = network.getNodesIds();
		int allNodesCount = allNodesIds.length;
		
		if (allNodesCount <= nodesCountToCalculate) {
			int[] result = new int[allNodesCount];
			for (int i = 0; i < allNodesCount; i++) {
				result[i] = allNodesIds[i];
			}
			return result;
		}
		
		int[] result = new int[nodesCountToCalculate];
		Arrays.fill(result, -1);
		int allEdgesCount = network.getNumberOfEdges();
		
		// calculating 1st adjacent node
		double iteration = (double) allNodesCount / (double) (allEdgesCount*2);
		double randomValue = Math.random() * allNodesCount;
		int areaCounter = 0;
		for (int i = 0; i < allNodesCount; i++) {
			int candidateNodeId = allNodesIds[i];
			int adjacentNodesCount = network.getAdjacentNodesCount(candidateNodeId);
			double rangeFrom = areaCounter * iteration;
			double rangeTo = (areaCounter + adjacentNodesCount) * iteration;
			if (randomValue >= rangeFrom && randomValue < rangeTo) {
				result[0] = candidateNodeId;
				break;
			}
			areaCounter += adjacentNodesCount;
		}
		
		int[] adjacentNodes = network.getAdjacentNodesIds(result[0]);
		if (adjacentNodes.length <= nodesCountToCalculate-1) {
			for (int i = 0; i < adjacentNodes.length; i++) {
				result[i+1] = adjacentNodes[i];
			}
		} else {
			Random r = new Random();
			List<Integer> adjacentNodesAsList = new ArrayList<Integer>();
			for (int adjacentNode : adjacentNodes) {
				adjacentNodesAsList.add(adjacentNode);
			}
			for (int i = 0; i < nodesCountToCalculate-1; i++) {
				int randomNodeIndex = r.nextInt(adjacentNodesAsList.size());
				result[i+1] = adjacentNodes[randomNodeIndex];
				adjacentNodesAsList.remove(randomNodeIndex);
			}
		}
		return result;
	}
	
	public int[] calculateAdjacentNodesClusterDriven(int nodesCountToCalculate, Network network) {
		int[] allNodesIds = network.getNodesIds();
		int allNodesCount = allNodesIds.length;
		
		if (allNodesCount <= nodesCountToCalculate) {
			int[] result = new int[allNodesCount];
			for (int i = 0; i < allNodesCount; i++) {
				result[i] = allNodesIds[i];
			}
			return result;
		}
		
		int[] result = new int[nodesCountToCalculate];
		Arrays.fill(result, -1);
		
		double[] clusterRatios = NetworkUtils.calculateClusterRatios(allNodesIds, network);
		double sumOfClusterRatios = CommonUtils.sumOfDoubleArray(clusterRatios);
		double sumOfClusterRatiosPlusOnes = sumOfClusterRatios + (double)network.getNumberOfNodes();
		
		// calculating 1st adjacent node
		double randomValue = Math.random() * sumOfClusterRatiosPlusOnes;
		double areaCounter = 0d;
		for (int i = 0; i < allNodesIds.length; i++) {
			int candidateNodeId = allNodesIds[i];
			double candidatesClusterRatio = clusterRatios[i];
			double rangeFrom = areaCounter;
			double rangeTo = areaCounter + candidatesClusterRatio + 1.0;
			if (randomValue >= rangeFrom && randomValue < rangeTo) {
				result[0] = candidateNodeId;
				break;
			}
			areaCounter = rangeTo;
		}
		
		int[] adjacentNodes = network.getAdjacentNodesIds(result[0]);
		if (adjacentNodes.length <= nodesCountToCalculate-1) {
			for (int i = 0; i < adjacentNodes.length; i++) {
				result[i+1] = adjacentNodes[i];
			}
		} else {
			Random r = new Random();
			List<Integer> adjacentNodesAsList = new ArrayList<Integer>();
			for (int adjacentNode : adjacentNodes) {
				adjacentNodesAsList.add(adjacentNode);
			}
			for (int i = 0; i < nodesCountToCalculate-1; i++) {
				int randomNodeIndex = r.nextInt(adjacentNodesAsList.size());
				result[i+1] = adjacentNodes[randomNodeIndex];
				adjacentNodesAsList.remove(randomNodeIndex);
			}
		}
		return result;
	}
	
	public int[] calculateAdjacentNodesRandomDriven(int nodesCountToCalculate, Network network) {
		int[] allNodesIds = network.getNodesIds();
		int allNodesCount = allNodesIds.length;
		
		if (allNodesCount <= nodesCountToCalculate) {
			int[] result = new int[allNodesCount];
			for (int i = 0; i < allNodesCount; i++) {
				result[i] = allNodesIds[i];
			}
			return result;
		}
		
		int[] result = new int[nodesCountToCalculate];
		Arrays.fill(result, -1);
		
		int foundedNodes = 0;
		Random r = new Random();
		List<Integer> resultList = new ArrayList<Integer>();
		while (foundedNodes < nodesCountToCalculate) {
			int nodeIndex = r.nextInt(allNodesCount);
			if (!resultList.contains(nodeIndex)) {
				resultList.add(nodeIndex);
				result[foundedNodes] = nodeIndex;
				foundedNodes++;
			}
		}
		return result;
	}

	protected NetworkBuildStatistics getBuildStatistics() {
		return buildStatistics;
	}

	protected void setBuildStatistics(NetworkBuildStatistics buildStatistics) {
		this.buildStatistics = buildStatistics;
	}
}
