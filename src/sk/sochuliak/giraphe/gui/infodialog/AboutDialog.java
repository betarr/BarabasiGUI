package sk.sochuliak.giraphe.gui.infodialog;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JTextArea;

import sk.sochuliak.giraphe.gui.MainGuiConfiguration;

public class AboutDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	public AboutDialog(Component owner, String title, String text) {
		this.setSize(MainGuiConfiguration.INFO_DIALOG_SIZE);
		this.setTitle(title);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setFont(new Font("Verdana", Font.PLAIN, 12));
		textArea.setText(text);
		this.add(textArea);
		
		this.setLocationRelativeTo(owner);
	}
	
	public static void showDialog(Component owner, String title, String text) {
		AboutDialog dialog = new AboutDialog(owner, title, text);
		dialog.setVisible(true);
	}

}
