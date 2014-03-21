package sk.sochuliak.barabasi.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import sk.sochuliak.barabasi.utils.CommonUtils;
import sk.sochuliak.barabasi.utils.NetworkUtils;
import sk.sochuliak.barabasi.utils.TaskTimeCounter;

public class NetworkStatistics {
	
	public static double getAverageDistanceBetweenNodes(Network network, boolean ignoreAndReturnZero) {
		if (ignoreAndReturnZero) {
			return 0d;
		}
		TaskTimeCounter.getInstance().startTask(String.format("Calculating average distance for network %s", network.getName()));
		int numberOfDistances = 10;
		int[] distances = new int[numberOfDistances];
		
		int[] nodesIds = network.getNodesIds();
		if (nodesIds.length == 0 || nodesIds.length == 1) {
			return -1;
		}
		if (nodesIds.length == 1) {
			return 0;
		}
		
		Random r = new Random();
		for (int i = 0; i < numberOfDistances; i++) {
			int startNodeId = nodesIds[r.nextInt(nodesIds.length)];
			int endNodeId = nodesIds[r.nextInt(nodesIds.length)];
			
			distances[i] = NetworkStatistics.getDistanceBetweenNodes(network, startNodeId, endNodeId);	
		}
		
		int distancesSum = 0;
		for (int distance : distances) {
			distancesSum += distance;
		}
		TaskTimeCounter.getInstance().endTask(String.format("Calculating average distance for network %s", network.getName()));
		return (double) distancesSum / (double) numberOfDistances;
	}
	
	/**
	 * Returns distance between source node and target node in network
	 * @param network Network
	 * @param source Source node id
	 * @param target Target node id
	 * @return distance between source node and target node
	 */
	public static int getDistanceBetweenNodes(Network network, int source, int target) {
		TaskTimeCounter.getInstance().startTask(String.format("Calculating distance from node %d to node %d in network %s", source, target, network.getName()));
		Map<Integer, Integer> distances = new HashMap<Integer, Integer>();
		for (int nodeId : network.getNodesIds()) {
			distances.put(nodeId, Integer.MAX_VALUE);
		}
		distances.put(source, 0);
		
		List<Integer> queue = new ArrayList<Integer>();
		queue.add(source);
		
		while (!queue.isEmpty()) {
			
			int smallestDistanceNode = getNodeWithsmallestDistance(queue, distances);
			if (smallestDistanceNode == target) {
				TaskTimeCounter.getInstance().endTask(String.format("Calculating distance from node %d to node %d in network %s", source, target, network.getName()));
				return distances.get(smallestDistanceNode);
			}
			
			for (int adjacentNodeId : network.getAdjacentNodesIds(smallestDistanceNode)) {
				int distance = distances.get(smallestDistanceNode)+1;
				if (distance < distances.get(adjacentNodeId)) {
					distances.put(adjacentNodeId, distance);
					queue.add(adjacentNodeId);
				}
			}
			
			queue.remove(new Integer(smallestDistanceNode));
			
		}
		
		TaskTimeCounter.getInstance().endTask(String.format("Calculating distance from node %d to node %d in network %s", source, target, network.getName()));
		return distances.get(target);
	}

	private static int getNodeWithsmallestDistance(List<Integer> queue, Map<Integer, Integer> distances) {
		int minNodeId = queue.get(0);
		int minDistance = distances.get(minNodeId);
		for (Integer nodeId : queue) {
			if (distances.get(nodeId) < minDistance) {
				minNodeId = nodeId;
				minDistance = distances.get(nodeId);
			}
		}
		return minNodeId;
	}

	/**
	 * Calculates maximal node degree in network.
	 * @param network Network
	 * @return Maximal node degree in network
	 */
	public static int getMaxNodeDegree(Network network) {
		TaskTimeCounter.getInstance().startTask(String.format("Calculating max node degree for network %s", network.getName()));
		
		int maxNodeDegree = 0;
		
		int[] nodeIds = network.getNodesIds();
		for (int nodeId : nodeIds) {
			int nodeDegree = network.getAdjacentNodesCount(nodeId);
			if (nodeDegree > maxNodeDegree) {
				maxNodeDegree = nodeDegree;
			}
		}
		
		TaskTimeCounter.getInstance().endTask(String.format("Calculating max node degree for network %s", network.getName()));
		return maxNodeDegree;
	}
	
