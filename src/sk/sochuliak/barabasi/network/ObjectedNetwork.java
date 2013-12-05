package sk.sochuliak.barabasi.network;

import java.util.ArrayList;
import java.util.List;

public class ObjectedNetwork extends NetworkBase implements Network {

	private List<ObjectedNode> nodes;
	
	public ObjectedNetwork() {
		this.nodes = new ArrayList<ObjectedNode>();
	}
	
	@Override
	public boolean addNode(int nodeId) {
		if (this.containsNode(nodeId)) {
			return false;
		}
		
		this.nodes.add(new ObjectedNode(nodeId));
		return true;
	}

	@Override
	public boolean addEdge(int nodeId1, int nodeId2) {	
		if (nodeId1 == nodeId2) {
			return false;
		}
		
		ObjectedNode node1 = this.getNodeById(nodeId1);
		ObjectedNode node2 = this.getNodeById(nodeId2);
		if (node1 != null && node2 != null) {
			if (!this.isEdgeBetweenNodes(nodeId1, nodeId2)) {
				node1.addEdge(node2);
				node2.addEdge(node1);
				return true;
			}
		}
		return false;
	}

	@Override
	public int getNodeToConnectDegreeDriven() {
		return this.getNodeToConnectDegreeDriven();
	}

	@Override
	public int getNodeToConnectDegreeDriven(int[] nodesIds) {
		return this.getNodeToConnectDegreeDriven(nodesIds);
	}

	@Override
	public int getNodeToConnectClusterDriven() {
		return this.getNodeToConnectClusterDriven();
	}

	@Override
	public int getNodeToConnectClusterDriven(int[] nodesIds) {
		return this.getNodeToConnectClusterDriven(nodesIds);
	}

	@Override
	public int getNodeToConnectRandomDriven() {
		return this.getNodeToConnectRandomDriven();
	}

	@Override
	public int getNodeToConnectRandomDriven(int[] nodesIds) {
		return this.getNodeToConnectRandomDriven(nodesIds);
	}

	@Override
	public int getNumberOfExistingEdgesBetweenNodes(int[] nodesIds) {
		ObjectedNode[] nodes = this.getNodesByIds(nodesIds);
		
		int numberOfEdges = 0;
		
		for (ObjectedNode node1 : nodes) {
			for (ObjectedNode node2 : nodes) {
				numberOfEdges += node1.hasEdgeTo(node2) ? 1 : 0;
			}
		}
		return numberOfEdges / 2;
	}

	@Override
	public int getNumberOfAllPossibleEdgesBetweenNodes(int[] nodesIds) {
		return this.calculateNumberOfAllPossibleEdgesBetweenNodes(nodesIds.length);
	}

	@Override
	public int[] getAdjacentNodesIds(int nodeId) {
		ObjectedNode node = this.getNodeById(nodeId);
		int[] result = new int[node.getAdjacentNodesCount()];
		if (result.length > 0) {
			int pointer = 0;
			List<ObjectedNode> adjacentNodes = node.getAdjacentNodes();
			for (ObjectedNode adjacentNode : adjacentNodes) {
				result[pointer] = adjacentNode.getId();
				pointer++;
			}
		}
		return result;
	}

	@Override
	public int getAdjacentNodesCount(int nodeId) {
		return this.getNodeById(nodeId).getAdjacentNodesCount();
	}

	@Override
	public boolean isEdgeBetweenNodes(int nodeId1, int nodeId2) {
		return this.getNodeById(nodeId1).hasEdgeTo(this.getNodeById(nodeId2));
	}

	@Override
	public int getNumberOfNodes() {
		return this.nodes.size();
	}

	@Override
	public boolean containsNode(int nodeId) {
		for (ObjectedNode node : this.nodes) {
			if (node.getId() == nodeId) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int[] getNodesIds() {
		int[] result = new int[this.getNumberOfNodes()];
		int pointer = 0;
		for (ObjectedNode node : this.nodes) {
			result[pointer] = node.getId();
			pointer++;
		}
		return result;
	}
	
	@Override
	public int getNumberOfEdges() {
		int result = 0;
		for (ObjectedNode node : this.nodes) {
			result += node.getAdjacentNodesCount();
		}
		return result / 2;
	}
	
	@Override
	public int getNumberOfEdges(int[] nodesIds) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getClusterRatio(int nodeId) {
		int[] adjacentNodesIds = this.getAdjacentNodesIds(nodeId);
		int existingEdges = this.getNumberOfExistingEdgesBetweenNodes(adjacentNodesIds);
		int allPossibleEdges = this.getNumberOfAllPossibleEdgesBetweenNodes(adjacentNodesIds);
		if (allPossibleEdges == 0) {
			return 0;
		}
		return (double)existingEdges / (double)allPossibleEdges;
	}
	
	@Override
	public double getAverageNodeDegree() {
		return NetworkAnalyse.calculateAverageNodeDegree(this);
	}

	@Override
	public double getAverageClusterRatio() {
		return NetworkAnalyse.calculateAverageClusteRatios(this);
	}
	
	@Override
	public void setNetworkBuildStatistics(
			NetworkBuildStatistics networkBuildStatistics) {
		this.setBuildStatistics(networkBuildStatistics);
	}

	@Override
	public NetworkBuildStatistics getNetworkBuildStatistics() {
		return this.getBuildStatistics();
	}
	
	@Override
	public List<int[]> getPairsOfNeighboringNodes() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns node identified by nodeId.
	 * 
	 * @param nodeId Id of node
	 * @return Node if exists, null otherwise
	 */
	private ObjectedNode getNodeById(int nodeId) {
		for (ObjectedNode node : this.nodes) {
			if (node.getId() == nodeId) {
				return node;
			}
		}
		return null;
	}
	
	/**
	 * Returns array of nodes by their ids.
	 * 
	 * @param nodesIds Nodes ids
	 * @return Array of nodes
	 */
	private ObjectedNode[] getNodesByIds(int[] nodesIds) {
		ObjectedNode[] result = new ObjectedNode[nodesIds.length];
		for (int i = 0; i < nodesIds.length; i++) {
			result[i] = this.getNodeById(nodesIds[i]);
		}
		return result;
	}
}
