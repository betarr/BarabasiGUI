package sk.sochuliak.barabasi.gui.newgraphdialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import sk.sochuliak.barabasi.controllers.ControllerService;
import sk.sochuliak.barabasi.gui.MainGuiConfiguration;
import sk.sochuliak.barabasi.gui.Strings;
import sk.sochuliak.barabasi.network.NetworkBuildConfiguration;
import sk.sochuliak.barabasi.utils.CommonUtils;

public class NewGraphDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private Component owner;
	
	JComboBox<String> growthManagementComboBox = null;
	JTextField numberOfNodesTextField = null;
	JTextField numberOfEdgesTextField = null;
	
	public NewGraphDialog(Component owner) {
		this.owner = owner;
		
		this.setTitle(Strings.NEW_GRAPH_DIALOG_TITLE);
		this.setSize(MainGuiConfiguration.NEW_GRAPH_DIALOG_SIZE);
		
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setLocationRelativeTo(this.owner);
		
		this.buildContent();
	}

	private void buildContent() {
		this.setLayout(new BorderLayout());
		this.add(this.buildConfigurationPanel(), BorderLayout.CENTER);
		this.add(this.buildControlPanel(), BorderLayout.SOUTH);
	}
	
	private JPanel buildConfigurationPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel growthManagementLabel = new JLabel(Strings.NEW_GRAPH_DIALOG_GROWTH_MANAGEMENT);
		growthManagementLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		panel.add(growthManagementLabel, c);
		
		String[] growthManagementOptions = new String[] {
			Strings.NEW_GRAPH_DIALOG_GROWTH_MANAGEMENT_NODE_DEGREE,
			Strings.NEW_GRAPH_DIALOG_GROWTH_MANAGEMENT_NODE_CLASTER,
			Strings.NEW_GRAPH_DIALOG_GROWTH_MANAGEMENT_RANDOM
		};
		this.growthManagementComboBox = new JComboBox<String>(growthManagementOptions);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 2;
		panel.add(growthManagementComboBox, c);
		
		JLabel numberOfNodesLabel = new JLabel(Strings.NEW_GRAPH_DIALOG_NUMBER_OF_NODES);
		numberOfNodesLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		panel.add(numberOfNodesLabel, c);
		
		this.numberOfNodesTextField = new JTextField();
		this.numberOfNodesTextField.setText("5000");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 1;
		c.gridwidth = 1;
		panel.add(numberOfNodesTextField, c);
		
		JLabel numberOfEdgesLabel = new JLabel(Strings.NEW_GRAPH_DIALOG_NUMBER_OF_EDGES);
		numberOfEdgesLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;
		panel.add(numberOfEdgesLabel, c);
		
		this.numberOfEdgesTextField = new JTextField();
		this.numberOfEdgesTextField.setText("2");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 2;
		c.gridwidth = 1;
		panel.add(numberOfEdgesTextField, c);
		
		return panel;
	}

	private JPanel buildControlPanel() {
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JButton createButton = new JButton(Strings.OK);
		createButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String growthManagementComboValue = (String)growthManagementComboBox.getSelectedItem();
				String numberOfNodesText = numberOfNodesTextField.getText();
				String numberOfEdgesText = numberOfEdgesTextField.getText();
				
				StringBuilder sb = new StringBuilder();
				if (!growthManagementComboValue.equals(Strings.NEW_GRAPH_DIALOG_GROWTH_MANAGEMENT_NODE_DEGREE)
						&& !growthManagementComboValue.equals(Strings.NEW_GRAPH_DIALOG_GROWTH_MANAGEMENT_NODE_CLASTER)
						&& !growthManagementComboValue.equals(Strings.NEW_GRAPH_DIALOG_GROWTH_MANAGEMENT_RANDOM)) {
					sb.append(Strings.WRONG_VALUE).append(": ").append(Strings.NEW_GRAPH_DIALOG_GROWTH_MANAGEMENT).append("\n");
				}
				if (!CommonUtils.isPositiveNumber(numberOfNodesText)) {
					sb.append(Strings.WRONG_VALUE).append(": ").append(Strings.NEW_GRAPH_DIALOG_NUMBER_OF_NODES).append("\n");
				}
				if (!CommonUtils.isPositiveNumber(numberOfEdgesText)) {
					sb.append(Strings.WRONG_VALUE).append(": ").append(Strings.NEW_GRAPH_DIALOG_NUMBER_OF_EDGES);
				}
				if (sb.toString().equals("")) {
					int growthManagement = -1;
					if (growthManagementComboValue.equals(Strings.NEW_GRAPH_DIALOG_GROWTH_MANAGEMENT_NODE_DEGREE)) {
						growthManagement = NetworkBuildConfiguration.DEGREE_DRIVEN;
					} else if (growthManagementComboValue.equals(Strings.NEW_GRAPH_DIALOG_GROWTH_MANAGEMENT_NODE_CLASTER)) {
						growthManagement = NetworkBuildConfiguration.CLUSTER_DRIVEN;
					} else if (growthManagementComboValue.equals(Strings.NEW_GRAPH_DIALOG_GROWTH_MANAGEMENT_RANDOM)) {
						growthManagement = NetworkBuildConfiguration.RANDOM_DRIVEN;
					}
					int numberOfNodes = Integer.parseInt(numberOfNodesText);
					int numberOfEdges = Integer.parseInt(numberOfEdgesText);
					
					dispose();
					ControllerService.getNetworkController().buildNetwork(growthManagement, numberOfNodes, numberOfEdges);
				} else {
					JOptionPane.showMessageDialog(
							owner,
							sb.toString(),
							Strings.ERROR,
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JButton cancelButton = new JButton(Strings.CANCEL);
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		controlPanel.add(createButton);
		controlPanel.add(cancelButton);
		
		return controlPanel;
	}
	

	
	

}
