package sk.sochuliak.barabasi.gui.mainscreen;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import sk.sochuliak.barabasi.gui.Strings;

public class BasicPropertiesTable extends JTable {

	private static final long serialVersionUID = 1L;
	
	public static final int TOTAL_NODES_COUNT = 0;
	public static final int AVERAGE_NODE_DEGREE = 1;
	public static final int AVERAGE_CLUSTER_RATIO = 2;
	public static final int AVERAGE_DISTANCE = 3;
	public static final int NUMBER_OF_NEIGHBORING_NODES = 4;
	
	
	public BasicPropertiesTable() {
		this.setModel(new BasicPropertiesTableModel());
	}
	
	class BasicPropertiesTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		
		private final String[] columnNames = new String[] {Strings.BASIC_PROPERTIES_PROPERTY_NAME, Strings.BASIC_PROPERTIES_PROPERTY_VALUE};
		private Object[][] data = new Object[][]{
				{Strings.BASIC_PROPERTIES_TOTAL_NODES_COUNT, ""},
				{Strings.BASIC_PROPERTIES_AVERAGE_NODE_DEGREE, ""}, 
				{Strings.BASIC_PROPERTIES_AVERAGE_NODE_CLUSTER, ""},
				{Strings.BASIC_PROPERTIES_AVERAGE_DISTANCE, ""},
				{Strings.BASIC_PROPERTIES_NUMBER_OF_NEIGHBORING_NODES, ""}
		};
		
		
		@Override
		public String getColumnName(int column) {
			return columnNames[column];
		}

		@Override
		public int getColumnCount() {
			return this.columnNames.length;
		}

		@Override
		public int getRowCount() {
			return this.data.length;
		}

		@Override
		public Object getValueAt(int row, int column) {
			return data[row][column];
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}

		@Override
		public void setValueAt(Object value, int row, int column) {
			this.data[row][column] = value;
		}
	}
	
	public void setValue(int property, Object value) {
		this.getModel().setValueAt(value, property, 1);
		this.repaint();
	}

	public void clearValues() {
		this.getModel().setValueAt("", BasicPropertiesTable.TOTAL_NODES_COUNT, 1);
		this.getModel().setValueAt("", BasicPropertiesTable.AVERAGE_NODE_DEGREE, 1);
		this.getModel().setValueAt("", BasicPropertiesTable.AVERAGE_CLUSTER_RATIO, 1);
		this.getModel().setValueAt("", BasicPropertiesTable.AVERAGE_DISTANCE, 1);
		this.getModel().setValueAt("", BasicPropertiesTable.NUMBER_OF_NEIGHBORING_NODES, 1);
		this.repaint();
	}

}
