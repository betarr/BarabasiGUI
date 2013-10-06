package sk.sochuliak.barabasi.gui.mainscreen;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import sk.sochuliak.barabasi.gui.Strings;

public class BasicPropertiesPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private BasicPropertiesTable basicPropertiesTable = null;

	public BasicPropertiesPanel() {
		this.setLayout(new BorderLayout());
		
		this.add(this.buildLabel(), BorderLayout.NORTH);
		this.add(this.buildPropertiesTable(), BorderLayout.CENTER);
	}
	
	public JPanel buildLabel() {
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout());
		titlePanel.add(new JLabel(Strings.BASIC_PROPERTIES_TITLE));
		return titlePanel;
	}
	
	public JScrollPane buildPropertiesTable() {
		this.basicPropertiesTable = new BasicPropertiesTable();
		JScrollPane propertiesPanel = new JScrollPane(this.basicPropertiesTable);
		return propertiesPanel;
	}

	public BasicPropertiesTable getBasicPropertiesTable() {
		return basicPropertiesTable;
	}
}
