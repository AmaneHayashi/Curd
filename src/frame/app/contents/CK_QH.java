package frame.app.contents;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import common.support.FrameSupport;
import common.support.UtilSupport;
import common.support.SQLSupport;
import frame.app.vars.CSE;
import frame.origin.contents.F1;
import frame.origin.contents.F1P0;
import my.listeners.MyWindow;

public class CK_QH implements CSE.QH {

	CKF1 jf;
	String table_name;
	String table_label;
	String fcolN[] = new String[] { "药品数量" };

	public CK_QH(String table_name) {
		this.table_name = table_name;
		table_label = table_name.substring(2);
	}

	public JFrame run() {
		jf = new CK_QH(table_name).new CKF1();
		return jf;
	}

	public class CKF1 extends F1 {

		private static final long serialVersionUID = -1795814280706802080L;

		@Override
		public void init() {
			jbs = F1jbs;
			rfstr = F1rfstr;
			tbN = table_name;
		}
	}

	public class CKF1P0 extends F1P0 {

		private static final long serialVersionUID = 5852442571152476857L;

		@Override
		public void init() {
			jl1s = F1P0jl1s;
			mt1s = F1P0mt1s;
			mt2s = F1P0mt2s;
			dmcs = F1P0dmcs;
			dmcv = new String[][] { SQLSupport.sqlFillDmc("GYS", "1", "1", "", new int[] { 0, 1 }),
					SQLSupport.sqlFillDmc("CK", "1", "1", "", new int[] { 0, 1 }),
					SQLSupport.sqlFillDmc("YG", "职务", "销售员", "", new int[] { 0, 1 }) };
			jb1s = F1P0jb1s;
			jb2s = F1P0jb2s;
			jb3s = F1P0jb3s;
			colN = UtilSupport.concatAll(new String[] { "序号" }, SQLSupport.sqlGetColN("YP"), fcolN);
		}

		@Override
		public void H() {
			super.H();
			// 增加其它项目
			JComboBox<Object> dmc1 = dmc[1].getDmc();
			// 写单号
			FrameSupport.setLabel(jl2, table_name, table_name, dmc1.getSelectedItem().toString().trim());
			// 修改日期
			mt1[0].setEnabled(false);
			mt1[0].getTextField().setText(new SimpleDateFormat("YYYY-MM-dd").format(new Date()));
			// 修改单号
			dmc1.addItemListener((l) -> FrameSupport.updateLabel(jl2, dmc1.getSelectedItem().toString().trim()));

			dtm.setRowFilter(dtm.getRowCount());
			dtm.setColumnFilter(new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 10 });
			dtm.addTableModelListener(new TableModelListener() {
				@Override
				public void tableChanged(TableModelEvent e) {
					int row = e.getFirstRow();
					int col = e.getColumn();
					if (row == dtm.getRowCount() - 1 && col == -1) {
						dtm.setValueAt(dtm.getRowCount(), row, 0);
						dtm.setValueAt(SJ_X.selected_label, row, 1);
					}
				}
			});
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// 实现老方法
			// super.actionPerformed(e);
			// 增加新方法
			if (e.getSource().equals(jb1[0])) {
				SJ_X sjx = new SJ_X("YP");
				sjx.run().addWindowListener(new MyWindow() {
					@Override
					public void windowClosed(WindowEvent e) {
						String sl = SJ_X.selected_label;
						// 已经存在了
						if (UtilSupport.filterOrder(dtm.getColumnValue(1), (s) -> s.equals(sl)) != -1) {
							JOptionPane.showMessageDialog(null, "该药品已添加，请于药品表中查看！");
						} else {
							String[] sValue = SQLSupport.sqlGetValue("YP", "药品编号", sl, 30, "");
							dtm.addRow(UtilSupport.concatAll(new String[1], sValue, new String[2]));
						}
					}
				});
			}
			// 保存
			else if (e.getSource().equals(jb3[0]) && dtm.isReadyToSave()) {
				// 序列号
				String s0 = String.valueOf(SQLSupport.sqlGetLabel(table_name));
				// 单号
				String s1 = UtilSupport.subAfter(jl2.getText().toString().trim(), "：");
				// 客户编号
				String s2 = UtilSupport.subBefore(dmc[0].getDmc().getSelectedItem().toString().trim(), "(");
				// 仓库编号
				String s3 = UtilSupport.subBefore(dmc[1].getDmc().getSelectedItem().toString().trim(), "(");
				// 经办人
				String s4 = UtilSupport.subBefore(dmc[2].getDmc().getSelectedItem().toString().trim(), "(");
				// mt1--金额信息012+订单日期3,mt2--备注
				SQLSupport.sqlInsert(table_name, new String[] { s0, s1, s2, s3, s4, mt1[0].S(), mt2.S() });
				// 单号--s1,药品编号1,单价8,数量9
				for (int i = 0; i < dtm.getRowCount(); i++) {
					String[] rowv = dtm.getRowValue(i);
					SQLSupport.sqlInsert(table_name + "_X", new String[] { s1, rowv[1], rowv[9] });
				}
				JOptionPane.showMessageDialog(null, "提交单据成功！");
				JOptionPane.getFrameForComponent(this).dispose();
			}
			// 删除
			else if (e.getSource().equals(jb2[0])) {
				int selectRow = mtb.getJt().getSelectedRow();
				if (selectRow >= 0) {
					dtm.removeRow(selectRow);
				}
			} else if (e.getSource().equals(jb3[1])) {
				JOptionPane.getFrameForComponent(this).dispose();
			}
		}
	}
}
