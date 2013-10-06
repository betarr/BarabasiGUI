package sk.sochuliak.barabasi.utils;

import java.util.ArrayList;
import java.util.List;

public class LinearRegression {

	private List<double[]> points;
	
	private double slope;
	private double intercept;
	
	private double k;

	public LinearRegression(List<double[]> points) {
		this.points = points;
	}
	
	private void computeParameters() {
		double n = (double) this.points.size();
		double sumx = 0d;
		double sumy = 0d;
		double sumx2 = 0d;
		double sumxy = 0d;
		
		for (double[] point : this.points) {
			double x = point[0];
			double y = point[1];
			sumx += x;
			sumy += y;
			sumx2 += x*x;
			sumxy += x*y;
		}
		
		double meanX = sumx / n;
		double meanY = sumy / n;
		this.slope = (sumxy - sumx * meanY) / (sumx2 - sumx * meanX);
		this.intercept = meanY - slope * meanX;
	}
	
	public double calculateY (double x) {
		return this.slope * x + this.intercept;
	}
	
	public double calculateX (double y) {
		return (y - this.intercept) / this.slope;
	}
	
	public List<double[]> doLinearRegression() {
		this.computeParameters();
		
		List<double[]> result = new ArrayList<double[]>();
		for (double[] point : points) {
			double x = point[0];
			
			double resultX = x;
			double resultY = this.calculateY(x);
			
			result.add(new double[]{resultX, resultY});
		}
		this.k = this.computeK(result.get(0), result.get(result.size()-1));
		return result;
	}

	private double computeK(double[] point1, double[] point2) {
		double[] s = new double[] {point2[0] - point1[0], point2[1] - point1[1]};
		double[] n = new double[] {-(s[1]), s[0]};
		
		return (point2[1] - n[1]) / point2[0];
	}

	public double getK() {
		return k;
	}
}
