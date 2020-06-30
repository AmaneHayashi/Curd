package frame.app.contents;

import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import common.support.FrameSupport;
import common.support.SQLSupport;
import common.support.UtilSupport;
import frame.app.vars.CSJ;
import frame.origin.contents.F2;
import frame.origin.contents.F2P0;
import my.comps.MyTable;

public class SJ implements CSJ.CSJ_C {

	SJF2 jf;
	String table_name;

	public SJ(String table_name) {
		this.table_name = table_name.substring(table_name.indexOf("_") + 1);
	}

	public JFrame run() {
		jf = new SJ(table_name).new SJF2();
		return jf;
	}

	public class SJF2 extends F2 {

		private static final long serialVersionUID = -316263345420212959L;

		@Override
		public void init() {
			jbs = F2jbs;
			rfstr = F2rfstr;
			shN = table_name;
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(jb[0]))
				this.setExtendedState(JFrame.ICONIFIED);
			else if (e.getSource().equals(jb[1])) {
				this.dispose();
			} else if (e.getSource().equals(jb[2])) {
				MyTable mtb = ((SJF2P0) jp.getComponent(0)).getMtb();
				if (mtb.getDtm().isEachCellFilled()) {
					SQLSupport.sqlRenewTable(table_name, mtb);
					this.dispose();
				} else {
					JOptionPane.showMessageDialog(null, "您的信息表尚未完成，不能退出！");
				}
			}
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
			dtm.addTableModelListener((l) -> jl3[0].setText(F2P0jl3s[0] + (dtm.getRowCount())));
			SQLSupport.sqlFillTable(table_name, mtb, "order by 1");
			jt.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			dtm.setColumnFilter(new int[] { 0 });
			dtm.setRowFilter(dtm.getRowCount());
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			super.actionPerformed(e);
			if (e.getSource().equals(jb[0])) {
				FrameSupport.setLabel(dtm, table_name);
			} else if (e.getSource().equals(jb[1])) {
				dtm.setRowFilter(0);
			} else if (e.getSource().equals(jb[2])) {
				FrameSupport.refreshLabel(dtm, table_name, "000");
			}
		}
	}
}
