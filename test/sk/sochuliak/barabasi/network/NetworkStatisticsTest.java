package sk.sochuliak.barabasi.network;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class NetworkStatisticsTest {
	
	Network network = null;
	
	@Before
	public void setUp() {
		this.network = TestUtils.buildSimpleNetwork(new MapNetwork(this.getClass().getName()));
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
}
