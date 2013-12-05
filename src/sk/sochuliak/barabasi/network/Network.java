package sk.sochuliak.barabasi.network;

import java.util.List;

public interface Network {

	/**
	 * Adds node to network identified by its id.
	 * 
	 * @param nodeId Id of node
	 * @return True if node successfully added, false otherwise
	 */
	public boolean addNode(int nodeId);
	
	/**
	 * Adds edge between two nodes identified by their ids.
	 * 
	 * @param nodeId1 Id of first node
	 * @param nodeId2 Id of second node
	 * @return True if edge successfully added, false otherwise
	 */
	public boolean addEdge(int nodeId1, int nodeId2);
	
	/**
	 * Returns id of node by degree driven method.
	 * @return ID of node
	 */
	public int getNodeToConnectDegreeDriven();
	
	/**
	 * Returns id of node by degree driven method.
	 * @param nodesIds ids from which result will be given
	 * @return ID of node
	 */
	public int getNodeToConnectDegreeDriven(int[] nodesIds);
	
	/**
	 * Returns id of node by cluster driven method.
	 * @return ID of node
	 */
	public int getNodeToConnectClusterDriven();
	
	/**
	 * Returns id of node by cluster driven method.
	 * @param nodesIds ids from which result will be given
	 * @return ID of node
	 */
	public int getNodeToConnectClusterDriven(int[] nodesIds);
	
	/**
	 * Returns id of node by random driven method.
	 * @return ID of node
	 */
	public int getNodeToConnectRandomDriven();
	
	/**
	 * Returns id of node by random driven method.
	 * @param nodesIds ids from which result will be given
	 * @return ID of node
	 */
	public int getNodeToConnectRandomDriven(int[] nodesIds);
	
	/**
	 * Returns number of existing edges between nodes identified by their ids.
	 * 
	 * @param nodesIds Ids of nodes
	 * @return Number of existing edges
	 */
	public int getNumberOfExistingEdgesBetweenNodes(int[] nodesIds);
	
	/**
	 * Returns number of all possible edges between nodes identified by their ids.
	 * 
	 * @param nodesIds Ids of nodes
	 * @return Number of all possible edges
	 */
	public int getNumberOfAllPossibleEdgesBetweenNodes(int[] nodesIds);
	
	/**
	 * Returns ids of all nodes connected by edge to given node identified by its id.
	 * @param nodeId Id of node
	 * @return Array of connected nodes
	 */
	public int[] getAdjacentNodesIds(int nodeId);
	
	/**
	 * Returns number of nodes connected by edge to given node identified by its id.
	 * 
	 * @param nodeId Id of node
	 * @return Number of connected nodes
	 */
	public int getAdjacentNodesCount(int nodeId);
	
	/**
	 * Checks if there is an edge between two nodes identified by their ids.
	 * 
	 * @param nodeId1 Id of node 1.
	 * @param nodeId2 Id of node 2.
	 * @return True if edge exists, false otherwise
	 */
	public boolean isEdgeBetweenNodes(int nodeId1, int nodeId2);
	
	/**
	 * Returns number of nodes in network.
	 * 
	 * @return Number of nodes
	 */
	public int getNumberOfNodes();
	
	/**
	 * Checks if network contains node identified by its id.
	 * 
	 * @param nodeId Id of node
	 * @return True if network contains node, false otherwise
	 */
	public boolean containsNode(int nodeId);
	
	/**
	 * Returns ids of all nodes.
	 * 
	 * @return Ids of nodes
	 */
	public int[] getNodesIds();
	
	/**
	 * Returns number of edges in network.
	 * @return numberOfEdges
	 */
	public int getNumberOfEdges();
	
	/**
	 * Returns number of edges of specific nodes.
	 * @param nodesIds IDs of nodes
	 * @return number of edges
	 */
	public int getNumberOfEdges(int[] nodesIds);
	
	/**
	 * Returns cluster ratio of given node identified by its id.
	 * 
	 * @param nodeId nodeId Id of node
	 * @return Cluster ratio of node
	 */
	public double getClusterRatio(int nodeId);
	
	/**
	 * Returns average node degree value.
	 * @return
	 */
	public double getAverageNodeDegree();
	
	/**
	 * Returns average cluster coefficient value.
	 * @return
	 */
	public double getAverageClusterRatio();

	/**
	 * Setter for build statistics.
	 * 
	 * @param networkBuildStatistics buildStatistics
	 */
	public void setNetworkBuildStatistics(NetworkBuildStatistics networkBuildStatistics);
	
	/**
	 * Getter for build statistics.
	 * 
	 * @return Build statistics
	 */
	public NetworkBuildStatistics getNetworkBuildStatistics();

	/**
	 * Returns pairs of neighboring nodes in network.
	 * @return list of neighboring nodes pairs
	 */
	public List<int[]> getPairsOfNeighboringNodes();
}
