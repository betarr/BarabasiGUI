package sk.sochuliak.barabasi.controllers;

import java.util.List;

import sk.sochuliak.barabasi.analysisdialogs.DegreeDistributionFrame;
import sk.sochuliak.barabasi.utils.LinearRegression;

public class DegreeDistributionController {

	private DegreeDistributionFrame degreeDistributionDialog = null;

	public DegreeDistributionController(DegreeDistributionFrame degreeDistributionDialog) {
		this.degreeDistributionDialog = degreeDistributionDialog;
	}
	
	public void setPointToInfoPanel(double x, double y) {
		this.degreeDistributionDialog.getInfoPanel().setPoint(x, y);
	}
	
	public List<double[]> getPointsBetweenXYItemsEntities(double startX, double endX) {
		if (startX <= endX) {
			return this.degreeDistributionDialog.getPointsBetweenXYItemsEntities(startX, endX);
		} else {
			return this.degreeDistributionDialog.getPointsBetweenXYItemsEntities(endX, startX);
		}
	}
	
	public List<double[]> computeLine(List<double[]> points) {
		LinearRegression lr = new LinearRegression(points);
		List<double[]> linearRegression = lr.doLinearRegression();
		
		this.degreeDistributionDialog.getInfoPanel().setK(lr.getK());
		
		return linearRegression;
	}
	
	public void drawLinearRegression(List<double[]> linearRegressionPoints) {
		this.degreeDistributionDialog.drawLinearRegression(linearRegressionPoints);
	}
	
}
