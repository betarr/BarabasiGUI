package sk.sochuliak.barabasi.gui.mainscreen;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sk.sochuliak.barabasi.controllers.ControllerService;
import sk.sochuliak.barabasi.gui.Strings;

public class NetworksList extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JList<String> list = null;
	private DefaultListModel<String> listModel = new DefaultListModel<String>();
	private JButton addNetworkButton = new JButton(Strings.NETWORK_LIST_ADD_NETWORK);
	private JButton removeNetworkButton = new JButton(Strings.NETWORK_LIST_REMOVE_NETWORK);
	
	public NetworksList() {
		this.setLayout(new BorderLayout());
		
		this.add(this.buildTopPanel(), BorderLayout.NORTH);
		this.add(this.buildCenterPanel(), BorderLayout.CENTER);
		this.add(this.buildSouthPanel(), BorderLayout.SOUTH);
		
		this.addListeners();
	}
	
	private Component buildTopPanel() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(200, 50));
		panel.setLayout(new GridLayout(2, 1));
		JPanel labelPanel = new JPanel();
		labelPanel.add(new JLabel(Strings.NETWORK_LIST_NETWORKS_LIST));
		panel.add(labelPanel);
		panel.add(this.addNetworkButton);
		return panel;
	}

	private Component buildCenterPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		panel.setLayout(new GridLayout(1, 1));
		this.list = new JList<String>(this.listModel);
		this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panel.add(new JScrollPane(this.list));
		return panel;
	}

	private Component buildSouthPanel() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(200, 25));
		panel.setLayout(new GridLayout(1, 1));
		this.removeNetworkButton.setEnabled(false);
		panel.add(this.removeNetworkButton);
		return panel;
	}
	
	private void addListeners() {
		this.addNetworkButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent ae) {
				ControllerService.getAppController().showNewNetworkDialog();
			}
		});
		
		this.removeNetworkButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent ae) {
				String selectedNetworkName = list.getSelectedValue();
				if (selectedNetworkName != null) {
					int dialogResult = JOptionPane.showConfirmDialog(
							ControllerService.getAppController().getMainScreen(),
							Strings.NETWORK_LIST_REMOVE_NETWORK_QUESTION + " " + selectedNetworkName + "?",
							Strings.NETWORK_LIST_REMOVE_NETWORK,
							JOptionPane.YES_NO_OPTION);
					if (dialogResult == JOptionPane.YES_OPTION) {
						ControllerService.getAppController().removeNetwork(list.getSelectedValue());
					}
				}
			}
		});
		
		this.list.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					String selectedNetworkName = list.getSelectedValue();
					ControllerService.getAppController().updateDataInBasicPropertiesTable(selectedNetworkName);
					removeNetworkButton.setEnabled(selectedNetworkName != null);
				}
			}
		});
	}

	public void addNetworkNameToList(String networkName) {
		this.listModel.addElement(networkName);
		
		String[] content = new String[this.listModel.size()];
		this.listModel.copyInto(content);
		this.listModel.clear();
		Arrays.sort(content);
		for (String networkName1 : content) {
			this.listModel.addElement(networkName1);
		}
		this.list.setSelectedValue(networkName, false);
	}
	
	public void removeNetworkNameFromList(String networkName) {
		this.listModel.removeElement(networkName);
	}
	
	public List<String> getNetworksNames() {
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < this.listModel.getSize(); i++) {
			result.add(this.listModel.getElementAt(i));
		}
		return result;
	}
	
	public boolean isNetworkWithName(String networkName) {
		return this.getNetworksNames().contains(networkName);
	}

}
