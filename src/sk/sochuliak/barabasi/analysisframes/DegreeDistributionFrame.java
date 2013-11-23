package sk.sochuliak.barabasi.analysisframes;

import java.awt.Component;

import sk.sochuliak.barabasi.controllers.ControllerService;

public class DegreeDistributionFrame extends DistributionFrame {

	private static final long serialVersionUID = 1L;
	
	private boolean logScaleUsed = false;
	
	public DegreeDistributionFrame(String title, Component owner, GraphConfiguration config, boolean logScaleUsed) {
		super(title, owner, config);
		this.logScaleUsed = logScaleUsed;
	}

	@Override
	public DistributionInfoPanel getInstanceOfDistributionInfoPanel() {
		return DegreeDistributionInfoPanel.getInstance();
	}

	@Override
	public void onMouseClickedOnItemEntity(double x, double y) {
		ControllerService.getDegreeDistributionController().setPointToInfoPanel(x, y);
	}

	@Override
	public void onFrameClosed() {
		if (this.logScaleUsed) {
			ControllerService.getAppController().setDegreeDistributionLogShowed(false);
		} else {
			ControllerService.getAppController().setDegreeDistributionShowed(false);
		}
	}
}
