package frame.app.contents;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
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
import frame.app.vars.CRE;
import frame.origin.contents.F1;
import frame.origin.contents.F1P0;
import frame.origin.contents.F1P1;
import frame.origin.contents.F2;
import frame.origin.contents.F2P0;
import my.comps.MyButton;
import my.comps.MyComboBox;
import my.comps.MyDtm;

public class XS_FH implements CRE.FH {

	XSF1 jf;
	String table_name;
	String table_label;
	String fcolN[] = new String[] { "药品数量", "药品金额" };

	public XS_FH(String table_name) {
		this.table_name = table_name;
		table_label = table_name.substring(2);
	}

	@Override
	public JFrame run() {
		jf = new XS_FH(table_name).new XSF1();
		return jf;
	}

	public class XSF1 extends F1 {

		private static final long serialVersionUID = -1795814280706802080L;

		@Override
		public void init() {
			jbs = F1jbs;
			rfstr = F1rfstr;
			tbN = table_name;
		}
	}

	public class XSF1P0 extends F1P0 {

		private static final long serialVersionUID = 5852442571152476857L;

		@Override
		public void init() {
			jl1s = F1P0jl1s;
			mt1s = F1P0mt1s;
			mt2s = F1P0mt2s;
			dmcs = F1P0dmcs;
			String moreCond = String.format("and 关联状态 = '%s'", ASSOCIATING);
			dmcv = new String[][] { SQLSupport.sqlFillDmc("XS_DH", "审核状态", VERIFIED, moreCond, new int[] { 1, 2, 3 }),
					SQLSupport.sqlFillDmc("KH", "1", "1", "", new int[] { 0, 1 }),
					SQLSupport.sqlFillDmc("YG", "职务", "库管员", "", new int[] { 0, 1 }) };
			jb1s = F1P0jb1s;
			jb3s = F1P0jb3s;
			colN = UtilSupport.concatAll(new String[] { "序号" }, SQLSupport.sqlGetColN("YP"), fcolN);
		}

		@Override
		public void H() {
			super.H();
			// 增加其它项目
			JComboBox<Object> dmc0 = dmc[0].getDmc();
			JComboBox<Object> dmc1 = dmc[1].getDmc();
			// 修改日期
			mt1[3].setEnabled(false);
			mt1[3].getTextField().setText(new SimpleDateFormat("YYYY-MM-dd").format(new Date()));
			// 更新单据（新）
			dmc0.addItemListener((l) -> updateDmc(dmc, l.getItem().toString().trim()));
			// 写单号
			FrameSupport.setLabel(jl2, table_name, table_name, dmc1.getSelectedItem().toString().trim());// 铜钱
			// 修改单号
			dmc1.addItemListener((l) -> FrameSupport.updateLabel(jl2, dmc1.getSelectedItem().toString().trim()));// 铜钱

			dtm.setRowFilter(dtm.getRowCount());// 铜钱
			dtm.setColumnFilter(MyDtm.ALL_PROHIBITED);// 铜钱
			dtm.addTableModelListener(new TableModelListener() {
				@Override
				public void tableChanged(TableModelEvent e) {
					int row = e.getFirstRow();
					int col = e.getColumn();
					if (row == dtm.getRowCount() - 1 && col == -1) {
						dtm.setValueAt(dtm.getRowCount(), row, 0);
					}
				}
			});
			// 初始化数据
			dmc0.setSelectedIndex(0);
			try {
				updateDmc(dmc, dmc0.getItemAt(0).toString().trim());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "当前没有可以关联的销售单，请联系销售部门。");
			}
			dmc1.setEnabled(false);
		}

