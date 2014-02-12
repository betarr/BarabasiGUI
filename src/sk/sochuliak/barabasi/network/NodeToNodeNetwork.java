package sk.sochuliak.barabasi.network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NodeToNodeNetwork extends NetworkBase implements Network {
	
	/**
	 * Initial size of nodesIds array.
	 */
	private static final int INITIAL_SIZE = 1000;
	
	/**
	 * Ids of nodes.
	 */
	private int[] nodesIds;
	
	/**
	 * Pairs of ids of nodes representing edge.
	 */
	private List<int[]> edges;
	
	/**
	 * Number of nodes.
	 */
	private int numberOfNodes = 0;
	
	public NodeToNodeNetwork() {
		this.nodesIds = new int[NodeToNodeNetwork.INITIAL_SIZE];
		Arrays.fill(this.nodesIds, -1);
		this.edges = new ArrayList<int[]>();
	}
	
	@Override
	public boolean addNode(int nodeId) {
		if (this.containsNode(nodeId)) {
			return false;
		}
		
		if (this.numberOfNodes == this.nodesIds.length) {
			this.appendDoubleSizeOfNodesIds();
		}
		this.nodesIds[this.numberOfNodes] = nodeId;
		this.numberOfNodes++;
		return true;
	}

	@Override
	public boolean addEdge(int nodeId1, int nodeId2) {
		if (nodeId1 != nodeId2 && this.containsNode(nodeId1) && this.containsNode(nodeId2)) {
			if (!this.isEdgeBetweenNodes(nodeId1, nodeId2)) {
				this.edges.add(new int[]{nodeId1, nodeId2});
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
	public int getNodeToConnectDegreeDrivenNewWay(int[] availableNodes) {
		return this.getNodeToConnectDegreeDrivenNewWay(availableNodes);
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
		int numberOfEdges = 0;
		
		for (int nodeId1 : nodesIds) {
			for (int nodeId2 : nodesIds) {
				if (this.isEdgeBetweenNodes(nodeId1, nodeId2)) {
					numberOfEdges++;
				}
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
		int adjacentNodesCount = this.getAdjacentNodesCount(nodeId);
		int[] result = new int[adjacentNodesCount];
		if (adjacentNodesCount > 0) {
			int pointer = 0;
			for (int[] edge : this.edges) {
				if (edge[0] == nodeId) {
					result[pointer] = edge[1];
					pointer++;
				}else if (edge[1] == nodeId) {
					result[pointer] = edge[0];
					pointer++;
				}
			}
		}
		return result;
	}

	@Override
	public int getAdjacentNodesCount(int nodeId) {
		int result = 0;
		for (int[] edge : this.edges) {
			if (edge[0] == nodeId || edge[1] == nodeId) {
				result ++;
			}
		}
		return result;
	}

	@Override
	public boolean isEdgeBetweenNodes(int nodeId1, int nodeId2) {
		for (int[] edge : this.edges) {
			if (edge[0] == nodeId1 && edge[1] == nodeId2) {
				return true;
			} else if (edge[0] == nodeId2 && edge[1] == nodeId1) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int getNumberOfNodes() {
		return this.numberOfNodes;
	}

	@Override
	public boolean containsNode(int nodeId) {
		for (int i = 0; i < this.numberOfNodes; i++) {
			if (this.nodesIds[i] == nodeId) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int[] getNodesIds() {
		int[] result = new int[this.getNumberOfNodes()];
		for (int i = 0; i < this.numberOfNodes; i++) {
			result[i] = this.nodesIds[i];
		}
		return result;
	}
	
	@Override
	public int getNumberOfEdges() {
		int result =  this.edges.size();
		return result;
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
	 * Makes nodesIds array two times larger.
	 */
	private void appendDoubleSizeOfNodesIds() {
		int oldSize = this.nodesIds.length;
		int newSize = oldSize * 2;
		int[] newNodesIds = new int[newSize];
		for (int i = 0; i < newSize; i++) {
			newNodesIds[i] = (i < oldSize) ? this.nodesIds[i] : -1;
		}
		this.nodesIds = newNodesIds;
	}
}
