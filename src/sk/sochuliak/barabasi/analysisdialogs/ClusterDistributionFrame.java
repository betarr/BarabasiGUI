package sk.sochuliak.barabasi.analysisdialogs;

import java.awt.Component;

import sk.sochuliak.barabasi.controllers.ControllerService;

public class ClusterDistributionFrame extends DistributionFrame {
	
	private static final long serialVersionUID = 1L;

	public ClusterDistributionFrame(String title, Component owner, GraphConfiguration config) {
		super(title, owner, config);
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
		ControllerService.getAppController().setClusterDistributionShowed(false);
	}
	
	

}
