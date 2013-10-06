package sk.sochuliak.barabasi.network;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

public class ObjectedNetworkTest {

	private Network network;
	
	@Before
	public void setUp() {
		this.network = new ObjectedNetwork();
		for (int i = 0; i < 3; i++) {
			this.network.addNode(i);
		}
		this.network.addEdge(0, 1);
	}
	
	@Test
	public void testAddNodeSuccess() {
		boolean nodeAdded = this.network.addNode(3);
		int expectedNumberOfNodes = 4;
		assertTrue(nodeAdded);
		assertEquals(expectedNumberOfNodes, this.network.getNumberOfNodes());
	}
	
	@Test
	public void testAddNodeFail() {
		boolean nodeAdded = this.network.addNode(2);
		int expectedNumberOfNodes = 3;
		assertFalse(nodeAdded);
		assertEquals(expectedNumberOfNodes, this.network.getNumberOfNodes());
	}
	
	@Test
	public void testAddEdgeSuccess() {
		boolean edgeAdded = this.network.addEdge(1, 2);
		assertTrue(edgeAdded);
		assertTrue(this.network.isEdgeBetweenNodes(1, 2));
	}
	
	@Test
	public void testAddEdgeFail() {
		boolean edgeAdded = this.network.addEdge(0, 1);
		assertFalse(edgeAdded);
		assertTrue(this.network.isEdgeBetweenNodes(0, 1));
		
		boolean edgeAdded2 = this.network.addEdge(1, 1);
		assertFalse(edgeAdded2);
	}
	
	@Test
	public void testGetNumberOfExistingEdgesBetweenNodes() {
		this.network.addNode(3);
		this.network.addNode(4);
		this.network.addEdge(2, 4);
		this.network.addEdge(1, 4);
		int expectedNumberOfExistingEdges = 2;
		int actualNumberOfExistingEdges = this.network.getNumberOfExistingEdgesBetweenNodes(new int[]{1, 2, 4});
		assertEquals(expectedNumberOfExistingEdges, actualNumberOfExistingEdges);
	}
	
	@Test
	public void testGetNumberOfAllPossibleEdgesBetweenNodes() {
		int expectedNumberOfAllEdges = 0;
		int actualNumberOfAllEdges = this.network.getNumberOfAllPossibleEdgesBetweenNodes(new int[]{1});
		assertEquals(expectedNumberOfAllEdges, actualNumberOfAllEdges);
		
		expectedNumberOfAllEdges = 1;
		actualNumberOfAllEdges = this.network.getNumberOfAllPossibleEdgesBetweenNodes(new int[]{1, 2});
		assertEquals(expectedNumberOfAllEdges, actualNumberOfAllEdges);
		
		expectedNumberOfAllEdges = 3;
		actualNumberOfAllEdges = this.network.getNumberOfAllPossibleEdgesBetweenNodes(new int[]{1, 2, 3});
		assertEquals(expectedNumberOfAllEdges, actualNumberOfAllEdges);
		
		expectedNumberOfAllEdges = 6;
		actualNumberOfAllEdges = this.network.getNumberOfAllPossibleEdgesBetweenNodes(new int[]{1, 2, 3, 4});
		assertEquals(expectedNumberOfAllEdges, actualNumberOfAllEdges);
	}
	
	@Test
	public void testGetAdjacentNodesIds() {
		this.network.addNode(3);
		this.network.addNode(4);
		this.network.addEdge(2, 4);
		this.network.addEdge(1, 4);
		int[] expectedAdjacentNodesIds = new int[]{1, 2};
		int[] actualAdjacentNodesIds = this.network.getAdjacentNodesIds(4);
		
		SortedSet<Integer> expectedAdjacentNodesIdsAsSet = this.getArrayAsSet(expectedAdjacentNodesIds);
		SortedSet<Integer> actualAdjacentNodesIdsAsSet = this.getArrayAsSet(actualAdjacentNodesIds);
		
		assertEquals(expectedAdjacentNodesIdsAsSet, actualAdjacentNodesIdsAsSet);
	}

	@Test
	public void testGetAdjacentNodesCount() {
		this.network.addNode(3);
		this.network.addNode(4);
		this.network.addEdge(2, 4);
		this.network.addEdge(1, 4);
		int expectedNodesCount = 2;
		int actualNodesCount = this.network.getAdjacentNodesCount(4);
		assertEquals(expectedNodesCount, actualNodesCount);
	}
	
	@Test
	public void testIsEdgeBetweenNodes() {
		this.network.addNode(3);
		this.network.addNode(4);
		this.network.addEdge(2, 4);
		this.network.addEdge(1, 4);
		assertTrue(this.network.isEdgeBetweenNodes(2, 4));
		assertFalse(this.network.isEdgeBetweenNodes(0, 4));
	}
	
	@Test
	public void testGetNumberOfNodes() {
		this.network.addNode(3);
		this.network.addNode(4);
		this.network.addEdge(2, 4);
		this.network.addEdge(1, 4);
		int expectedNumberOfNodes = 5;
		int actualNumberOfNodes = this.network.getNumberOfNodes();
		assertEquals(expectedNumberOfNodes, actualNumberOfNodes);
	}
	
	@Test
	public void testContainsNode() {
		this.network.addNode(3);
		this.network.addNode(4);
		this.network.addEdge(2, 4);
		this.network.addEdge(1, 4);
		assertTrue(this.network.containsNode(4));
		assertFalse(this.network.containsNode(5));
	}
	
	@Test
	public void testGetNodesIds() {
		this.network.addNode(3);
		this.network.addNode(4);
		this.network.addEdge(2, 4);
		this.network.addEdge(1, 4);
		
		SortedSet<Integer> expectedIds = this.getArrayAsSet(new int[]{0, 1, 2, 3, 4});
		SortedSet<Integer> actualIds = this.getArrayAsSet(this.network.getNodesIds());
		assertEquals(expectedIds, actualIds);
	}
	
	@Test
	public void testGetNumberOfEdges() {
		this.network.addNode(3);
		this.network.addNode(4);
		this.network.addEdge(2, 4);
		this.network.addEdge(1, 4);
		
		int expectedNumberOfEdges = 3;
		int actualNumberOfEdges = this.network.getNumberOfEdges();
		assertEquals(expectedNumberOfEdges, actualNumberOfEdges);
	}
	
	@Test
	public void testGetAverageNodeDegree() {
		this.network.addNode(3);
		this.network.addNode(4);
		this.network.addEdge(2, 4);
		this.network.addEdge(1, 4);
		
		Double expectedAverageNodeDegree = 1.2;
		Double actualAverageNodeDegree = this.network.getAverageNodeDegree();
		assertEquals(expectedAverageNodeDegree, actualAverageNodeDegree);
	}
	
	@Test
	public void testGetAverageClusterCoefficient() {
		this.network.addNode(3);
		this.network.addNode(4);
		this.network.addEdge(2, 4);
		this.network.addEdge(1, 4);
		this.network.addEdge(0, 4);
		
		Double expectedAverageClusterCoefficient = 7d/15d;
		Double actualAverageClusterCoefficient = this.network.getAverageClusterRatio();
		assertEquals(expectedAverageClusterCoefficient, actualAverageClusterCoefficient);
	}
	
	private SortedSet<Integer> getArrayAsSet(int[] nodesIds) {
		SortedSet<Integer> result = new TreeSet<Integer>();
		for (int nodeId : nodesIds) {
			result.add(nodeId);
		}
		return result;
	}

}
