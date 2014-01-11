package sk.sochuliak.barabasi.gui.mainscreen;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;


public class MainScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private BMenuBar bMenuBar = null;
	private BasicPropertiesPanel basicPropertiesPanel = null;
	private GraphList graphList = null;

	public MainScreen(String title, Dimension size) {
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(size);
		this.setLayout(new BorderLayout());
		
		this.addMenuBar();
		this.addBasicPropertiesPanel(BorderLayout.CENTER);
		this.addGraphList(BorderLayout.WEST);
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private void addMenuBar() {
		this.bMenuBar = new BMenuBar();
		this.setJMenuBar(bMenuBar);
	}
	
	private void addBasicPropertiesPanel(String position) {
		this.basicPropertiesPanel = new BasicPropertiesPanel();
		this.add(basicPropertiesPanel, position);
	}
	
	private void addGraphList(String position) {
		this.graphList = new GraphList();
		this.add(this.graphList, position);
	}

	public BasicPropertiesPanel getBasicPropertiesPanel() {
		return basicPropertiesPanel;
	}

	public BMenuBar getBMenuBar() {
		return bMenuBar;
	}
	
}
