package sk.sochuliak.barabasi.network;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

public class ObjectedNetworkTest {

	private Network network;
	
	private int[] edgesFrom = new int[]{0, 6,  12, 7, 2, 1, 12, 18, 5,  12, 16, 6,  10, 0,  14, 14, 22, 19, 23, 12, 17, 3, 4,  8, 8,  12, 2,  5,  7,  7,  2};
	private int[] edgesTo = new int[]{  6, 12, 7,  3, 6, 5, 18, 22, 22, 16, 20, 16, 16, 15, 18, 24, 24, 23, 24, 17, 21, 4, 14, 9, 13, 13, 22, 11, 13, 18, 12};
	
	@Before
	public void setUp() {
		this.network = new ObjectedNetwork();
		for (int i = 0; i < 3; i++) {
			this.network.addNode(i);
		}
		this.network.addEdge(0, 1);
	}
	
	private Network buildNetwork() {
		Network network = new ObjectedNetwork();
		for (int i = 0; i <= 24; i++) {
			network.addNode(i);
		}
		
		for (int i = 0; i < edgesFrom.length; i++) {
			network.addEdge(edgesFrom[i], edgesTo[i]);
		}
		return network;
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
		
		boolean edgeAdded3 = this.network.addEdge(2, 3);
		assertFalse(edgeAdded3);
		
		boolean edgeAdded4 = this.network.addEdge(3, 2);
		assertFalse(edgeAdded4);
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
		this.network = this.buildNetwork();
		int[] expectedAdjacentNodesIds = new int[]{0, 2, 12, 16};
		int[] actualAdjacentNodesIds = this.network.getAdjacentNodesIds(6);
		
		SortedSet<Integer> expectedAdjacentNodesIdsAsSet = this.getArrayAsSet(expectedAdjacentNodesIds);
		SortedSet<Integer> actualAdjacentNodesIdsAsSet = this.getArrayAsSet(actualAdjacentNodesIds);
		
		assertEquals(expectedAdjacentNodesIdsAsSet, actualAdjacentNodesIdsAsSet);
	}
	
	@Test
	public void testGetAdjacentNodesCount() {
		this.network = this.buildNetwork();
		int expectedNodesCount = 3;
		int actualNodesCount = this.network.getAdjacentNodesCount(14);
		assertEquals(expectedNodesCount, actualNodesCount);
	}
	
	@Test
	public void testIsEdgeBetweenNodes() {
		this.network = this.buildNetwork();
		assertTrue(this.network.isEdgeBetweenNodes(14, 18));
		assertFalse(this.network.isEdgeBetweenNodes(10, 17));
	}
	
	@Test
	public void testGetNumberOfNodes() {
		this.network = this.buildNetwork();
		int expectedNumberOfNodes = 25;
		int actualNumberOfNodes = this.network.getNumberOfNodes();
		assertEquals(expectedNumberOfNodes, actualNumberOfNodes);
	}
	
	@Test
	public void testContainsNode() {
		this.network = this.buildNetwork();
		assertTrue(this.network.containsNode(15));
		assertFalse(this.network.containsNode(31));
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
		this.network = this.buildNetwork();
		
		int expectedNumberOfEdges = 31;
		int actualNumberOfEdges = this.network.getNumberOfEdges();
		assertEquals(expectedNumberOfEdges, actualNumberOfEdges);
	}
	
	@Test
	public void testGetNumberOfEdgesWithParameter() {
		this.network = this.buildNetwork();
		
		int expectedNumberOfEdges = 31;
		int actualNumberOfEdges = this.network.getNumberOfEdges(this.network.getNodesIds());
		assertEquals(expectedNumberOfEdges, actualNumberOfEdges);
	}
	
	@Test
	public void testGetClusterRatio() {
		this.network = this.buildNetwork();
		
		Double expectedClusterRatio = 4d/21d;
		Double actualClusterRatio = this.network.getClusterRatio(12);
		assertEquals(expectedClusterRatio, actualClusterRatio);
	}
	
	@Test
	public void testGetClusterRatioFail() {
		this.network = this.buildNetwork();
		
		Double expectedClusterRatio = 0d;
		Double actualClusterRatio = this.network.getClusterRatio(21);
		assertEquals(expectedClusterRatio, actualClusterRatio);
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
	
	@Test
	public void testGetPairsOfNeighboringNodes() {
		this.network = this.buildNetwork();
		
		List<int[]> expectedPairs = new ArrayList<int[]>();
		for (int i = 0; i < this.edgesFrom.length; i++) {
			expectedPairs.add(new int[]{this.edgesFrom[i], this.edgesTo[i]});
		}
		
		List<int[]> actualPairs = this.network.getPairsOfNeighboringNodes();
		assertTrue(this.arePairsOfNeighboringNodesEquals(expectedPairs, actualPairs));
		
	}
	
	private SortedSet<Integer> getArrayAsSet(int[] nodesIds) {
		SortedSet<Integer> result = new TreeSet<Integer>();
		for (int nodeId : nodesIds) {
			result.add(nodeId);
		}
		return result;
	}
	
	private boolean arePairsOfNeighboringNodesEquals(List<int[]> expectedPairs, List<int[]> actualPairs) {
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
}
