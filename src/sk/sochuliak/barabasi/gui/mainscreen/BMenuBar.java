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
import sk.sochuliak.barabasi.utils.GuiUtils;

public class BMenuBar extends JMenuBar {
	
	private static final long serialVersionUID = 1L;
	
	public static List<JMenuItem> menuItemsEnabledOnGraphBuilded = new ArrayList<JMenuItem>();
	
	JMenuItem exportMenuItem = null;
	JMenuItem showDegreeDistributionMenuItem = null;
	JMenuItem showDegreeDistributionLogMenuItem = null;
	JMenuItem showClusterDistributionMenuItem = null;
	JMenuItem showClusterDistributionLogMenuItem = null;
	
	public BMenuBar() {
		this.add(this.buildProgramMenu());
		this.add(this.buildAnalysisMenu());
	}

	private JMenu buildProgramMenu() {
		JMenu programMenu = new JMenu(Strings.MENU_PROGRAM);
		
		programMenu.add(GuiUtils.buildJMenuItem(Strings.MENU_NEW_NETWORK, 
				KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK),
				new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						ControllerService.getAppController().showNewGraphDialog();
					}
				}));
		
		programMenu.addSeparator();
		
		programMenu.add(GuiUtils.buildJMenuItem(Strings.MENU_IMPORT,
				KeyStroke.getKeyStroke(KeyEvent.VK_I, Event.CTRL_MASK),
				new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						ControllerService.getAppController().importGraph();
					}
				}));
		
		this.exportMenuItem = GuiUtils.buildJMenuItem(Strings.MENU_EXPORT,
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
		
		programMenu.add(GuiUtils.buildJMenuItem(Strings.MENU_CLOSE_PROGRAM,
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
		
		this.showDegreeDistributionMenuItem = GuiUtils.buildJMenuItem(Strings.MENU_ANALYSIS_SHOW_DEGREE_DISTRIBUTION, 
				KeyStroke.getKeyStroke(KeyEvent.VK_D, Event.CTRL_MASK),
				new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (!ControllerService.getAppController().isDegreeDistributionShowed()) { 
							ControllerService.getAppController().showDegreeDistributionDialog(false);
							ControllerService.getAppController().setDegreeDistributionShowed(true);
						}
					}
				});
		this.showDegreeDistributionMenuItem.setEnabled(false);
		BMenuBar.registerMenuItemEnabledOnGraphBuilded(this.showDegreeDistributionMenuItem);
		analysisMenu.add(this.showDegreeDistributionMenuItem);
		
		this.showDegreeDistributionLogMenuItem = GuiUtils.buildJMenuItem(Strings.MENU_ANALYSIS_SHOW_DEGREE_DISTRIBUTION_LOG,
				KeyStroke.getKeyStroke(KeyEvent.VK_D, Event.ALT_MASK),
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (!ControllerService.getAppController().isDegreeDistributionLogShowed()) { 
							ControllerService.getAppController().showDegreeDistributionDialog(true);
							ControllerService.getAppController().setDegreeDistributionLogShowed(true);
						}
					}
			
				});
		this.showDegreeDistributionLogMenuItem.setEnabled(false);
		BMenuBar.registerMenuItemEnabledOnGraphBuilded(this.showDegreeDistributionLogMenuItem);
		analysisMenu.add(this.showDegreeDistributionLogMenuItem);
		
		this.showClusterDistributionMenuItem = GuiUtils.buildJMenuItem(Strings.MENU_ANALYSIS_SHOW_CLUSTER_DISTRIBUTION,
				KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK),
				new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (!ControllerService.getAppController().isClusterDistributionShowed()) {
							ControllerService.getAppController().showClusterDistributionDialog(false);
							ControllerService.getAppController().setClusterDistributionShowed(true);
						}
					}
				});
		this.showClusterDistributionMenuItem.setEnabled(false);
		BMenuBar.registerMenuItemEnabledOnGraphBuilded(this.showClusterDistributionMenuItem);
		analysisMenu.add(this.showClusterDistributionMenuItem);
		
		this.showClusterDistributionLogMenuItem = GuiUtils.buildJMenuItem(Strings.MENU_ANALYSIS_SHOW_CLUSTER_DISTRIBUTION_LOG,
				KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.ALT_MASK),
				new ActionListener() {
			
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (!ControllerService.getAppController().isClusterDistributionLogShowed()) {
							ControllerService.getAppController().showClusterDistributionDialog(true);
							ControllerService.getAppController().setClusterDistributionLogShowed(true);
						}
					}
				});
		this.showClusterDistributionLogMenuItem.setEnabled(false);
		BMenuBar.registerMenuItemEnabledOnGraphBuilded(this.showClusterDistributionLogMenuItem);
		analysisMenu.add(this.showClusterDistributionLogMenuItem);
		return analysisMenu;
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