	/**
	 * Calculates average node degree in network
	 * @param network Network
	 * @return Average node degree
	 */
	public static double getAverageNodeDegree(Network network) {
		TaskTimeCounter.getInstance().startTask(String.format("Calculating average node degree for network %s", network.getName()));
		int sumOfNodeDegrees = 0;
		int[] nodeIds = network.getNodesIds();
		for (int nodeId : nodeIds) {
			sumOfNodeDegrees += network.getAdjacentNodesCount(nodeId);
		}
		TaskTimeCounter.getInstance().endTask(String.format("Calculating average node degree for network %s", network.getName()));
		return (double) sumOfNodeDegrees / (double) nodeIds.length;
	}
	
	/**
	 * Calculates average cluster ratio in network
	 * @param network Network
	 * @return Average cluster ratio
	 */
	public static double getAverageClusteRatios(Network network) {
		TaskTimeCounter.getInstance().startTask(String.format("Calculating average cluster ratio for network %s", network.getName()));
		double[] clusterRatios = NetworkUtils.calculateClusterRatios(network.getNodesIds(), network);
		double sumOfClusterRatios = CommonUtils.sumOfDoubleArray(clusterRatios);
		TaskTimeCounter.getInstance().endTask(String.format("Calculating average cluster ratio for network %s", network.getName()));
		return sumOfClusterRatios / (double) clusterRatios.length;
	}
	
	/**
	 * Calculates degree distribution.
	 * Degree distribution is map where key is the degree of node and value is a list of nodes with this degree.
	 * @param network Network
	 * @return Map of degree distribution
	 */
	private static Map<Integer, List<Integer>> getDegreeDistribution(Network network) {
		Map<Integer, List<Integer>> result = new HashMap<Integer, List<Integer>>();
		
		int[] nodesIds = network.getNodesIds();
		for (int nodeId : nodesIds) {
			int adjacentNodesCount = network.getAdjacentNodesCount(nodeId);
			if (result.get(adjacentNodesCount) == null) {
				result.put(adjacentNodesCount, new ArrayList<Integer>());
			}
			result.get(adjacentNodesCount).add(nodeId);
		}
		return result;
	}

	/**
	 * Calculates standardized degree distribution.
	 * Degree distribution is map where key is the degree of node and value is normalized count of nodes with this degree.
	 * @param network Network
	 * @return Map of standardized degree distribution
	 */
	public static Map<Integer, Double> getStandardizedDegreeDistribution(Network network) {
		TaskTimeCounter.getInstance().startTask(String.format("Calculating degree distribution for network %s", network.getName()));
		Map<Integer, List<Integer>> degreeDistribution = NetworkStatistics.getDegreeDistribution(network);
		
		double nodesCountAsDouble = (double) network.getNumberOfNodes();
		
		Map<Integer, Double> result = new HashMap<Integer, Double>();
		for (Integer nodeDegree : degreeDistribution.keySet()) {
			result.put(nodeDegree, (double) degreeDistribution.get(nodeDegree).size() / nodesCountAsDouble);
		}
		TaskTimeCounter.getInstance().endTask(String.format("Calculating degree distribution for network %s", network.getName()));
		return result;
	}
	
	/**
	 * Calculates cluster distribution. 
	 * Cluster distribution is represented as map where key is node degree 
	 * and value is average cluster ratio of nodes
	 * with this degree.
	 * 
	 * @param network
	 * @return
	 */
	public static Map<Integer, Double> getClusterDistribution(Network network) {
		TaskTimeCounter.getInstance().startTask(String.format("Calculating cluster distribution for network %s", network.getName()));
		Map<Integer, Double> result = new HashMap<Integer, Double>();
		
		int[] nodesIds = network.getNodesIds();
		double[] clustersRatios = NetworkUtils.calculateClusterRatios(nodesIds, network);
		
		Map<Integer, List<Integer>> degreeDistribution = NetworkStatistics.getDegreeDistribution(network);
		
		for (Integer degree : degreeDistribution.keySet()) {
			List<Integer> nodes = degreeDistribution.get(degree);
			
			double clusterSum = 0d;
			for (Integer nodeId : nodes) {
				int indexOfNode = NetworkUtils.getIndexOfNodeIdInNodesIdsArray(nodeId, nodesIds);
				double clusterRatio = clustersRatios[indexOfNode];
				clusterSum += clusterRatio;
			}
			double averageCluster = clusterSum / (double) nodes.size();
			result.put(degree, averageCluster);
		}
		TaskTimeCounter.getInstance().endTask(String.format("Calculating cluster distribution for network %s", network.getName()));
		return result;
	}
}
