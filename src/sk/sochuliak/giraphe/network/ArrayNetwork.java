package sk.sochuliak.giraphe.network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class ArrayNetwork extends NetworkBase implements Network {
	
	/**
	 * Initial size of incidenceMatrix.
	 */
	private static final int INITIAL_SIZE = 1000;

	/**
	 * Indexes of nodes.
	 */
	private int[] nodesIndexes;
	
	/**
	 * Incidence matrix.
	 */
	private int[][] incidenceMatrix;
	
	/**
	 * Number of nodes.
	 */
	private int numberOfNodes = 0;
	
	public ArrayNetwork(String networkName) {
		super(networkName);
		this.nodesIndexes = new int[ArrayNetwork.INITIAL_SIZE];
		
		this.incidenceMatrix = new int[ArrayNetwork.INITIAL_SIZE][ArrayNetwork.INITIAL_SIZE];
		for (int i = 0; i < this.incidenceMatrix.length; i++) {
			Arrays.fill(this.incidenceMatrix[i], 0);
		}
	}
	
	@Override
	public boolean addNode(int nodeId) {
		if (this.containsNode(nodeId)) {
			return false;
		}
		
		if (this.numberOfNodes == this.nodesIndexes.length) {
			this.makeTwoTimesLargerNodesIndexesAndIncidenceMatrix();
		}
		
		this.nodesIndexes[this.numberOfNodes] = nodeId;
		this.numberOfNodes++;
		return true;
	}
	
	@Override
	public boolean addEdge(int nodeId1, int nodeId2) {
		if (nodeId1 == nodeId2) {
			return false;
		}
		
		int indexOfNode1 = this.getIndexOfNode(nodeId1);
		int indexOfNode2 = this.getIndexOfNode(nodeId2);
		if (indexOfNode1 != -1 && indexOfNode2 != -1) {
			if (this.incidenceMatrix[indexOfNode1][indexOfNode2] == 1 
					&& this.incidenceMatrix[indexOfNode2][indexOfNode1] == 1) {
				return false;
			} else {
				this.incidenceMatrix[indexOfNode1][indexOfNode2] = 1;
				this.incidenceMatrix[indexOfNode2][indexOfNode1] = 1;
				return true;
			}
		}
		return false;
	}

	@Override
	public int getNodeToConnectDegreeDriven() {
		return this.getNodeToConnectDegreeDriven(this);
	}

	@Override
	public int getNodeToConnectDegreeDriven(int[] nodesIds) {
		return this.getNodeToConnectDegreeDriven(nodesIds, this);
	}
	
	@Override
	public int getNodeToConnectDegreeDrivenNewWay(int[] availableNodes) {
		return this.getNodeToConnectDegreeDrivenNewWay(availableNodes, this);
	}

	@Override
	public int getNodeToConnectClusterDriven() {
		return this.getNodeToConnectClusterDriven(this);
	}

	@Override
	public int getNodeToConnectClusterDriven(int[] nodesIds) {
		return this.getNodeToConnectClusterDriven(nodesIds, this);
	}
	
	@Override
	public int getNodeToConnectRandomDriven() {
		return this.getNodeToConnectRandomDriven(this);
	}

	@Override
	public int getNodeToConnectRandomDriven(int[] nodesIds) {
		return this.getNodeToConnectRandomDriven(nodesIds, this);
	}

	@Override
	public int getNumberOfExistingEdgesBetweenNodes(int[] nodesIds) {
		int[] indexesOfNodesIds = this.getIndexesOfNodes(nodesIds);
		
		int numberOfEdges = 0;
		for (int indexOfNode1 : indexesOfNodesIds) {
			for (int indexOfNode2 : indexesOfNodesIds) {
				if (this.incidenceMatrix[indexOfNode1][indexOfNode2] == 1) {
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
			
			int indexOfNode = this.getIndexOfNode(nodeId);
			for (int i = 0; i < this.incidenceMatrix[indexOfNode].length; i++) {
				if (this.incidenceMatrix[indexOfNode][i] == 1) {
					result[pointer] = this.getNodeIdAtIndex(i);
					pointer++;
				}
			}
		}
		return result;
	}

	@Override
	public int getAdjacentNodesCount(int nodeId) {
		int result = 0;
		int indexOfNode = this.getIndexOfNode(nodeId);
		for (int i = 0; i < this.incidenceMatrix[indexOfNode].length; i++) {
			if (this.incidenceMatrix[indexOfNode][i] == 1) {
				result++;
			}
		}
		return result;
	}

	@Override
	public boolean isEdgeBetweenNodes(int nodeId1, int nodeId2) {
		int indexOfNode1 = this.getIndexOfNode(nodeId1);
		int indexOfNode2 = this.getIndexOfNode(nodeId2);
		
		if (indexOfNode1 == -1 || indexOfNode2 == -1) {
			return false;
		} else {
			return this.incidenceMatrix[indexOfNode1][indexOfNode2] == 1 
					&& this.incidenceMatrix[indexOfNode2][indexOfNode1] == 1;
		}
	}
	
	@Override
	public int getNumberOfNodes() {
		return this.numberOfNodes;
	}

	@Override
	public boolean containsNode(int nodeId) {
		for (int i = 0; i < this.numberOfNodes; i++) {
			if (this.nodesIndexes[i] == nodeId) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int[] getNodesIds() {
		int[] result = new int[this.numberOfNodes];
		for (int i = 0; i < this.numberOfNodes; i++) {
			result[i] = this.nodesIndexes[i];
		}
		return result;
	}
	
	@Override
	public int getNumberOfEdges() {
		int result = 0;
		for (int i = 0; i < this.numberOfNodes; i++) {
			for (int j = 0; j < this.numberOfNodes; j++) {
				if (this.incidenceMatrix[i][j] == 1) {
					result++;
				}
			}
		}
		return result / 2;
	}
	
	@Override
	public int getNumberOfEdges(int[] nodesIds) {
		int result = 0;
		for (int i : nodesIds) {
			for (int j : nodesIds) {
				if (this.incidenceMatrix[i][j] == 1) {
					result ++;
				}
			}
		}
		return result / 2;
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
	
	/**
	 * Makes nodesIndexes array and incidenceMatrix two times larger.
	 */
	private void makeTwoTimesLargerNodesIndexesAndIncidenceMatrix() {
		int oldSize = this.nodesIndexes.length;
		int newSize = oldSize * 2;
		
		int[] newNodesIndexes = new int[newSize];
		for (int i = 0; i < this.getNumberOfNodes(); i++) {
			newNodesIndexes[i] = this.nodesIndexes[i];
		}
		this.nodesIndexes = newNodesIndexes;
		
		int[][] newIncidenceMatrix = new int[newSize][newSize];
		for (int i = 0; i < newIncidenceMatrix.length; i++) {
			Arrays.fill(newIncidenceMatrix[i], 0);
			if (i < this.getNumberOfNodes()) {
				for (int j = 0; j < this.getNumberOfNodes(); j++) {
					newIncidenceMatrix[i][j] = this.incidenceMatrix[i][j];
				}
			}
		}
		this.incidenceMatrix = newIncidenceMatrix;
	}
	
	/**
	 * Returns index of node in nodesIndexes array.
	 * 
	 * @param nodeId Id of node
	 * @return Index of node if contains, -1 otherwise
	 */
	private int getIndexOfNode(int nodeId) {
		for (int i = 0; i < this.numberOfNodes; i++) {
			if (this.nodesIndexes[i] == nodeId) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Returns array of nodes indexes in nodesIndexes array.
	 * 
	 * @param nodesIds Ids of nodes
	 * @return Array of nodes indexes
	 */
	private int[] getIndexesOfNodes(int[] nodesIds) {
		int[] result = new int[nodesIds.length];
		for (int i = 0; i < nodesIds.length; i++) {
			result[i] = this.getIndexOfNode(nodesIds[i]);
		}
		return result;
	}
	
	/**
	 * Return id of node from nodesIndexes. 
	 * 
	 * @param indexOfNode Index of node
	 * @return Id of node
	 */
	private int getNodeIdAtIndex(int indexOfNode) {
		return this.nodesIndexes[indexOfNode];
	}

	@Override
	public List<int[]> getPairsOfNeighboringNodes() {
		List<int[]> result = new ArrayList<int[]>();
		List<Integer> alreadyAddedNodesIds = new ArrayList<Integer>();
		int[] nodeIds = this.getNodesIds();
		for (int nodeId1 : nodeIds) {
			for (int nodeId2 : nodeIds) {
				if (this.isEdgeBetweenNodes(nodeId1, nodeId2) && !alreadyAddedNodesIds.contains(nodeId2)) {
					result.add(new int[]{nodeId1, nodeId2});
				}
			}
			alreadyAddedNodesIds.add(nodeId1);
		}
		return result;
	}
}