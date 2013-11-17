package sk.sochuliak.barabasi.analysisdialogs;

import sk.sochuliak.barabasi.controllers.ControllerService;

public class ClusterDistributionInfoPanel extends DistributionInfoPanel {

private static final long serialVersionUID = 1L;
	
	public static DistributionInfoPanel getInstance() {
		return new ClusterDistributionInfoPanel();
	}
	
	@Override
	public void onCalculateButtonClick(double[] startPoint, double[] endPoint) {
		ControllerService.getClusterDistributionController().doRegression(startPoint[0], endPoint[0]);
	}

}
