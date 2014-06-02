package sk.sochuliak.barabasi.network;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import sk.sochuliak.giraphe.network.MapNetwork;
import sk.sochuliak.giraphe.network.Network;
import sk.sochuliak.giraphe.network.NetworkStatistics;

public class NetworkStatisticsTest {
	
	Network network = null;
	
	@Before
	public void setUp() {
		this.network = TestUtils.buildSimpleNetwork(new MapNetwork(this.getClass().getName()));
	}
	
	private Network buildNetwork() {
		return TestUtils.buildComplexNetwork(new MapNetwork(this.getClass().getName()));
	}

	@Test
	public void testGetAverageClusterCoefficient() {
		this.network.addNode(3);
		this.network.addNode(4);
		this.network.addEdge(2, 4);
		this.network.addEdge(1, 4);
		this.network.addEdge(0, 4);
		
		Double expectedAverageClusterCoefficient = 7d/15d;
		Double actualAverageClusterCoefficient = NetworkStatistics.getAverageClusteRatios(this.network);
		assertEquals(expectedAverageClusterCoefficient, actualAverageClusterCoefficient);
	}
	
	@Test
	public void testGetAverageNodeDegree() {
		this.network.addNode(3);
		this.network.addNode(4);
		this.network.addEdge(2, 4);
		this.network.addEdge(1, 4);
		
		Double expectedAverageNodeDegree = 1.2;
		Double actualAverageNodeDegree = NetworkStatistics.getAverageNodeDegree(this.network);
		assertEquals(expectedAverageNodeDegree, actualAverageNodeDegree);
	}
	
	@Test
	public void testGetDistanceBetweenNodes() {
		Network network = this.buildNetwork();
		
		int expectedDistance1 = 1;
		int actualDistance1 = NetworkStatistics.getDistanceBetweenNodes(network, 6, 2);
		assertEquals(expectedDistance1, actualDistance1);
		
		int expectedDistance2 = 5;
		int actualDistance2 = NetworkStatistics.getDistanceBetweenNodes(network, 7, 19);
		assertEquals(expectedDistance2, actualDistance2);
		
		int expectedDistance3 = 3;
		int actualDistance3 = NetworkStatistics.getDistanceBetweenNodes(network, 7, 24);
		assertEquals(expectedDistance3, actualDistance3);
		
		int expectedDistance4 = 0;
		int actualDistance4 = NetworkStatistics.getDistanceBetweenNodes(network, 12, 12);
		assertEquals(expectedDistance4, actualDistance4);
	}
	
	@Test
	public void testGetNumberOfNeighboringNodes() {
		Network network = this.buildNetwork();
		
		int expectedNumber = 31;
		int actualNumber = NetworkStatistics.getNumberOfNeighboringNodes(network);
		assertEquals(expectedNumber, actualNumber);
	}
}
