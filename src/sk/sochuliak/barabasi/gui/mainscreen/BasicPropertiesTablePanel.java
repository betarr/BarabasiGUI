package sk.sochuliak.barabasi.gui.mainscreen;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class BasicPropertiesTablePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private BasicPropertiesTable basicPropertiesTable;
	private JPanel buttonsPanel;
	
	public BasicPropertiesTablePanel() {
		this.basicPropertiesTable = new BasicPropertiesTable();
		this.buttonsPanel = this.buildButtonsPanel();
		this.setLayout(new BorderLayout());
		
		this.add(this.basicPropertiesTable, BorderLayout.CENTER);
		this.add(buttonsPanel, BorderLayout.EAST);
	}

	public BasicPropertiesTable getBasicPropertiesTable() {
		return basicPropertiesTable;
	}
	
	private JPanel buildButtonsPanel() {
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBackground(Color.WHITE);
		
		PropertyRefreshButton totalNodesCountButton = new PropertyRefreshButton(BasicPropertiesTable.TOTAL_NODES_COUNT);
		PropertyRefreshButton averageNodeDegreeButton = new PropertyRefreshButton(BasicPropertiesTable.AVERAGE_NODE_DEGREE);
		PropertyRefreshButton averageNodeClusterButton = new PropertyRefreshButton(BasicPropertiesTable.AVERAGE_CLUSTER_RATIO);
		PropertyRefreshButton averageDistanceButton = new PropertyRefreshButton(BasicPropertiesTable.AVERAGE_DISTANCE);
		PropertyRefreshButton numberOfNeighboringNodesButton = new PropertyRefreshButton(BasicPropertiesTable.NUMBER_OF_NEIGHBORING_NODES);
		PropertyRefreshButton maxNodeDegreeButton = new PropertyRefreshButton(BasicPropertiesTable.MAX_NODE_DEGREE);
		
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
		buttonsPanel.add(totalNodesCountButton);
		buttonsPanel.add(averageNodeDegreeButton);
		buttonsPanel.add(averageNodeClusterButton);
		buttonsPanel.add(averageDistanceButton);
		buttonsPanel.add(numberOfNeighboringNodesButton);
		buttonsPanel.add(maxNodeDegreeButton);
		
		return buttonsPanel;
	}

}
