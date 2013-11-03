package sk.sochuliak.barabasi.controllers;

import java.util.List;

import sk.sochuliak.barabasi.analysisdialogs.ClusterDistributionFrame;
import sk.sochuliak.barabasi.utils.LinearRegression;

public class ClusterDistributionController {

	private ClusterDistributionFrame clusterDistributionDialog = null;

	public ClusterDistributionController(ClusterDistributionFrame clusterDistributionDialog) {
		this.clusterDistributionDialog = clusterDistributionDialog;
	}
	
	public void setPointToInfoPanel(double x, double y) {
		this.clusterDistributionDialog.getInfoPanel().setPoint(x, y);
	}
	
	public List<double[]> getPointsBetweenXYItemsEntities(double startX, double endX) {
		if (startX <= endX) {
			return this.clusterDistributionDialog.getPointsBetweenXYItemsEntities(startX, endX);
		} else {
			return this.clusterDistributionDialog.getPointsBetweenXYItemsEntities(endX, startX);
		}
	}
	
	public List<double[]> computeLine(List<double[]> points) {
		LinearRegression lr = new LinearRegression(points);
		List<double[]> linearRegression = lr.doLinearRegression();
		
		this.clusterDistributionDialog.getInfoPanel().setK(lr.getK());
		
		return linearRegression;
	}
	
	public void drawLinearRegression(List<double[]> linearRegressionPoints) {
		this.clusterDistributionDialog.drawLinearRegression(linearRegressionPoints);
	}
	
	
}
