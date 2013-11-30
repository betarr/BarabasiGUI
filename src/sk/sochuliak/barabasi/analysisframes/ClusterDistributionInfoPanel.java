package sk.sochuliak.barabasi.analysisframes;

import sk.sochuliak.barabasi.controllers.ControllerService;

public class ClusterDistributionInfoPanel extends DistributionInfoPanel {

	private static final long serialVersionUID = 1L;
	
	public ClusterDistributionInfoPanel(boolean logScaleUsed) {
		super(logScaleUsed);
	}

	public static DistributionInfoPanel getInstance(boolean logScaleUsed) {
		return new ClusterDistributionInfoPanel(logScaleUsed);
	}
	
	@Override
	public void onCalculateButtonClick(double[] startPoint, double[] endPoint) {
		if (this.isLogScaleUsed()) {
			ControllerService.getClusterDistributionLogController().doRegression(startPoint[0], endPoint[0]);
		} else {
			ControllerService.getClusterDistributionController().doRegression(startPoint[0], endPoint[0]);
		}
	}

}
