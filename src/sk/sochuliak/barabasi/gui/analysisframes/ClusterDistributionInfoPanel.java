package sk.sochuliak.barabasi.gui.analysisframes;

import sk.sochuliak.barabasi.controllers.ControllerService;

public class ClusterDistributionInfoPanel extends DistributionInfoPanel {

	private static final long serialVersionUID = 1L;
	
	public ClusterDistributionInfoPanel(String networkName, boolean logScaleUsed) {
		super(networkName, logScaleUsed);
	}

	public static DistributionInfoPanel getInstance(String networkName, boolean logScaleUsed) {
		return new ClusterDistributionInfoPanel(networkName, logScaleUsed);
	}
	
	@Override
	public void onCalculateButtonClick(double[] startPoint, double[] endPoint) {
		if (this.isLogScaleUsed()) {
			ControllerService.getClusterDistributionLogController(this.getNetworkName()).doRegression(startPoint[0], endPoint[0]);
		} else {
			ControllerService.getClusterDistributionController(this.getNetworkName()).doRegression(startPoint[0], endPoint[0]);
		}
	}

}
