package sk.sochuliak.giraphe.gui.analysisframes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetworkConfiguration {

	private String title = "Network title";
	
	private String xAxisLabel = "X axis label";
	
	private String yAxisLabel = "Y axis label";
	
	private Map<String, List<double[]>> data = new HashMap<String, List<double[]>>();
	
	public static NetworkConfiguration getInstance() {
		return new NetworkConfiguration();
	}
	
	public static NetworkConfiguration getInstance(
			String title,
			String xAxisLabel,
			String yAxisLabel,
			Map<String, List<double[]>> data) {
		return NetworkConfiguration.getInstance()
				.setTitle(title)
				.setxAxisLabel(xAxisLabel)
				.setyAxisLabel(yAxisLabel)
				.setData(data);
	}

	public String getTitle() {
		return this.title;
	}

	public NetworkConfiguration setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getxAxisLabel() {
		return this.xAxisLabel;
	}

	public NetworkConfiguration setxAxisLabel(String xAxisLabel) {
		this.xAxisLabel = xAxisLabel;
		return this;
	}

	public String getyAxisLabel() {
		return this.yAxisLabel;
	}

	public NetworkConfiguration setyAxisLabel(String yAxisLabel) {
		this.yAxisLabel = yAxisLabel;
		return this;
	}

	public Map<String, List<double[]>> getData() {
		return this.data;
	}

	public NetworkConfiguration setData(Map<String, List<double[]>> data) {
		this.data = data;
		return this;
	}
}
