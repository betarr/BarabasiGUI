package sk.sochuliak.barabasi.analysisdialogs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphConfiguration {

	private String title = "Graph title";
	
	private String xAxisLabel = "X axis label";
	
	private String yAxisLabel = "Y axis label";
	
	private Map<String, List<double[]>> data = new HashMap<String, List<double[]>>();
	
	public static GraphConfiguration getInstance() {
		return new GraphConfiguration();
	}
	
	public static GraphConfiguration getInstance(
			String title,
			String xAxisLabel,
			String yAxisLabel,
			Map<String, List<double[]>> data) {
		return GraphConfiguration.getInstance()
				.setTitle(title)
				.setxAxisLabel(xAxisLabel)
				.setyAxisLabel(yAxisLabel)
				.setData(data);
	}

	public String getTitle() {
		return this.title;
	}

	public GraphConfiguration setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getxAxisLabel() {
		return this.xAxisLabel;
	}

	public GraphConfiguration setxAxisLabel(String xAxisLabel) {
		this.xAxisLabel = xAxisLabel;
		return this;
	}

	public String getyAxisLabel() {
		return this.yAxisLabel;
	}

	public GraphConfiguration setyAxisLabel(String yAxisLabel) {
		this.yAxisLabel = yAxisLabel;
		return this;
	}

	public Map<String, List<double[]>> getData() {
		return this.data;
	}

	public GraphConfiguration setData(Map<String, List<double[]>> data) {
		this.data = data;
		return this;
	}
}
