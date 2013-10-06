package sk.sochuliak.barabasi.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NetworkUtilsTest {

	@Test
	public void testIsNodeIdInNodesIdsArraySuccess() {
		int[] nodesIdsArray = new int[]{1, 2, 3, 4, 5, 6, 7};
		int nodeId = 4;
		assertTrue(NetworkUtils.isNodeIdInNodesIdsArray(nodeId, nodesIdsArray));
	}
	
	@Test
	public void testIsNodeIdInNodesIdsArrayFail() {
		int[] nodesIdsArray = new int[]{1, 2, 3, 4, 5, 6, 7};
		int nodeId = 9;
		assertFalse(NetworkUtils.isNodeIdInNodesIdsArray(nodeId, nodesIdsArray));
	}
	
	@Test
	public void testGetIndexOfNodeIdInNodesIdsArray() {
		int[] nodesIdsArray = new int[]{1, 2, 3, 4, 5, 6, 7};
		int searchedIdSuccess = 5;
		int searchedIdFail = 19;
		
		assertEquals(4, NetworkUtils.getIndexOfNodeIdInNodesIdsArray(searchedIdSuccess, nodesIdsArray));
		assertEquals(-1, NetworkUtils.getIndexOfNodeIdInNodesIdsArray(searchedIdFail, nodesIdsArray));
	}
}
