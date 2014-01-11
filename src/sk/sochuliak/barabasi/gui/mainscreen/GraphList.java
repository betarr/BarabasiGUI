package sk.sochuliak.barabasi.gui.mainscreen;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import sk.sochuliak.barabasi.controllers.ControllerService;
import sk.sochuliak.barabasi.gui.Strings;

public class GraphList extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private DefaultListModel<String> listModel = new DefaultListModel<>();
	private JButton addGraphButton = new JButton(Strings.GRAPHS_LIST_ADD_GRAPH);
	private JButton showInfoButton = new JButton(Strings.GRAPHS_LIST_SHOW_INFO_BUTTON);
	private JButton removeGraphButton = new JButton(Strings.GRAPHS_LIST_REMOVE_GRAPH);
	
	public GraphList() {
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
		labelPanel.add(new JLabel(Strings.GRAPHS_LIST_GRAPHS_LIST));
		panel.add(labelPanel);
		panel.add(this.addGraphButton);
		return panel;
	}

	private Component buildCenterPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		panel.setLayout(new GridLayout(1, 1));
		panel.add(new JList<String>(this.listModel));
		return panel;
	}

	private Component buildSouthPanel() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(200, 50));
		panel.setLayout(new GridLayout(2, 1));
		panel.add(this.showInfoButton);
		panel.add(this.removeGraphButton);
		return panel;
	}
	
	private void addListeners() {
		this.addGraphButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ControllerService.getAppController().showNewGraphDialog();
			}
		});
	}

	public void addGraphNameToList(String graphName) {
		this.listModel.addElement(graphName);
	}

}
