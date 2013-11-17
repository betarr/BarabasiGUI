package sk.sochuliak.barabasi.gui.mainscreen;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import sk.sochuliak.barabasi.controllers.ControllerService;
import sk.sochuliak.barabasi.gui.Strings;

public class BMenuBar extends JMenuBar {
	
	private static final long serialVersionUID = 1L;
	
	public static List<JMenuItem> menuItemsEnabledOnGraphBuilded = new ArrayList<JMenuItem>();
	
	JMenuItem exportMenuItem = null;
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
		
		programMenu.add(this.buildJMenuItem(Strings.MENU_IMPORT,
				KeyStroke.getKeyStroke(KeyEvent.VK_I, Event.CTRL_MASK),
				new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						ControllerService.getAppController().importGraph();
					}
				}));
		
		this.exportMenuItem = this.buildJMenuItem(Strings.MENU_EXPORT,
				KeyStroke.getKeyStroke(KeyEvent.VK_E, Event.CTRL_MASK),
				new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						ControllerService.getAppController().exportGraph();
					}
				});
		this.exportMenuItem.setEnabled(false);
		BMenuBar.registerMenuItemEnabledOnGraphBuilded(this.exportMenuItem);
		programMenu.add(this.exportMenuItem);
		
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
						if (!ControllerService.getAppController().isDegreeDistributionShowed()) { 
							ControllerService.getAppController().showDegreeDistributionDialog();
							ControllerService.getAppController().setDegreeDistributionShowed(true);
						}
					}
				});
		this.showDegreeDistributionMenuItem.setEnabled(false);
		BMenuBar.registerMenuItemEnabledOnGraphBuilded(this.showDegreeDistributionMenuItem);
		analysisMenu.add(showDegreeDistributionMenuItem);
		
		this.showClusterDistributionMenuItem = this.buildJMenuItem(Strings.MENU_ANALYSIS_SHOW_CLUSTER_DISTRIBUTION,
				KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK),
				new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (!ControllerService.getAppController().isClusterDistributionShowed()) {
							ControllerService.getAppController().showClusterDistributionDialog();
							ControllerService.getAppController().setClusterDistributionShowed(true);
						}
					}
				});
		this.showClusterDistributionMenuItem.setEnabled(false);
		BMenuBar.registerMenuItemEnabledOnGraphBuilded(this.showClusterDistributionMenuItem);
		analysisMenu.add(showClusterDistributionMenuItem);
		
		
		return analysisMenu;
	}
	
	private JMenuItem buildJMenuItem(String label, KeyStroke keyStroke, ActionListener listener) {
		JMenuItem jMenuItem = new JMenuItem(label);
		jMenuItem.setAccelerator(keyStroke);
		jMenuItem.addActionListener(listener);
		return jMenuItem;
	}
	
	public static void registerMenuItemEnabledOnGraphBuilded(JMenuItem jMenuItem) {
		BMenuBar.menuItemsEnabledOnGraphBuilded.add(jMenuItem);
	}
	
	public static void onGraphBuilded() {
		for (JMenuItem menuItem : BMenuBar.menuItemsEnabledOnGraphBuilded) {
			menuItem.setEnabled(true);
		}
	}
}
