package sk.sochuliak.barabasi.analysisdialogs;

import java.awt.Component;

import sk.sochuliak.barabasi.controllers.ControllerService;

public class DegreeDistributionFrame extends DistributionFrame {

	private static final long serialVersionUID = 1L;
	
	public DegreeDistributionFrame(String title, Component owner, GraphConfiguration config) {
		super(title, owner, config);
	}

	@Override
	public DistributionInfoPanel getInstanceOfDistributionInfoPanel() {
		return DegreeDistributionInfoPanel.getInstance();
	}

	@Override
	public void onMouseClickedOnItemEntity(double x, double y) {
		ControllerService.getDegreeDistributionController().setPointToInfoPanel(x, y);
	}
}
