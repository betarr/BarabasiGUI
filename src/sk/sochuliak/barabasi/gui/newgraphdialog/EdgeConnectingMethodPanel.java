package sk.sochuliak.barabasi.gui.newgraphdialog;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import sk.sochuliak.barabasi.network.EdgeConnectingMethodRowConfig;

public class EdgeConnectingMethodPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private List<EdgeConnectingMethodRow> edgeConnectingMethodRows = new ArrayList<EdgeConnectingMethodRow>();
	
	public EdgeConnectingMethodPanel() {
		super();
		new JScrollPane(this);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	public void addItem(EdgeConnectingMethodRow edgeConnectingMethodRow) {
		this.edgeConnectingMethodRows.add(edgeConnectingMethodRow);
		this.add(edgeConnectingMethodRow);
		revalidate();
	}
	
	public void removeItem(EdgeConnectingMethodRow edgeConnectingMethodRow) {
		this.edgeConnectingMethodRows.remove(edgeConnectingMethodRow);
		this.remove(edgeConnectingMethodRow);
		revalidate();
	}
	
	public List<EdgeConnectingMethodRowConfig> getConfig() throws NumberFormatException {
		List<EdgeConnectingMethodRowConfig> config = new ArrayList<EdgeConnectingMethodRowConfig>();
		for (EdgeConnectingMethodRow row : this.edgeConnectingMethodRows) {
			config.add(row.getConfig());
		}
		return config;
	}

}
