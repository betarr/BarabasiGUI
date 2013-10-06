package sk.sochuliak.barabasi.gui.mainscreen;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import sk.sochuliak.barabasi.controllers.ControllerService;
import sk.sochuliak.barabasi.gui.Strings;

public class BMenuBar extends JMenuBar {
	
	private static final long serialVersionUID = 1L;
	
	JMenuItem showDegreeDistributionMenuItem = null;
	JMenuItem showClusterDistributionMenuItem = null;
	
	public BMenuBar() {
		this.add(this.buildProgramMenu());
		this.add(this.buildAnalysisMenu());
	}

	private JMenu buildProgramMenu() {
		JMenu programMenu = new JMenu(Strings.MENU_PROGRAM);
		
		programMenu.add(this.buildJMenuItem(Strings.MENU_NEW_NETWORK, 
				KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK),
				new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						ControllerService.getAppController().showNewGraphDialog();
					}
				}));
		
		programMenu.addSeparator();
		
		programMenu.add(this.buildJMenuItem(Strings.MENU_CLOSE_PROGRAM,
				KeyStroke.getKeyStroke(KeyEvent.VK_Q, Event.CTRL_MASK),
				new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						ControllerService.getAppController().closeApplication();
					}
				}));
		

		return programMenu;
	}
	
	private JMenu buildAnalysisMenu() {
		JMenu analysisMenu = new JMenu(Strings.MENU_ANALYSIS);
		
		this.showDegreeDistributionMenuItem = this.buildJMenuItem(Strings.MENU_ANALYSIS_SHOW_DEGREE_DISTRIBUTION, 
				KeyStroke.getKeyStroke(KeyEvent.VK_D, Event.CTRL_MASK),
				new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						ControllerService.getAppController().showDegreeDistributionDialog();
					}
				});
		showDegreeDistributionMenuItem.setEnabled(false);
		analysisMenu.add(showDegreeDistributionMenuItem);
		
		this.showClusterDistributionMenuItem = this.buildJMenuItem(Strings.MENU_ANALYSIS_SHOW_CLUSTER_DISTRIBUTION,
				KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK),
				new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						ControllerService.getAppController().showClusterDistributionDialog();
					}
				});
		showClusterDistributionMenuItem.setEnabled(false);
		analysisMenu.add(showClusterDistributionMenuItem);
		
		
		return analysisMenu;
	}
	
	private JMenuItem buildJMenuItem(String label, KeyStroke keyStroke, ActionListener listener) {
		JMenuItem jMenuItem = new JMenuItem(label);
		jMenuItem.setAccelerator(keyStroke);
		jMenuItem.addActionListener(listener);
		return jMenuItem;
	}
	
	public void enableShowDegreeDistributionMenuItem(boolean enable) {
		this.showDegreeDistributionMenuItem.setEnabled(enable);
	}
	
	public void enableShowClusterDistributionMenuItem(boolean enable) {
		this.showClusterDistributionMenuItem.setEnabled(enable);
	}
}