		public void updateDmc(MyComboBox[] dmc, String updItem) {
			dtm.removeAll();
			String id = UtilSupport.subBefore(updItem, "(");
			String client = UtilSupport.substringBetween(updItem, "(", ",");
			// 设置dmc1（客户与单号的客户对应）
			JComboBox<Object> dmc1 = dmc[1].getDmc();
			dmc1.setSelectedIndex(UtilSupport.filterOrder(dmc[1].getsValue(), (s) -> s.contains(client)));
			// 填充1~8列（药品属性列）
			Object obj = SQLSupport.sqlGetMultiValue("YP", "XS_DH_X", "药品编号", "单号", id);
			if (obj instanceof String[]) {
				dtm.addRow(UtilSupport.concatAll(new String[1], (String[]) obj));
			} else if (obj instanceof String[][]) {
				for (int i = 0; i < ((String[][]) obj).length; i++) {
					dtm.addRow(UtilSupport.concatAll(new String[1], ((String[][]) obj)[i]));
				}
			}
			// 填充9~10列（金额列）
			for (int i = 0; i < dtm.getRowCount(); i++) {
				String moreCond = String.format("and %s = '%s'", "药品编号", dtm.getValueAt(i, 1).toString().trim());
				String[] sValue = SQLSupport.sqlGetValue("XS_DH_X", "单号", id,
						new String[] { mtb.getColN()[9], mtb.getColN()[10] }, moreCond);
				dtm.setValueAt(sValue[0], i, 9);
				dtm.setValueAt(sValue[1], i, 10);
			}
			// 填充金额区域
			String[] $ = SQLSupport.sqlGetValue("XS_DH", "单号", id, new String[] { "应收金额", "实收金额", "待收金额" }, "");
			for (int i = 0; i < mt1s.length - 1; i++) {
				mt1[i].getTextField().setText($[i]);
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// 增加新方法
			if (e.getSource().equals(jb1[0])) {
			}
			// 保存
			else if (e.getSource().equals(jb3[0]) && dtm.isReadyToSave()) {
				// 序列号
				String s0 = String.valueOf(SQLSupport.sqlGetLabel(table_name));
				// 单号
				String s1 = UtilSupport.subAfter(jl2.getText().toString().trim(), "：");
				// 关联单号
				String s2 = UtilSupport.subBefore(dmc[0].getDmc().getSelectedItem().toString().trim(), "(");
				// 仓库编号
				String s3 = UtilSupport.subBefore(dmc[1].getDmc().getSelectedItem().toString().trim(), "(");
				// 经办人
				String s4 = UtilSupport.subBefore(dmc[2].getDmc().getSelectedItem().toString().trim(), "(");
				// mt1--金额信息012+订单日期3,mt2--备注
				SQLSupport.sqlInsert(table_name, new String[] { s0, s1, s2, s3, s4, mt1[3].S(), mt2.S(), VERIFING });
				SQLSupport.sqlUpdate("XS_DH", "关联状态", ASSOCIATED, "单号", s2, "");
				// 单号--s1,药品编号1,单价8,数量9,金额10
				JOptionPane.showMessageDialog(null, "提交单据成功！");
				JOptionPane.getFrameForComponent(this).dispose();
			} else if (e.getSource().equals(jb3[1])) {
				JOptionPane.getFrameForComponent(this).dispose();
			}
		}
	}

	public class XSF1P1 extends F1P1 {

		private static final long serialVersionUID = 5852442571152476857L;

		@Override
		public void init() {
			jls = F1P1jls;
			mts = F1P1mts;
			jtas = F1P1jtas;
			jb1s = F1P1jb1s;
			jb2s = F1P1jb2s;
			colN = SQLSupport.sqlGetColN(table_name);
			rfstr = F1P1rfstr;
			tbN = table_name;
		}

