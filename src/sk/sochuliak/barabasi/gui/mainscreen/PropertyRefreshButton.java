package sk.sochuliak.barabasi.gui.mainscreen;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import sk.sochuliak.barabasi.controllers.ControllerService;
import sk.sochuliak.barabasi.gui.Strings;

public class PropertyRefreshButton extends JButton {

	private static final long serialVersionUID = 1L;
	
	
	public PropertyRefreshButton(final int propertyId) {
		this.setText(Strings.CALCULATE);
		
		this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Object source = e.getSource();
				
				if (source instanceof PropertyRefreshButton) {
					setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					ControllerService.getNetworkController().refreshNetworkProperty(propertyId);
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
			}
		});
	}
}
