package sk.sochuliak.giraphe.gui.analysisframes;

import java.awt.Component;

import sk.sochuliak.giraphe.controllers.ControllerService;

public class ClusterDistributionFrame extends DistributionFrame {
	
	private static final long serialVersionUID = 1L;
	
	public ClusterDistributionFrame(String title, Component owner, String networkName, NetworkConfiguration config, boolean logScaleUsed) {
		super(title, owner, networkName, config, logScaleUsed);
	}

	@Override
	public DistributionInfoPanel getInstanceOfDistributionInfoPanel(String networkName, boolean logScaleUsed) {
		return ClusterDistributionInfoPanel.getInstance(networkName, logScaleUsed);
	}

	@Override
	public void onMouseClickedOnItemEntity(double x, double y) {
		if (this.isLogScaleUsed()) {
			ControllerService.getClusterDistributionLogController(this.getNetworkName()).setPointToInfoPanel(x, y);
		} else {
			ControllerService.getClusterDistributionController(this.getNetworkName()).setPointToInfoPanel(x, y);
		}
	}

	@Override
	public void onFrameClosed() {
		if (this.isLogScaleUsed()) {
			ControllerService.unregisterClusterDistributionLogController(this.getNetworkName());
		} else {
			ControllerService.unregisterClusterDistributionController(this.getNetworkName());
		}
	}
	
	

}
