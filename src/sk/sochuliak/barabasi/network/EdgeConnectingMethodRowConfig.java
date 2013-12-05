package sk.sochuliak.barabasi.network;

public class EdgeConnectingMethodRowConfig {
	
	public static final int DEGREE_DRIVEN = 0;
	public static final int CLUSTER_DRIVEN = 1;
	public static final int RANDOM_DRIVEN = 2;

	private int numberOfEdges = 0;
	private int connectingMethod = 0;
	
	public EdgeConnectingMethodRowConfig() {
	}
	
	public EdgeConnectingMethodRowConfig(int numberOfEdges, int connectingMethod) {
		this.numberOfEdges = numberOfEdges;
		this.connectingMethod = connectingMethod;
	}

	public int getNumberOfEdges() {
		return numberOfEdges;
	}

	public EdgeConnectingMethodRowConfig setNumberOfEdges(int numberOfEdges) {
		this.numberOfEdges = numberOfEdges;
		return this;
	}

	public int getConnectingMethod() {
		return connectingMethod;
	}

	public EdgeConnectingMethodRowConfig setConnectingMethod(int connectingMethod) {
		this.connectingMethod = connectingMethod;
		return this;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer().append("[");
		sb.append("Number of edges: ").append(this.numberOfEdges).append(" ");
		sb.append("Connecting method: ").append(this.connectingMethod);
		sb.append("]");
		return sb.toString();
	}
}
