package sk.sochuliak.barabasi.analysisframes;

import java.awt.Component;

import sk.sochuliak.barabasi.controllers.ControllerService;

public class DegreeDistributionFrame extends DistributionFrame {

	private static final long serialVersionUID = 1L;
	
	public DegreeDistributionFrame(String title, Component owner, GraphConfiguration config, boolean logScaleUsed) {
		super(title, owner, config, logScaleUsed);
	}

	@Override
	public DistributionInfoPanel getInstanceOfDistributionInfoPanel(boolean logScaleUsed) {
		return DegreeDistributionInfoPanel.getInstance(logScaleUsed);
	}

	@Override
	public void onMouseClickedOnItemEntity(double x, double y) {
		if (this.isLogScaleUsed()) {
			ControllerService.getDegreeDistributionLogController().setPointToInfoPanel(x, y);
		} else {
			ControllerService.getDegreeDistributionController().setPointToInfoPanel(x, y);
		}
	}

	@Override
	public void onFrameClosed() {
		if (this.isLogScaleUsed()) {
			ControllerService.getAppController().setDegreeDistributionLogShowed(false);
		} else {
			ControllerService.getAppController().setDegreeDistributionShowed(false);
		}
	}
}
