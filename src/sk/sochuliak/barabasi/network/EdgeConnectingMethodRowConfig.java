package sk.sochuliak.barabasi.network;

public class EdgeConnectingMethodRowConfig {
	
	public static final int DRIVEN_DEGREE = 0;
	public static final int DRIVEN_CLUSTER = 1;
	public static final int DRIVEN_RANDOM = 2;
	
	public static final int RANGE_NEIGHBOR = 0;
	public static final int RANGE_ALL = 1;

	private int numberOfEdges = 0;
	private int connectingMethod = 0;
	private int range = 0;
	
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
	
	public int getRange() {
		return range;
	}

	public EdgeConnectingMethodRowConfig setRange(int range) {
		this.range = range;
		return this;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer().append("[");
		sb.append("Number of edges: ").append(this.numberOfEdges).append(", ");
		sb.append("Connecting method: ").append(this.connectingMethod).append(", ");
		sb.append("Range: ").append(this.range);
		sb.append("]");
		return sb.toString();
	}
}
