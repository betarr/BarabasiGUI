package sk.sochuliak.barabasi.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetworkStatistics {
	
	public static int distanceBetweenNodes(Network network, int source, int target, boolean ignoreAndReturnZero) {
		
		if (ignoreAndReturnZero) {
			return 0;
		}
		
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


}
