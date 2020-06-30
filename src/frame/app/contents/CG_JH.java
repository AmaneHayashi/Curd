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
import frame.app.vars.CSE;
import frame.origin.contents.F1;
import frame.origin.contents.F1P0;
import frame.origin.contents.F1P1;
import frame.origin.contents.F2;
import frame.origin.contents.F2P0;
import my.comps.MyButton;
import my.comps.MyDtm;
import my.listeners.MyFocus;
import my.listeners.MyKeyListener;
import my.listeners.MyWindow;

public class CG_JH implements CSE.JH {

	CGF1 jf;
	String table_name;
	String table_label;
	String fcolN[] = new String[] { "药品数量", "药品金额" };

	public CG_JH(String table_name) {
		this.table_name = table_name;
		table_label = table_name.substring(2);
	}

	@Override
	public JFrame run() {
		jf = new CG_JH(table_name).new CGF1();
		return jf;
	}

	public class CGF1 extends F1 {

		private static final long serialVersionUID = -1795814280706802080L;

		@Override
		public void init() {
			jbs = F1jbs;
			rfstr = F1rfstr;
			tbN = table_name;
		}
	}

	public class CGF1P0 extends F1P0 {

		private static final long serialVersionUID = 5852442571152476857L;

		@Override
		public void init() {
			jl1s = F1P0jl1s;
			mt1s = F1P0mt1s;
			mt2s = F1P0mt2s;
			dmcs = F1P0dmcs;
			dmcv = new String[][] { SQLSupport.sqlFillDmc("GYS", "1", "1", "", new int[] { 0, 1 }), // diff1
					SQLSupport.sqlFillDmc("CK", "1", "1", "", new int[] { 0, 1 }),
					SQLSupport.sqlFillDmc("YG", "职务", "采购员", "", new int[] { 0, 1 }) };
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
			mt1[3].setEnabled(false);
			mt1[3].getTextField().setText(new SimpleDateFormat("YYYY-MM-dd").format(new Date()));
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
					if (col == 9) {
						try {
							// 此处为float
							float price = Float.parseFloat((dtm.getValueAt(row, 8).toString().trim()));
							int amount = Integer.parseInt(dtm.getValueAt(row, 9).toString().trim());
							// float re = price * amount;
							dtm.setValueAt(UtilSupport.scaleF(price * amount, FLOAT_SCALE), row, 10);
							// 表格内容改变时,重新计算
							countRestAmount();
						} catch (NumberFormatException e1) {
							JOptionPane.showMessageDialog(null, "数量必须为数字！");
							e1.printStackTrace();
						}
					}
					if (col == 10) {
						float sumAmount = 0;
						for (int i = 0; i <= row; i++) {
							sumAmount += Float.parseFloat(dtm.getValueAt(i, 10).toString().trim());
						}
						mt1[0].getTextField().setText(UtilSupport.scaleF(sumAmount, FLOAT_SCALE));
					}
				}
			});
			// 文本类型监听
			mt1[1].getTextField().addKeyListener(new MyKeyListener(mt1[1].getTextField()));
			// 文本内容监听
			// 实付金额内容改变时,重新计算
			mt1[1].getTextField().addFocusListener((MyFocus) l -> countRestAmount());
		}

		public void countRestAmount() {
			try {
				float in = Float.parseFloat(mt1[0].getTextField().getText().toString());
				float ex = Float.parseFloat(mt1[1].getTextField().getText().toString());
				mt1[1].getTextField().setText(UtilSupport.scaleF((in > ex) ? ex : in, FLOAT_SCALE));
				mt1[2].getTextField().setText(UtilSupport.scaleF((in > ex) ? in - ex : 0, FLOAT_SCALE));
			} catch (NumberFormatException e) {
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// 实现老方法
			super.actionPerformed(e);
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
			} else if (e.getSource().equals(jb2[0])) {
				// 清空实付金额后,重新计算
				countRestAmount();
			}
			// 保存
			else if (e.getSource().equals(jb3[0]) && dtm.isReadyToSave()) {
				// 序列号
				String s0 = String.valueOf(SQLSupport.sqlGetLabel(table_name));
				// 单号
				String s1 = UtilSupport.subAfter(jl2.getText().toString().trim(), "：");
				// 供应商编号
				String s2 = UtilSupport.subBefore(dmc[0].getDmc().getSelectedItem().toString().trim(), "(");
				// 仓库编号
				String s3 = UtilSupport.subBefore(dmc[1].getDmc().getSelectedItem().toString().trim(), "(");
				// 经办人
				String s4 = UtilSupport.subBefore(dmc[2].getDmc().getSelectedItem().toString().trim(), "(");
				// mt1--金额信息012+订单日期3,mt2--备注
				SQLSupport.sqlInsert(table_name, new String[] { s0, s1, s2, s3, s4, mt1[0].S(), mt1[1].S(), mt1[2].S(),
						mt1[3].S(), mt2.S(), VERIFING, ASSOCIATING });
				// 单号--s1,药品编号1,单价8,数量9,金额10
				for (int i = 0; i < dtm.getRowCount(); i++) {
					String[] rowv = dtm.getRowValue(i);
					SQLSupport.sqlInsert(table_name + "_X", new String[] { s1, rowv[1], rowv[8], rowv[9], rowv[10] });
				}
				JOptionPane.showMessageDialog(null, "提交单据成功！");
				JOptionPane.getFrameForComponent(this).dispose();
			}
		}
	}

	public class CGF1P1 extends F1P1 {

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

			// 审核单据
			if (e.getSource().equals(jb1[0])) {
				if (selectRow == -1) {
					JOptionPane.showMessageDialog(null, "未选择一项！");
				} else {
					try {
						// 若已经审核，提示只可查看
						if (verifyValue.equals(DELETED) || verifyValue.equals(OVERDUE)) {
							JOptionPane.showMessageDialog(null, "本单据已经删除或失效，不可使用且不可修改。");
						} else {
							if (verifyValue.equals(VERIFIED)) {
								JOptionPane.showMessageDialog(null, "本单据已经审核，只看查看不可修改。");
							}

							// 页面跳转，增加监控
							JFrame jf = FrameSupport.reflectJF2(rfstr, tbN);
							jf.addWindowListener(new WindowAdapter() {
								@Override
								public void windowClosed(WindowEvent e) {
									// 若主页面关闭,则手动设置重置,并重新设置为可见
									SQLSupport.sqlFillTable(tbN, mtb, "order by 1");
								}
							});
						}
					} catch (Exception e1) {
						System.out.println("反射失败！");
						e1.printStackTrace();
					}
				}
			}
		}
	}

	public class CGF2 extends F2 {

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

	public class CGF2P0 extends F2P0 {

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
			dtm.addTableModelListener(new TableModelListener() {
				@Override
				public void tableChanged(TableModelEvent e) {
					int row = e.getFirstRow();
					int rowC = dtm.getRowCount();
					jl3[0].setText(String.format(F2P0jl3s[0], rowC));

					// 修改数量时
					if (e.getColumn() == 8) {
						// 修改金额
						float price = Float.parseFloat((dtm.getValueAt(row, 7).toString().trim()));
						int amount = Integer.parseInt(dtm.getValueAt(row, 8).toString().trim());
						dtm.setValueAt(UtilSupport.scaleF(price * amount, FLOAT_SCALE), row, 9);
					}
					jl3[1].setText(String.format(F2P0jl3s[1], dtm.getSum(9, FLOAT_SCALE)));
				}
			});
			// 填充0~7列（药品属性列）
			dtm.addDual(SQLSupport.sqlGetMultiValue("YP", table_name + "_X", "药品编号", "单号", F1P1.inter_label));
			// 填充8~9列（金额列）
			for (int i = 0; i < dtm.getRowCount(); i++) {
				String moreCond = String.format("and %s = '%s'", "药品编号", dtm.getValueAt(i, 0).toString().trim());
				String[] sValue = SQLSupport.sqlGetValue(table_name + "_X", "单号", F1P1.inter_label,
						new String[] { mtb.getColN()[8], mtb.getColN()[9] }, moreCond);
				dtm.setValueAt(sValue[0], i, 8);
				dtm.setValueAt(sValue[1], i, 9);
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// 点击修改，设置可更改数量
			if (e.getSource().equals(jb[0])) {
				dtm.setColumnFilter(new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 9 });
			} else if (e.getSource().equals(jb[1])) {
				String sl = F1P1.inter_label;
				// 更新审核状态与金额
				SQLSupport.sqlUpdate(table_name, "审核状态", VERIFIED, "单号", sl, "");
				// 当前应付金额
				float f1 = Float.parseFloat(dtm.getSum(9, FLOAT_SCALE));
				// 先前应付金额
				float f2 = Float.parseFloat(SQLSupport.sqlGetUniqueValue(table_name, "单号", sl, "应付金额", ""));
				// 先前已付金额
				float f3 = Float.parseFloat(SQLSupport.sqlGetUniqueValue(table_name, "单号", sl, "实付金额", ""));
				System.out.println("当前应付：" + f1 + "，先前应付：" + f2 + "，先前已付：" + f3);
				if (f1 >= f3) {
					SQLSupport.sqlUpdate(table_name, "应付金额", UtilSupport.scaleF(f1, FLOAT_SCALE), "单号", sl, "");
					SQLSupport.sqlUpdate(table_name, "待付金额", UtilSupport.scaleF(f1 - f3, FLOAT_SCALE), "单号", sl, "");
				} else if (f1 <= f2) {
					SQLSupport.sqlUpdate(table_name, "应付金额", UtilSupport.scaleF(f1, FLOAT_SCALE), "单号", sl, "");
					SQLSupport.sqlUpdate(table_name, "实付金额", UtilSupport.scaleF(f1, FLOAT_SCALE), "单号", sl, "");
					SQLSupport.sqlUpdate(table_name, "待付金额", UtilSupport.scaleF(0, FLOAT_SCALE), "单号", sl, "");
				}
				// 更新药品数量与金额
				String moreCond = String.format("and 单号 = '%s'", sl);
				for (int i = 0; i < fcolN.length; i++) {
					for (int j = 0; j < dtm.getRowCount(); j++) {
						String aValue = dtm.getValueAt(j, 8 + i).toString().trim();
						String aKey = dtm.getValueAt(j, 0).toString().trim();
						SQLSupport.sqlUpdate(table_name + "_X", dtm.getColumnName(8 + i), aValue, "药品编号", aKey,
								moreCond);
					}
				}
				JOptionPane.showMessageDialog(null, "更新成功！");
				JOptionPane.getFrameForComponent(this).dispose();
			}
		}
	}
}
