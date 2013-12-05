package sk.sochuliak.barabasi.network;

import java.util.ArrayList;
import java.util.List;

public class NetworkBuildConfiguration2 {
	
	public static final int DEGREE_DRIVEN = 0;
	public static final int CLUSTER_DRIVEN = 1;
	public static final int RANDOM_DRIVEN = 2;
	
	private Network network = new MapNetwork();
	
	private int numberOfNodes = 0;
	
	private int firstEdgeConnecting = 0;
	
	private List<EdgeConnectingMethodRowConfig> edgeConnectingMethodRowConfigs = null;
	
	public static NetworkBuildConfiguration2 getInstance() {
		return new NetworkBuildConfiguration2();
	}
	
	public static NetworkBuildConfiguration2 createDefaultConfig(int method, int numberOfNodes, int numberOfEdges) {
		NetworkBuildConfiguration2 config = new NetworkBuildConfiguration2();
		config.setNumberOfNodes(numberOfNodes);
		config.setFirstEdgeConnecting(method);
		
		EdgeConnectingMethodRowConfig rowConfig = new EdgeConnectingMethodRowConfig();
		rowConfig.setConnectingMethod(EdgeConnectingMethodRowConfig.RANDOM_DRIVEN);
		rowConfig.setNumberOfEdges(numberOfEdges-1);
		
		List<EdgeConnectingMethodRowConfig> rowsConfig = new ArrayList<EdgeConnectingMethodRowConfig>();
		rowsConfig.add(rowConfig);
		
		config.setEdgeConnectingMethodRowConfigs(rowsConfig);
		return config;
	}

	public int getNumberOfNodes() {
		return numberOfNodes;
	}

	public NetworkBuildConfiguration2 setNumberOfNodes(int numberOfNodes) {
		this.numberOfNodes = numberOfNodes;
		return this;
	}

	public int getFirstEdgeConnecting() {
		return firstEdgeConnecting;
	}

	public NetworkBuildConfiguration2 setFirstEdgeConnecting(int firstEdgeConnecting) {
		this.firstEdgeConnecting = firstEdgeConnecting;
		return this;
	}

	public List<EdgeConnectingMethodRowConfig> getEdgeConnectingMethodRowConfigs() {
		return edgeConnectingMethodRowConfigs;
	}

	public NetworkBuildConfiguration2 setEdgeConnectingMethodRowConfigs(
			List<EdgeConnectingMethodRowConfig> edgeConnectingMethodRowConfigs) {
		this.edgeConnectingMethodRowConfigs = edgeConnectingMethodRowConfigs;
		return this;
	}

	public Network getNetwork() {
		return network;
	}

	public NetworkBuildConfiguration2 setNetwork(Network network) {
		this.network = network;
		return this;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.getClass().getName()).append("\n");
		sb.append("\t").append("Number of nodes: ").append(this.getNumberOfNodes()).append("\n");
		sb.append("\t").append("First edge connecting: ").append(this.getFirstEdgeConnecting()).append("\n");
		sb.append("\t").append("Other edges connecting").append("\n");
		for (EdgeConnectingMethodRowConfig rowConfig : this.getEdgeConnectingMethodRowConfigs()) {
			sb.append("\t\t").append(rowConfig).append("\n");
		}
		return sb.toString();
	}
}