		@Override
		public void H() {
			super.H();
			// 设计表格
			SQLSupport.sqlFillTable(table_name, mtb, "order by 1");
			dtm.setColumnFilter(MyDtm.ALL_PROHIBITED);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			super.actionPerformed(e);
			if (selectRow != -1 && e.getSource().equals(jb1[0])) {
				if (verifyValue.equals(DELETED)) {
					JOptionPane.showMessageDialog(null, "检测到关联单已经失效，不再提供审核功能。");
					dtm.setValueAt(OVERDUE, mtb.getJt().getSelectedRow(), verifyColInd);
					SQLSupport.sqlUpdate(table_name, "审核状态", OVERDUE, "单号", F1P1.inter_label, "");
				} else if (verifyValue.equals(OVERDUE)) {
					JOptionPane.showMessageDialog(null, "检测到关联单已经失效，不再提供审核功能。");
				}
				// 页面跳转,增加监控
				else {
					if (verifyValue.equals(VERIFIED)) {
						JOptionPane.showMessageDialog(null, "本单据已经通过审核，不再提供审核功能。");
					}
					try {
						JFrame jf = FrameSupport.reflectJF2(rfstr, tbN);
						jf.addWindowListener(new WindowAdapter() {
							@Override
							public void windowClosed(WindowEvent e) {
								// 若主页面关闭,则手动设置重置,并重新设置为可见
								SQLSupport.sqlFillTable(tbN, mtb, "order by 1");
							}
						});
					} catch (Exception e1) {
						System.out.println("反射失败！");
						e1.printStackTrace();
					}
				}
			} else {
				if (e.getSource().equals(jb1[0]))
					JOptionPane.showMessageDialog(null, "未选择一项！");
			}
		}
	}

	public class XSF2 extends F2 {

		private static final long serialVersionUID = -316263345420212959L;

		@Override
		public void init() {
			jbs = F2jbs;
			rfstr = F2rfstr;
			shN = table_name;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			super.actionPerformed(e);
		}
	}

	public class XSF2P0 extends F2P0 {

		private static final long serialVersionUID = -3310532987156997101L;

		@Override
		public void init() {
			jl1s = F2P0jl1s;
			// 填写单号
			jl2s = F2P0jl2s + F1P1.inter_label;
			jl3s = F2P0jl3s;
			jbs = F2P0jbs;
			// 填写表头
			colN = UtilSupport.concatAll(SQLSupport.sqlGetColN("YP"), fcolN);
		}

		@Override
		public void H() {
			super.H();
			// 若单据已经审核，设置按钮不可编辑
			if (!F1P1.verifyValue.equals(VERIFING)) {
				for (MyButton mb : jb) {
					mb.setVisible(false);
				}
			}
			// 设置表格不可更改
			dtm.setColumnFilter(MyDtm.ALL_PROHIBITED);
			// 填充1~8列（药品属性列）
			dtm.addTableModelListener(new TableModelListener() {
				@Override
				public void tableChanged(TableModelEvent e) {
					int rowC = dtm.getRowCount();
					jl3[0].setText(String.format(F2P0jl3s[0], rowC));
					// 修改数量时
					jl3[1].setText(String.format(F2P0jl3s[1], dtm.getSum(9, FLOAT_SCALE)));
				}
			});
			// 填充0~7列（药品属性列）
			dtm.addDual(SQLSupport.sqlGetMultiValue("YP", "XS_DH_X", "药品编号", "单号", F1P1.asso_label));
			// 填充8~9列（金额列）
			for (int i = 0; i < dtm.getRowCount(); i++) {
				String moreCond = String.format("and %s = '%s'", "药品编号", dtm.getValueAt(i, 0).toString().trim());
				String[] sValue = SQLSupport.sqlGetValue("XS_DH_X", "单号", F1P1.asso_label,
						new String[] { mtb.getColN()[8], mtb.getColN()[9] }, moreCond);
				dtm.setValueAt(sValue[0], i, 8);
				dtm.setValueAt(sValue[1], i, 9);
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// 点击修改，设置可更改数量
			if (e.getSource().equals(jb[0])) {
				// 更新审核状态
				SQLSupport.sqlUpdate(table_name, "审核状态", VERIFIED, "单号", F1P1.inter_label, "");
				// SQLSupport.sqlUpdate("XS_DH", "关联状态", ASSOCIATED, "单号", F1P1.asso_label, "");
				JOptionPane.showMessageDialog(null, "更新成功！");
				JOptionPane.getFrameForComponent(this).dispose();
			}
		}
	}
}
