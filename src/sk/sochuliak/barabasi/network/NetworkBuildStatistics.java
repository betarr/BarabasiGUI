package sk.sochuliak.barabasi.network;

import java.util.HashMap;
import java.util.Map;

public class NetworkBuildStatistics {
	
	/**
	 * Build statistics will be computed after adding this number of nodes.
	 */
	public static final int BUILD_STATISTICS_ITERATION = 100;

	/**
	 * Map of average node degree where key is number of nodes.
	 */
	private Map<Integer, Double> averageNodeDegree;
	
	/**
	 * Map of average cluster coefficient where key is number of nodes.
	 */
	private Map<Integer, Double> averageClusterCoefficient;
	
	private long buildStartTime;
	
	private long buildEndTime; 
	
	public NetworkBuildStatistics() {
		this.averageNodeDegree = new HashMap<Integer, Double>();
		this.averageClusterCoefficient = new HashMap<Integer, Double>();
	}
	
	public NetworkBuildStatistics addAverageNodeDegreeValue(int numberOfNodes, double averageNodeDegreeValue) {
		this.averageNodeDegree.put(numberOfNodes, averageNodeDegreeValue);
		return this;
	}
	
	public NetworkBuildStatistics addAverageClusterRatioValue(int numberOfNodes, double averageClusterCoefficient) {
		this.averageClusterCoefficient.put(numberOfNodes, averageClusterCoefficient);
		return this;
	}

	public Map<Integer, Double> getAverageNodeDegree() {
		return averageNodeDegree;
	}
	
	public Map<Integer, Double> getAverageClusterRatio() {
		return averageClusterCoefficient;
	}

	public long getBuildStartTime() {
		return buildStartTime;
	}

	public NetworkBuildStatistics setBuildStartTime(long buildStartTime) {
		this.buildStartTime = buildStartTime;
		return this;
	}

	public long getBuildEndTime() {
		return buildEndTime;
	}

	public NetworkBuildStatistics setBuildEndTime(long buildEndTime) {
		this.buildEndTime = buildEndTime;
		return this;
	}
	
	public long getBuildTotalTime() {
		return this.buildEndTime - this.buildStartTime;
	}
	
	
}
