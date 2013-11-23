package sk.sochuliak.barabasi.analysisframes;

import sk.sochuliak.barabasi.controllers.ControllerService;

public class DegreeDistributionInfoPanel extends DistributionInfoPanel {

	private static final long serialVersionUID = 1L;

	public static DistributionInfoPanel getInstance() {
		return new DegreeDistributionInfoPanel();
	}

	@Override
	public void onCalculateButtonClick(double[] startPoint, double[] endPoint) {
		ControllerService.getDegreeDistributionController().doRegression(startPoint[0], endPoint[0]);
	}
	
}
