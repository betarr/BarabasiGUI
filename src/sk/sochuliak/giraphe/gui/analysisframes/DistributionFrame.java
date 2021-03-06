package sk.sochuliak.giraphe.gui.analysisframes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.ShapeUtilities;

import sk.sochuliak.giraphe.gui.MainGuiConfiguration;

public abstract class DistributionFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private Component owner = null;
	
	private String networkName = null;
	
	private DistributionInfoPanel infoPanel = null;
	
	private JFreeChart chart = null;
	
	private boolean logScaleUsed = false;
	
	public DistributionFrame(String title, Component owner, String networkName, NetworkConfiguration config, boolean logScaleUsed) {
		this.owner = owner;
		this.networkName = networkName;
		this.logScaleUsed = logScaleUsed;
		
		this.setTitle(networkName + " - " + title);
		this.setSize(MainGuiConfiguration.ANALYSIS_DISTRIBUTION_SIZE);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(this.owner);
		this.setLayout(new BorderLayout());
		
		this.add(this.buildChartPanel(config), BorderLayout.CENTER);
		
		this.infoPanel = this.getInstanceOfDistributionInfoPanel(networkName, logScaleUsed);
		this.add(this.infoPanel, BorderLayout.EAST);
	}
	
	private JPanel buildChartPanel(NetworkConfiguration config) {
		this.chart = this.createChart(config);
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(MainGuiConfiguration.ANALYSIS_DISTRIBUTION_SIZE);
		
		chartPanel.addChartMouseListener(new ChartMouseListener() {

			@Override
			public void chartMouseClicked(ChartMouseEvent e) {
				ChartEntity entity = e.getEntity();
				if (entity instanceof XYItemEntity) {
					XYItemEntity itemEntity = (XYItemEntity) entity;
					double[] coord = getCoordFromXYItemEntity(itemEntity);
					onMouseClickedOnItemEntity(coord[0], coord[1]);
				}
			}

			@Override
			public void chartMouseMoved(ChartMouseEvent e) {
				ChartEntity entity = e.getEntity();
				if (entity instanceof XYItemEntity) {
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				} else {
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
			}
		});
		
		return chartPanel;
	}
	
	private JFreeChart createChart(final NetworkConfiguration config) {
		XYDataset dataset = this.createDataset(config.getData());
		
		final JFreeChart chart = ChartFactory.createXYLineChart(
				config.getTitle(),
				config.getxAxisLabel(),
				config.getyAxisLabel(),
				dataset,
				PlotOrientation.VERTICAL,
				true,
				true,
				false
		);
		
		chart.setBackgroundPaint(Color.white);
		
		final XYPlot plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.WHITE);
		plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
		plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
		
		final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(false, true);
		renderer.setSeriesPaint(0, Color.BLUE);
		renderer.setSeriesShape(0, ShapeUtilities.createDiamond(2.5f));
		plot.setRenderer(renderer);
		
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		return chart;
	}
	
	private XYDataset createDataset(final Map<String, List<double[]>> data) {
		XYSeriesCollection dataset = new XYSeriesCollection();
		
		Set<String> seriesNames = data.keySet();
		for (String serieName : seriesNames) {
			List<double[]> points = data.get(serieName);
			if (points.size() > 0) {
				XYSeries series = new XYSeries(serieName);
				for (double[] point : points) {
					series.add(point[0], point[1]);
				}
				dataset.addSeries(series);
			}
		}
		return dataset;
	}
	
	private double[] getCoordFromXYItemEntity(final XYItemEntity xyItemEntity) {
		XYDataset dataset = xyItemEntity.getDataset();
		double[] result = new double[2];
		int seriesIndex = xyItemEntity.getSeriesIndex();
		int itemIndex = xyItemEntity.getItem();
		result[0] = dataset.getXValue(seriesIndex, itemIndex);
		result[1] = dataset.getYValue(seriesIndex, itemIndex);
		return result;
	}
	
	public List<double[]> getPointsBetweenXYItemsEntities(double startX, double endX) {
		List<double[]> result = new ArrayList<double[]>();
		
		XYSeriesCollection dataset = this.getDatasetFromChart();
		if (dataset == null) {
			return result;
		}
		
		int seriesIndex = 0;
		int itemsCount = dataset.getItemCount(seriesIndex);
		for (int i = 0; i < itemsCount; i++) {
			double x = dataset.getXValue(seriesIndex, i);
			if (x >= startX && x <= endX) {
				double y = dataset.getYValue(seriesIndex, i);
				result.add(new double[]{x, y});
			}
		}
		return result;
	}
	
	public void drawLinearRegression(XYSeries series) {
		Plot plot = this.chart.getPlot();
		XYPlot xyplot = null;
		XYSeriesCollection dataset = null;
		if (plot instanceof XYPlot) {
			xyplot = (XYPlot) plot;
			dataset = (XYSeriesCollection) xyplot.getDataset();
		} else {
			return;
		}
		
		int testSeriesIndex = dataset.getSeriesIndex(series.getKey());
		if (testSeriesIndex != -1) {
			dataset.removeSeries(testSeriesIndex);
		}
		
		dataset.addSeries(series);
		
		int seriesIndex = dataset.getSeriesIndex(series.getKey());
		XYLineAndShapeRenderer lrRenderer = (XYLineAndShapeRenderer) xyplot.getRenderer();
		lrRenderer.setSeriesShapesVisible(seriesIndex, false);
		lrRenderer.setSeriesLinesVisible(seriesIndex, true);
		lrRenderer.setSeriesPaint(seriesIndex, Color.RED);
		xyplot.setRenderer(lrRenderer);
	}
	
	private XYSeriesCollection getDatasetFromChart() {
		Plot plot = this.chart.getPlot();
		if (plot instanceof XYPlot) {
			XYPlot xyplot = (XYPlot) plot;
			return (XYSeriesCollection) xyplot.getDataset();
		} else {
			return null;
		}
	}
	
	public DistributionInfoPanel getInfoPanel() {
		return infoPanel;
	}

	public String getNetworkName() {
		return networkName;
	}

	public XYSeriesCollection getXYSeriesCollection() {
		Plot plot = this.chart.getPlot();
		if (plot instanceof XYPlot) {
			XYPlot xyplot = (XYPlot) plot;
			return (XYSeriesCollection)xyplot.getDataset();
		}
		return null;
	}
	
	@Override
	protected void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			onFrameClosed();
		}
		super.processWindowEvent(e);
	}
	
	public boolean isLogScaleUsed() {
		return logScaleUsed;
	}

	public abstract DistributionInfoPanel getInstanceOfDistributionInfoPanel(String networkName, boolean logScaleUsed);
	
	public abstract void onMouseClickedOnItemEntity(double x, double y);
	
	public abstract void onFrameClosed();

}
