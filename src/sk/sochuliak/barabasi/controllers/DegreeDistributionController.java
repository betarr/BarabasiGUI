package sk.sochuliak.barabasi.controllers;

import java.util.List;

import org.jfree.data.function.LineFunction2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.statistics.Regression;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import sk.sochuliak.barabasi.analysisdialogs.DegreeDistributionFrame;
import sk.sochuliak.barabasi.gui.Strings;

public class DegreeDistributionController {

	private DegreeDistributionFrame degreeDistributionFrame = null;

	public DegreeDistributionController(DegreeDistributionFrame degreeDistributionDialog) {
		this.degreeDistributionFrame = degreeDistributionDialog;
	}
	
	public void setPointToInfoPanel(double x, double y) {
		this.degreeDistributionFrame.getInfoPanel().setPoint(x, y);
	}
	
	public void drawLinearRegression(XYSeries series) {
		this.degreeDistributionFrame.drawLinearRegression(series);
	}

	@SuppressWarnings("unchecked")
	public void doRegression(double startX, double endX) {
		XYSeriesCollection dataset = this.degreeDistributionFrame.getXYSeriesCollection();
		XYSeries seriesTemp = new XYSeries("temp");
		for (XYDataItem dataItem : (List<XYDataItem>) dataset.getSeries(0).getItems()) {
			if (dataItem.getXValue() >= startX && dataItem.getXValue() <= endX) {
				seriesTemp.add(dataItem);
			}
		}
		XYSeriesCollection datasetTemp = new XYSeriesCollection();
		datasetTemp.addSeries(seriesTemp);
		double[] aAndB = Regression.getOLSRegression(datasetTemp, 0);
		XYSeries series = DatasetUtilities.sampleFunction2DToSeries(new LineFunction2D(aAndB[0], aAndB[1]), startX, endX, 2, Strings.LINEAR_REGRESION);
		this.drawLinearRegression(series);
		this.degreeDistributionFrame.getInfoPanel().setK(aAndB[1]);
	}
}