package sk.sochuliak.barabasi.analysisframes;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sk.sochuliak.barabasi.gui.Strings;

public abstract class DistributionInfoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private boolean logScaleUsed = false;
	
	private JLabel startPointLabel = new JLabel(Strings.INFO_PANEL_START_POINT);
	private JLabel endPointLabel = new JLabel(Strings.INFO_PANEL_END_POINT);
	
	private JLabel startPointXLabel = new JLabel("X:");
	private JLabel startPointYLabel = new JLabel("Y:");
	
	private JLabel startPointXValueLabel = new JLabel(" ");
	private JLabel startPointYValueLabel = new JLabel(" ");
	
	private JLabel endPointXLabel = new JLabel("X:");
	private JLabel endPointYLabel = new JLabel("Y:");
	
	private JLabel endPointXValueLabel = new JLabel(" ");
	private JLabel endPointYValueLabel = new JLabel(" ");
	
	private JButton calculateButton = new JButton(Strings.INFO_PANEL_CALCULATE);
	
	private JLabel resultLabel = new JLabel(Strings.INFO_PANEL_RESULT);
	private JLabel resultValueLabel = new JLabel(" ");
	
	private DecimalFormat decimalFormat = new DecimalFormat("#0.00");
	
	private double[] startPoint = null;
	private double[] endPoint = null;
	
	private int pointSetterCounter = 0;
	

	public DistributionInfoPanel(boolean logScaleUsed) {
		this.logScaleUsed = logScaleUsed;
		
		this.setLayout(new GridBagLayout());
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		GridBagConstraints constrains = new GridBagConstraints();
		constrains.ipadx = 10;
		constrains.ipady = 10;
		
		constrains = this.setPropsToGridBagContraints(constrains, 0, 0, 3, 1);
		constrains.fill = GridBagConstraints.HORIZONTAL;
		this.add(this.startPointLabel, constrains);
		
		constrains = this.setPropsToGridBagContraints(constrains, 0, 1, 1, 1);
		this.add(this.startPointXLabel, constrains);
		
		constrains = this.setPropsToGridBagContraints(constrains, 1, 1, 2, 1);
		this.add(this.startPointXValueLabel, constrains);
		
		constrains = this.setPropsToGridBagContraints(constrains, 0, 2, 1, 1);
		this.add(this.startPointYLabel, constrains);
		
		constrains = this.setPropsToGridBagContraints(constrains, 1, 2, 2, 1);
		this.add(this.startPointYValueLabel, constrains);
		
		constrains = this.setPropsToGridBagContraints(constrains, 0, 3, 3, 1);
		this.add(new JLabel(""), constrains);
		
		constrains = this.setPropsToGridBagContraints(constrains, 0, 4, 3, 1);
		this.add(this.endPointLabel, constrains);
		
		constrains = this.setPropsToGridBagContraints(constrains, 0, 5, 1, 1);
		this.add(this.endPointXLabel, constrains);
		
		constrains = this.setPropsToGridBagContraints(constrains, 1, 5, 2, 1);
		this.add(this.endPointXValueLabel, constrains);
		
		constrains = this.setPropsToGridBagContraints(constrains, 0, 6, 1, 1);
		this.add(this.endPointYLabel, constrains);
		
		constrains = this.setPropsToGridBagContraints(constrains, 1, 6, 2, 1);
		this.add(this.endPointYValueLabel, constrains);
		
		constrains = this.setPropsToGridBagContraints(constrains, 0, 7, 3, 1);
		this.add(new JLabel(""), constrains);
		
		constrains = this.setPropsToGridBagContraints(constrains, 0, 8, 3, 1);
		this.add(this.calculateButton, constrains);
		
		constrains = this.setPropsToGridBagContraints(constrains, 0, 9, 3, 1);
		this.add(new JLabel(""), constrains);
		
		constrains = this.setPropsToGridBagContraints(constrains, 0, 10, 3, 1);
		this.add(this.resultLabel, constrains);
		
		constrains = this.setPropsToGridBagContraints(constrains, 0, 11, 3, 1);
		this.add(this.resultValueLabel, constrains);
		
		this.calculateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (startPoint != null && endPoint != null) {
					if (startPoint[0] < endPoint[0]) {
						onCalculateButtonClick(startPoint, endPoint);
					} else {
						System.err.println("X-ova suradnica zaciatocneho bodu musi byt mensia ako koncoveho bodu");
					}
				} else {
					System.err.println("Nebol zvoleny zaciatocny alebo koncovy bod!");
				}
			}
		});
	}
	
	public void setPoint(double x, double y) {
		if (this.pointSetterCounter == 0) {
			this.setStartPoint(x, y);
			this.pointSetterCounter++;
		} else {
			this.setEndPoint(x, y);
			this.pointSetterCounter = 0;
		}
	}
	
	private void setStartPoint(double x, double y) {
		this.startPoint = new double[]{x, y};
		String stringX = this.formatDouble(x);
		String stringY = this.formatDouble(y);
		this.startPointXValueLabel.setText(stringX);
		this.startPointYValueLabel.setText(stringY);
	}
	
	private void setEndPoint(double x, double y) {
		this.endPoint = new double[]{x, y};
		String stringX = this.formatDouble(x);
		String stringY = this.formatDouble(y);
		this.endPointXValueLabel.setText(stringX);
		this.endPointYValueLabel.setText(stringY);
	}
	
	public void setK(double k) {
		String stringK = this.formatDouble(k);
		this.resultValueLabel.setText(stringK);
	}
	
	private String formatDouble(double x) {
		return this.decimalFormat.format(x); 
	}
	
	private GridBagConstraints setPropsToGridBagContraints(
			GridBagConstraints constrains, int x, int y, int width, int height) {
		constrains.gridx = x;
		constrains.gridy = y;
		constrains.gridwidth = width;
		constrains.gridheight = height;
		return constrains;
	}
	
	public boolean isLogScaleUsed() {
		return logScaleUsed;
	}

	public abstract void onCalculateButtonClick(double[] startPoint, double[] endPoint);
}
