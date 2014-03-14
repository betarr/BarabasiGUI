package sk.sochuliak.barabasi.networkselectdialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;

import sk.sochuliak.barabasi.gui.MainGuiConfiguration;
import sk.sochuliak.barabasi.gui.Strings;

public class NetworkSelectDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private Component owner = null;
	
	private List<String> networkNames = new ArrayList<String>();
	
	private boolean singleSelect = true;
	
	private ButtonGroup buttonGroup = null;
	private List<JToggleButton> buttons = new ArrayList<JToggleButton>();
	
	private JButton okButton = new JButton(Strings.OK);
	private JButton cancelButton = new JButton(Strings.CANCEL);
	
	public NetworkSelectDialog(Component owner, List<String> networkNames, boolean singleSelect) {
		this.owner = owner;
		this.networkNames = networkNames;
		Collections.sort(this.networkNames);
		this.singleSelect = singleSelect;
		this.buildDialog();
	}
	
	private void buildDialog() {
		this.setTitle(Strings.NETWORK_SELECT_DIALOG_TITLE);
		this.setSize(MainGuiConfiguration.NETWORK_SELECT_DIALOG_SIZE);
		this.setLocationRelativeTo(this.owner);
		
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
		
		JPanel centerFlowPanel = new JPanel();
		centerFlowPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(this.networkNames.size(), 1));
		if (this.singleSelect) {
			this.buttonGroup = new ButtonGroup();
		}
		for (int i = 0; i < this.networkNames.size(); i++) {
			JToggleButton button = this.getButton(this.networkNames.get(i));
			if (this.singleSelect) {
				this.buttonGroup.add(button);
			}
			centerPanel.add(button);
			this.buttons.add(button);
		}
		
		centerFlowPanel.add(centerPanel);
		contentPanel.add(new JScrollPane(centerFlowPanel), BorderLayout.CENTER);
	
		contentPanel.add(this.buildControlPanel(), BorderLayout.SOUTH);
		
		this.add(contentPanel);
	}
	
	private JToggleButton getButton(String label) {
		JToggleButton button = null;
		if (this.singleSelect) {
			button = new JRadioButton(label);
		} else {
			button = new JCheckBox(label);
		}
		return button;
	}
	
	private JPanel buildControlPanel() {
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		controlPanel.add(this.okButton);
		controlPanel.add(this.cancelButton);
		return controlPanel;
	}
	
	public String getSelectedNetworkName() {
		List<String> selectedNames = this.getSelectedNetworkNames();
		if (selectedNames.size() != 0) {
			return selectedNames.get(0);
		}
		return null;
	}
	
	public List<String> getSelectedNetworkNames() {
		List<String> result = new ArrayList<String>();
		for (JToggleButton button : this.buttons) {
			if (button.isSelected()) {
				result.add(button.getText());
			}
		}
		return result;
	}

	public void setOkButtonActionListener(ActionListener okButtonActionListener) {
		this.okButton.addActionListener(okButtonActionListener);
	}


}
