package sk.sochuliak.barabasi.gui.mainscreen;

import java.awt.Dimension;

import javax.swing.JFrame;


public class MainScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private BMenuBar bMenuBar = null;
	private BasicPropertiesPanel basicPropertiesPanel = null;

	public MainScreen(String title, Dimension size) {
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(size);
		
		this.addMenuBar();
		this.addBasicPropertiesPanel();
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private void addMenuBar() {
		this.bMenuBar = new BMenuBar();
		this.setJMenuBar(bMenuBar);
	}
	
	private void addBasicPropertiesPanel() {
		this.basicPropertiesPanel = new BasicPropertiesPanel();
		this.add(basicPropertiesPanel);
	}

	public BasicPropertiesPanel getBasicPropertiesPanel() {
		return basicPropertiesPanel;
	}

	public BMenuBar getBMenuBar() {
		return bMenuBar;
	}
	
}
