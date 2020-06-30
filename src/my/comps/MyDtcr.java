package my.comps;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MyDtcr extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 6333249054691623516L;

	public Color color[] = new Color[2];

	public MyDtcr(Color colorDeep, Color colorShal) {
		this.color[0] = colorDeep;
		this.color[1] = colorShal;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (row % 2 == 0)
			setBackground(color[0]);
		else if (row % 2 == 1)
			setBackground(color[1]);
		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}
}
