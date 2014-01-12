package sk.sochuliak.barabasi.gui.newnetworkdialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import sk.sochuliak.barabasi.gui.MainGuiConfiguration;
import sk.sochuliak.barabasi.gui.Strings;
import sk.sochuliak.barabasi.network.NetworkBuildConfiguration;
import sk.sochuliak.barabasi.network.NetworkBuilder;

public class NewNetworkProgressBar extends JDialog implements PropertyChangeListener {

	private static final long serialVersionUID = 1L;
	
	JProgressBar progressBar = null;
	NetworkBuilder networkBuilder = null;
	
	public NewNetworkProgressBar(Component owner, NetworkBuildConfiguration config) {
		this.setTitle(Strings.NEW_NETWORK_PROGRESS_BAR_TITLE);
		this.setSize(MainGuiConfiguration.NEW_NETWORK_PROGRESS_BAR_DIALOG_SIZE);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setLocationRelativeTo(owner);
		
		this.setLayout(new BorderLayout());
		
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new FlowLayout());
		labelPanel.add(new JLabel(Strings.NEW_NETWORK_PROGRESS_BAR_LABEL));
		this.add(labelPanel, BorderLayout.NORTH);
		
		this.progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		this.add(progressBar, BorderLayout.CENTER);
		
		this.networkBuilder = new NetworkBuilder(config);
		this.networkBuilder.addPropertyChangeListener(this);
		this.networkBuilder.execute();
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress".equals(evt.getPropertyName())) {
			int progress = (Integer) evt.getNewValue();
			this.progressBar.setValue(progress);
		}
	}

}
