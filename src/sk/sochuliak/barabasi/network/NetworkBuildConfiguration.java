package sk.sochuliak.barabasi.network;

public class NetworkBuildConfiguration {
	
	public static final int DEGREE_DRIVEN = 0;
	public static final int CLUSTER_DRIVEN = 1;
	public static final int RANDOM_DRIVEN = 2;
	
	public static NetworkBuildConfiguration getInstance() {
		return new NetworkBuildConfiguration();
	}
	
	/**
	 * Network
	 */
	private Network network = null;
	
	/**
	 * Number of nodes to be added to network.
	 */
	private int nodesCount = 500;
	
	/**
	 * Number of edges that new node uses for connecting to network.
	 */
	private int edgesCount = 2;
	
	/**
	 * Method of connecting new nodes to network.
	 */
	private int methodDriven = NetworkBuildConfiguration.DEGREE_DRIVEN;

	public Network getNetwork() {
		return network;
	}

	public NetworkBuildConfiguration setNetwork(Network network) {
		this.network = network;
		return this;
	}

	public int getNodesCount() {
		return nodesCount;
	}

	public NetworkBuildConfiguration setNodesCount(int nodesCount) {
		this.nodesCount = nodesCount;
		return this;
	}

	public int getEdgesCount() {
		return edgesCount;
	}

	public NetworkBuildConfiguration setEdgesCount(int edgesCount) {
		this.edgesCount = edgesCount;
		return this;
	}

	public int getMethodDriven() {
		return methodDriven;
	}

	public NetworkBuildConfiguration setMethodDriven(int methodDriven) {
		this.methodDriven = methodDriven;
		return this;
	}
	
}
