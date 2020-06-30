package my.comps;

import java.util.stream.IntStream;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import common.support.UtilSupport;

public class MyDtm extends DefaultTableModel {

	private static final long serialVersionUID = -6284913808702139115L;
	public static final int[] ALL_PROHIBITED = new int[] { -1 };

	int columnFilter[], rowFilter;

	public MyDtm(Object[] rowData, int columnFilter[], int rowFilter) {
		super(rowData, 0);
		this.columnFilter = columnFilter;
		this.rowFilter = rowFilter;
	}

	public boolean isCellEditable(int row, int column) {
		if (getColumnFilter() == ALL_PROHIBITED) {
			return false;
		} else {
			if (IntStream.of(getColumnFilter()).anyMatch(x -> x == column))
				return false;
			else if (row < rowFilter)
				return false;
			else
				return true;
		}
	}

	public boolean isEachCellFilled() {
		for (int i = 0; i < getRowCount(); i++) {
			for (int j = 0; j < getColumnCount(); j++) {
				try {
					getValueAt(i, j).toString();
				} catch (NullPointerException e) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean isReadyToSave() {
		try {
			isEachCellFilled();
			getValueAt(0, 0).toString();
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "保存失败，请检查信息是否填写完整。");
			return false;
		}
	}

	public void removeAll() {
		if (getRowCount() != 0) {
			removeRow(0);
			removeAll();
		}
	}

	public String[] getColumnValue(int columnIndex) {
		String[] str = new String[getRowCount()];
		for (int i = 0; i < str.length; i++) {
			str[i] = getValueAt(i, columnIndex).toString().trim();
		}
		return str;
	}

	public void setColumnValue(int columnIndex, String[] sValue) {
		for (int i = 0; i < getRowCount(); i++) {
			setValueAt(sValue[i], i, columnIndex);
		}
	}

	public String[] getRowValue(int rowIndex) {
		String[] str = new String[getColumnCount()];
		for (int i = 0; i < str.length; i++) {
			str[i] = getValueAt(rowIndex, i).toString().trim();
		}
		return str;
	}

	public void setRowValue(int rowIndex, String[] sValue) {
		for (int i = 0; i < getColumnCount(); i++) {
			setValueAt(sValue[i], rowIndex, i);
		}
	}

	public boolean addDual(Object sValues) {
		if (sValues instanceof String[][]) {
			String[][] ssValue = (String[][]) sValues;
			for (int i = 0; i < ssValue.length; i++) {
				addRow(ssValue[i]);
			}
			return true;
		} else if (sValues instanceof String[]) {
			String[] sValue = (String[]) sValues;
			addRow(sValue);
			return true;
		} else {
			return false;
		}
	}

	public int[] getColumnFilter() {
		return columnFilter;
	}

	public void setColumnFilter(int[] columnFilter) {
		this.columnFilter = columnFilter;
	}

	public int getRowFilter() {
		return rowFilter;
	}

	public void setRowFilter(int rowFilter) {
		this.rowFilter = rowFilter;
	}

	public String getSum(int columnIndex, int floatScale) {
		float f = 0;
		for (int i = 0; i < getRowCount(); i++) {
			try {
				f += Float.parseFloat(getValueAt(i, columnIndex).toString().trim());
			} catch (NumberFormatException e) {
				System.out.println("该列不可加！");
				e.printStackTrace();
			} catch (NullPointerException e1) {
			}
		}
		return UtilSupport.scaleF(f, floatScale);
	}
}
