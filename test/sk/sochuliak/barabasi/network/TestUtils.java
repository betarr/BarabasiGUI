package sk.sochuliak.barabasi.network;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import sk.sochuliak.giraphe.network.Network;

public class TestUtils {

	private static int[] edgesFrom = new int[]{0, 6,  12, 7, 2, 1, 12, 18, 5,  12, 16, 6,  10, 0,  14, 14, 22, 19, 23, 12, 17, 3, 4,  8, 8,  12, 2,  5,  7,  7,  2};
	private static int[] edgesTo = new int[]{  6, 12, 7,  3, 6, 5, 18, 22, 22, 16, 20, 16, 16, 15, 18, 24, 24, 23, 24, 17, 21, 4, 14, 9, 13, 13, 22, 11, 13, 18, 12};
	
	public static List<int[]> getExpectedNodePairs() {
		List<int[]> expectedPairs = new ArrayList<int[]>();
		for (int i = 0; i < Math.min(edgesFrom.length, edgesTo.length); i++) {
			expectedPairs.add(new int[]{edgesFrom[i], edgesTo[i]});
		}
		return expectedPairs;
	}
	
	public static Network buildSimpleNetwork(Network network) {
		for (int i = 0; i < 3; i++) {
			network.addNode(i);
		}
		network.addEdge(0, 1);
		return network;
	}
	
	public static Network buildComplexNetwork(Network network) {
		for (int i = 0; i <= 24; i++) {
			network.addNode(i);
		}
		
		for (int i = 0; i < edgesFrom.length; i++) {
			network.addEdge(edgesFrom[i], edgesTo[i]);
		}
		return network;
	}
	
	public static boolean arePairsOfNeighboringNodesEquals(List<int[]> expectedPairs, List<int[]> actualPairs) {
		if (expectedPairs.size() != actualPairs.size()) {
			return false;
		}
		for (int[] expectedPair : expectedPairs) {
			boolean pairFound = false;
			for (int[] actualPair : actualPairs) {
				if (expectedPair[0] == actualPair[0] && expectedPair[1] == actualPair[1]) {
					pairFound = true;
				} else if (expectedPair[0] == actualPair[1] && expectedPair[1] == actualPair[0]) {
					pairFound = true;
				}
			}
			if (!pairFound) {
				return false;
			}
		}
		return true;
	}
	
	public static SortedSet<Integer> getArrayAsSet(int[] nodesIds) {
		SortedSet<Integer> result = new TreeSet<Integer>();
		for (int nodeId : nodesIds) {
			result.add(nodeId);
		}
		return result;
	}
}
