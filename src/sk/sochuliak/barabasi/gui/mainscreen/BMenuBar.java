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
						ControllerService.getAppController().showNewNetworkDialog();
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
						ControllerService.getAppController().showExportGraph();
					}
				});
		this.exportMenuItem.setEnabled(false);
		BMenuBar.registerMenuItemEnabledWhenNetworkExists(this.exportMenuItem);
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
						ControllerService.getAppController().showDegreeDistributionDialog(false);
					}
				});
		this.showDegreeDistributionMenuItem.setEnabled(false);
		BMenuBar.registerMenuItemEnabledWhenNetworkExists(this.showDegreeDistributionMenuItem);
		analysisMenu.add(this.showDegreeDistributionMenuItem);
		
		this.showDegreeDistributionLogMenuItem = GuiUtils.buildJMenuItem(Strings.MENU_ANALYSIS_SHOW_DEGREE_DISTRIBUTION_LOG,
				KeyStroke.getKeyStroke(KeyEvent.VK_D, Event.ALT_MASK),
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						ControllerService.getAppController().showDegreeDistributionDialog(true);
					}
			
				});
		this.showDegreeDistributionLogMenuItem.setEnabled(false);
		BMenuBar.registerMenuItemEnabledWhenNetworkExists(this.showDegreeDistributionLogMenuItem);
		analysisMenu.add(this.showDegreeDistributionLogMenuItem);
		
		this.showClusterDistributionMenuItem = GuiUtils.buildJMenuItem(Strings.MENU_ANALYSIS_SHOW_CLUSTER_DISTRIBUTION,
				KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK),
				new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						ControllerService.getAppController().showClusterDistributionDialog(false);
					}
				});
		this.showClusterDistributionMenuItem.setEnabled(false);
		BMenuBar.registerMenuItemEnabledWhenNetworkExists(this.showClusterDistributionMenuItem);
		analysisMenu.add(this.showClusterDistributionMenuItem);
		
		this.showClusterDistributionLogMenuItem = GuiUtils.buildJMenuItem(Strings.MENU_ANALYSIS_SHOW_CLUSTER_DISTRIBUTION_LOG,
				KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.ALT_MASK),
				new ActionListener() {
			
					@Override
					public void actionPerformed(ActionEvent arg0) {
						ControllerService.getAppController().showClusterDistributionDialog(true);
					}
				});
		this.showClusterDistributionLogMenuItem.setEnabled(false);
		BMenuBar.registerMenuItemEnabledWhenNetworkExists(this.showClusterDistributionLogMenuItem);
		analysisMenu.add(this.showClusterDistributionLogMenuItem);
		return analysisMenu;
	}
	
	public static void registerMenuItemEnabledWhenNetworkExists(JMenuItem jMenuItem) {
		BMenuBar.menuItemsEnabledOnGraphBuilded.add(jMenuItem);
	}
	
	public static void onNetworkExists() {
		for (JMenuItem menuItem : BMenuBar.menuItemsEnabledOnGraphBuilded) {
			menuItem.setEnabled(true);
		}
	}
	
	public static void onNetworkDoesNotExist() {
		for (JMenuItem menuItem : BMenuBar.menuItemsEnabledOnGraphBuilded) {
			menuItem.setEnabled(false);
		}
	}
}
