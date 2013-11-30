package sk.sochuliak.barabasi.analysisframes;

import sk.sochuliak.barabasi.controllers.ControllerService;

public class DegreeDistributionInfoPanel extends DistributionInfoPanel {

	private static final long serialVersionUID = 1L;
	
	public DegreeDistributionInfoPanel(boolean logScaleUsed) {
		super(logScaleUsed);
	}

	public static DistributionInfoPanel getInstance(boolean logScaleUsed) {
		return new DegreeDistributionInfoPanel(logScaleUsed);
	}

	@Override
	public void onCalculateButtonClick(double[] startPoint, double[] endPoint) {
		if (this.isLogScaleUsed()) {
			ControllerService.getDegreeDistributionLogController().doRegression(startPoint[0], endPoint[0]);
		} else {
			ControllerService.getDegreeDistributionController().doRegression(startPoint[0], endPoint[0]);
		}
	}
	
}
