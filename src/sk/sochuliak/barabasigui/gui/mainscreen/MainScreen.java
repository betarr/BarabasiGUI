package sk.sochuliak.barabasigui.gui.mainscreen;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenuBar;


public class MainScreen {

	private JFrame jframe = null;
	
	public MainScreen(String title, Dimension size) {
		this.jframe = new JFrame(title);
		this.jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.jframe.setPreferredSize(size);
		
		this.addMenuBar();
//		this.addPropertiesTable();
		
		this.show();
	}
	
	private void show() {
		this.jframe.pack();
		this.jframe.setLocationRelativeTo(null);
		this.jframe.setVisible(true);
	}
	
	public void close() {
		System.exit(0);
	}
	
	private void addMenuBar() {
		JMenuBar menuBar = new BMenuBar();
		
	}
}
