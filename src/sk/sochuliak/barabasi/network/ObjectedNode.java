package sk.sochuliak.barabasi.network;

import java.util.ArrayList;
import java.util.List;

public class ObjectedNode {
	
	/**
	 * Id of node
	 */
	private int id;
	
	/**
	 * List of adjacent nodes.
	 */
	private List<ObjectedNode> adjacentNodes;

	/**
	 * Constructor.
	 * 
	 * @param id Id of node
	 */
	public ObjectedNode(int id) {
		this.id = id;
		this.adjacentNodes = new ArrayList<ObjectedNode>();
	}
	
	/**
	 * Adds edge to another node.
	 * 
	 * @param node Node
	 * @return true if added, false otherwise
	 */
	public boolean addEdge(ObjectedNode node) {
		if (this.adjacentNodes.contains(node)) {
			return false;
		}
		this.adjacentNodes.add(node);
		return true;
	}
	
	/**
	 * Checks if edge to another node exists.
	 * 
	 * @param node Node
	 * @return true if edge exists, false otherwise
	 */
	public boolean hasEdgeTo(ObjectedNode node) {
		return this.adjacentNodes.contains(node);
	}
	
	/**
	 * Return number of adjacent nodes.
	 * 
	 * @return Number of adjacent nodes.
	 */
	public int getAdjacentNodesCount() {
		return this.adjacentNodes.size();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ObjectedNode other = (ObjectedNode) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public int getId() {
		return id;
	}

	public List<ObjectedNode> getAdjacentNodes() {
		return adjacentNodes;
	}
	
}
