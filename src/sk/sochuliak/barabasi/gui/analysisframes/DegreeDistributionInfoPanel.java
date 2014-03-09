package sk.sochuliak.barabasi.gui.analysisframes;

import sk.sochuliak.barabasi.controllers.ControllerService;

public class DegreeDistributionInfoPanel extends DistributionInfoPanel {

	private static final long serialVersionUID = 1L;
	
	public DegreeDistributionInfoPanel(String networkName, boolean logScaleUsed) {
		super(networkName, logScaleUsed);
	}

	public static DistributionInfoPanel getInstance(String networkName, boolean logScaleUsed) {
		return new DegreeDistributionInfoPanel(networkName, logScaleUsed);
	}

	@Override
	public void onCalculateButtonClick(double[] startPoint, double[] endPoint) {
		if (this.isLogScaleUsed()) {
			ControllerService.getDegreeDistributionLogController(this.getNetworkName()).doRegression(startPoint[0], endPoint[0]);
		} else {
			ControllerService.getDegreeDistributionController(this.getNetworkName()).doRegression(startPoint[0], endPoint[0]);
		}
	}
	
}
