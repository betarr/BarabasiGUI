package sk.sochuliak.barabasi.analysisframes;

import java.awt.Component;

import sk.sochuliak.barabasi.controllers.ControllerService;

public class ClusterDistributionFrame extends DistributionFrame {
	
	private static final long serialVersionUID = 1L;
	
	public ClusterDistributionFrame(String title, Component owner, GraphConfiguration config, boolean logScaleUsed) {
		super(title, owner, config, logScaleUsed);
	}

	@Override
	public DistributionInfoPanel getInstanceOfDistributionInfoPanel(boolean logScaleUsed) {
		return ClusterDistributionInfoPanel.getInstance(logScaleUsed);
	}

	@Override
	public void onMouseClickedOnItemEntity(double x, double y) {
		if (this.isLogScaleUsed()) {
			ControllerService.getClusterDistributionLogController().setPointToInfoPanel(x, y);
		} else {
			ControllerService.getClusterDistributionController().setPointToInfoPanel(x, y);
		}
	}

	@Override
	public void onFrameClosed() {
		if (this.isLogScaleUsed()) {
			ControllerService.getAppController().setClusterDistributionLogShowed(false);
		} else {
			ControllerService.getAppController().setClusterDistributionShowed(false);
		}
	}
	
	

}
