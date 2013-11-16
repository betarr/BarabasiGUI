package sk.sochuliak.barabasi.controllers;

import org.jfree.data.function.LineFunction2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.statistics.Regression;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import sk.sochuliak.barabasi.analysisdialogs.ClusterDistributionFrame;
import sk.sochuliak.barabasi.gui.Strings;

public class ClusterDistributionController {

	private ClusterDistributionFrame clusterDistributionDialog = null;

	public ClusterDistributionController(ClusterDistributionFrame clusterDistributionDialog) {
		this.clusterDistributionDialog = clusterDistributionDialog;
	}
	
	public void setPointToInfoPanel(double x, double y) {
		this.clusterDistributionDialog.getInfoPanel().setPoint(x, y);
	}
	
	public void drawLinearRegression(XYSeries series) {
		this.clusterDistributionDialog.drawLinearRegression(series);
	}
	
	public void doRegression(double startX, double endX) {
		XYSeriesCollection dataset = this.clusterDistributionDialog.getXYSeriesCollection();
		double[] aAndB = Regression.getOLSRegression(dataset, 0);
		XYSeries series = DatasetUtilities.sampleFunction2DToSeries(new LineFunction2D(aAndB[0], aAndB[1]), startX, endX, 2, Strings.LINEAR_REGRESION);
		this.drawLinearRegression(series);
		this.clusterDistributionDialog.getInfoPanel().setK(aAndB[1]);
	}
	
	
}
