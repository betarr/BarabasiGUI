package sk.sochuliak.barabasi.analysisframes;

import java.awt.Component;

import sk.sochuliak.barabasi.controllers.ControllerService;

public class ClusterDistributionFrame extends DistributionFrame {
	
	private static final long serialVersionUID = 1L;
	
	private boolean logScaleUsed = false;

	public ClusterDistributionFrame(String title, Component owner, GraphConfiguration config, boolean logScaleUsed) {
		super(title, owner, config);
		this.logScaleUsed = logScaleUsed;
	}

	@Override
	public DistributionInfoPanel getInstanceOfDistributionInfoPanel() {
		return ClusterDistributionInfoPanel.getInstance();
	}

	@Override
	public void onMouseClickedOnItemEntity(double x, double y) {
		ControllerService.getClusterDistributionController().setPointToInfoPanel(x, y);
	}

	@Override
	public void onFrameClosed() {
		if (this.logScaleUsed) {
			ControllerService.getAppController().setClusterDistributionLogShowed(false);
		} else {
			ControllerService.getAppController().setClusterDistributionShowed(false);
		}
	}
	
	

}
