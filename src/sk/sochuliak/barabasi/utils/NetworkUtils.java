package sk.sochuliak.barabasi.utils;

import sk.sochuliak.barabasi.network.Network;

public class NetworkUtils {

	/**
	 * Checks if nodesIds array contains nodeId.
	 * 
	 * @param nodeId Id of node
	 * @param nodesIds Array of nodesIds
	 * @return true if contains, false otherwise
	 */
	public static boolean isNodeIdInNodesIdsArray(int nodeId, int[] nodesIds) {
		for (int i = 0; i < nodesIds.length; i++) {
			if (nodesIds[i] == nodeId) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns index of NodeId in NodesIds array
	 * 
	 * @param nodeId Id of node
	 * @param nodesIds Array of nodes ids
	 * @return index of node id or -1 if array does not contain node id
	 */
	public static int getIndexOfNodeIdInNodesIdsArray(int nodeId, int[] nodesIds) {
		for (int i = 0; i < nodesIds.length; i++) {
			if (nodesIds[i] == nodeId) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Returns cluster ratios of given nodes in network.
	 * @param nodesIds Nodes ids
	 * @param network Network
	 * @return Array of cluster ratios.
	 */
	public static double[] calculateClusterRatios(int[] nodesIds, Network network) {
		double[] result = new double[nodesIds.length];
		for (int i = 0; i < nodesIds.length; i++) {
			int nodeId = nodesIds[i];
			double clusterRatio = network.getClusterRatio(nodeId);
			result[i] = clusterRatio;
		}
		return result;
	}
}
