package sk.sochuliak.barabasi.gui.analysisframes;

import java.awt.Component;

import sk.sochuliak.barabasi.controllers.ControllerService;

public class DegreeDistributionFrame extends DistributionFrame {

	private static final long serialVersionUID = 1L;
	
	public DegreeDistributionFrame(String title, Component owner, String networkName, NetworkConfiguration config, boolean logScaleUsed) {
		super(title, owner, networkName, config, logScaleUsed);
	}

	@Override
	public DistributionInfoPanel getInstanceOfDistributionInfoPanel(String networkName, boolean logScaleUsed) {
		return DegreeDistributionInfoPanel.getInstance(networkName, logScaleUsed);
	}

	@Override
	public void onMouseClickedOnItemEntity(double x, double y) {
		if (this.isLogScaleUsed()) {
			ControllerService.getDegreeDistributionLogController(this.getNetworkName()).setPointToInfoPanel(x, y);
		} else {
			ControllerService.getDegreeDistributionController(this.getNetworkName()).setPointToInfoPanel(x, y);
		}
	}

	@Override
	public void onFrameClosed() {
		if (this.isLogScaleUsed()) {
			ControllerService.unregisterDegreeDistriubtionLogController(this.getNetworkName());
		} else {
			ControllerService.unregisterDegreeDistributionController(this.getNetworkName());
		}
	}
}
