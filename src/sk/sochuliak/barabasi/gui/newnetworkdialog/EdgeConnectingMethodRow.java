package sk.sochuliak.barabasi.gui.newnetworkdialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sk.sochuliak.barabasi.gui.Strings;
import sk.sochuliak.barabasi.network.EdgeConnectingMethodRowConfig;

public class EdgeConnectingMethodRow extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private EdgeConnectingMethodPanel parent;
	
	private String[] connectingMethod = new String[] {
			Strings.NEW_NETWORK_DIALOG_GROWTH_MANAGEMENT_NODE_DEGREE,
			Strings.NEW_NETWORK_DIALOG_GROWTH_MANAGEMENT_NODE_CLASTER,
			Strings.NEW_NETWORK_DIALOG_GROWTH_MANAGEMENT_RANDOM	
	};
	
	private String[] connectingRange = new String[] {
			Strings.NEW_NETWORK_DIALOG_RANGE_NEIGHBOR,
			Strings.NEW_NETWORK_DIALOG_RANGE_ALL
	};
	
	private JTextField numberOfEdgesTextField = new JTextField();
	private JComboBox<String> connectingMethodComboBox = new JComboBox<String>(this.connectingMethod);
	private JComboBox<String> connectingRangeComboBox = new JComboBox<String>(this.connectingRange);
	
	private JButton removeButton = new JButton(Strings.NEW_NETWORK_DIALOG_REMOVE);
	
	public EdgeConnectingMethodRow(EdgeConnectingMethodPanel parent) {
		this.parent = parent;
		this.buildContent();
		this.createEvents();
	}

	private void buildContent() {
		this.setLayout(new BorderLayout());

		JPanel leftSide = new JPanel();
		leftSide.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.numberOfEdgesTextField.setPreferredSize(new Dimension(50, 20));
		leftSide.add(this.numberOfEdgesTextField);
		leftSide.add(new JLabel(Strings.NEW_NETWORK_DIALOG_EDGES));
		leftSide.add(this.connectingMethodComboBox);
		leftSide.add(new JLabel(Strings.TO));
		leftSide.add(this.connectingRangeComboBox);
		
		JPanel rightSide = new JPanel();
		rightSide.setLayout(new FlowLayout(FlowLayout.RIGHT));
		rightSide.add(this.removeButton);
		
		this.add(leftSide, BorderLayout.CENTER);
		this.add(rightSide, BorderLayout.EAST);
	}
	
	private void createEvents() {
		this.removeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				parent.removeItem(EdgeConnectingMethodRow.this);
			}
		});
	}
	
	public EdgeConnectingMethodRowConfig getConfig() throws NumberFormatException {
		EdgeConnectingMethodRowConfig config = new EdgeConnectingMethodRowConfig();
		config.setNumberOfEdges(Integer.parseInt(this.numberOfEdgesTextField.getText()));
		
		String connectingMethodText = (String)this.connectingMethodComboBox.getSelectedItem();
		if (connectingMethodText.equals(Strings.NEW_NETWORK_DIALOG_GROWTH_MANAGEMENT_NODE_DEGREE)) {
			config.setConnectingMethod(EdgeConnectingMethodRowConfig.DRIVEN_DEGREE);
		} else if (connectingMethodText.equals(Strings.NEW_NETWORK_DIALOG_GROWTH_MANAGEMENT_NODE_CLASTER)) {
			config.setConnectingMethod(EdgeConnectingMethodRowConfig.DRIVEN_CLUSTER);
		} else if (connectingMethodText.equals(Strings.NEW_NETWORK_DIALOG_GROWTH_MANAGEMENT_RANDOM)) {
			config.setConnectingMethod(EdgeConnectingMethodRowConfig.DRIVEN_RANDOM);
		}
		
		String connectingRangeText = (String)this.connectingRangeComboBox.getSelectedItem();
		if (connectingRangeText.equals(Strings.NEW_NETWORK_DIALOG_RANGE_NEIGHBOR)) {
			config.setRange(EdgeConnectingMethodRowConfig.RANGE_NEIGHBOR);
		} else if (connectingRangeText.equals(Strings.NEW_NETWORK_DIALOG_RANGE_ALL)) {
			config.setRange(EdgeConnectingMethodRowConfig.RANGE_ALL);
		}
		return config;
	}

}
