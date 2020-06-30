package frame.app.contents;

import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import common.support.SQLSupport;
import common.support.UtilSupport;
import frame.app.vars.CSJ;
import frame.origin.contents.F2;
import frame.origin.contents.F2P0;
import my.comps.MyDtm;

public class SJ_X implements CSJ.CSJ_X {

	SJF2 jf;
	String table_name;
	public static String selected_label;

	public SJ_X(String table_name) {
		this.table_name = table_name;
	}

	public JFrame run() {
		jf = new SJ_X(table_name).new SJF2();
		return jf;
	}

	public class SJF2 extends F2 {

		private static final long serialVersionUID = -316263345420212959L;

		@Override
		public void init() {
			jbs = F2jmbs;
			rfstr = F2rfstr;
			shN = table_name;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(jb[0]))
				this.setExtendedState(JFrame.ICONIFIED);
			else if (e.getSource().equals(jb[1]))
				this.setVisible(false);
		}
	}

	public class SJF2P0 extends F2P0 {

		private static final long serialVersionUID = -3310532987156997101L;

		@Override
		public void init() {
			jbs = F2P0jbs;
			jl1s = String.format(F2P0jl1s,
					MENUf[4][UtilSupport.filterOrder(CURDf[4], (str) -> str.equals(table_name))]);
			jl3s = F2P0jl3s;
			colN = SQLSupport.sqlGetColN(table_name);
		}

		@Override
		public void H() {
			super.H();
			SQLSupport.sqlFillTable(table_name, mtb, "");
			dtm.addTableModelListener((l) -> jl3[0].setText(F2P0jl3s[0] + (dtm.getRowCount())));
			jt.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			dtm.setColumnFilter(MyDtm.ALL_PROHIBITED);
			dtm.setRowFilter(dtm.getRowCount());
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(jb[0])) {
				if (jt.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "«Î—°‘Ò“ªœÓ£°");
				} else {
					selected_label = dtm.getValueAt(jt.getSelectedRow(), 0).toString().trim();
					JOptionPane.getFrameForComponent(this).dispose();
				}
			}
		}
	}
}
