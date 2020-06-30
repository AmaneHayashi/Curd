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
	String fcolN[] = new String[] { "ҩƷ����", "ҩƷ���" };

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
					SQLSupport.sqlFillDmc("YG", "ְ��", "�ɹ�Ա", "", new int[] { 0, 1 }) };
			jb1s = F1P0jb1s;
			jb2s = F1P0jb2s;
			jb3s = F1P0jb3s;
			colN = UtilSupport.concatAll(new String[] { "���" }, SQLSupport.sqlGetColN("YP"), fcolN);
		}

		@Override
		public void H() {
			super.H();
			// ����������Ŀ
			JComboBox<Object> dmc1 = dmc[1].getDmc();
			// д����
			FrameSupport.setLabel(jl2, table_name, table_name, dmc1.getSelectedItem().toString().trim());
			// �޸�����
			mt1[3].setEnabled(false);
			mt1[3].getTextField().setText(new SimpleDateFormat("YYYY-MM-dd").format(new Date()));
			// �޸ĵ���
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
							// �˴�Ϊfloat
							float price = Float.parseFloat((dtm.getValueAt(row, 8).toString().trim()));
							int amount = Integer.parseInt(dtm.getValueAt(row, 9).toString().trim());
							// float re = price * amount;
							dtm.setValueAt(UtilSupport.scaleF(price * amount, FLOAT_SCALE), row, 10);
							// ������ݸı�ʱ,���¼���
							countRestAmount();
						} catch (NumberFormatException e1) {
							JOptionPane.showMessageDialog(null, "��������Ϊ���֣�");
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
			// �ı����ͼ���
			mt1[1].getTextField().addKeyListener(new MyKeyListener(mt1[1].getTextField()));
			// �ı����ݼ���
			// ʵ��������ݸı�ʱ,���¼���
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
			// ʵ���Ϸ���
			super.actionPerformed(e);
			// �����·���
			if (e.getSource().equals(jb1[0])) {
				SJ_X sjx = new SJ_X("YP");
				sjx.run().addWindowListener(new MyWindow() {
					@Override
					public void windowClosed(WindowEvent e) {
						String sl = SJ_X.selected_label;
						// �Ѿ�������
						if (UtilSupport.filterOrder(dtm.getColumnValue(1), (s) -> s.equals(sl)) != -1) {
							JOptionPane.showMessageDialog(null, "��ҩƷ����ӣ�����ҩƷ���в鿴��");
						} else {
							String[] sValue = SQLSupport.sqlGetValue("YP", "ҩƷ���", sl, 30, "");
							dtm.addRow(UtilSupport.concatAll(new String[1], sValue, new String[2]));
						}
					}
				});
			} else if (e.getSource().equals(jb2[0])) {
				// ���ʵ������,���¼���
				countRestAmount();
			}
			// ����
			else if (e.getSource().equals(jb3[0]) && dtm.isReadyToSave()) {
				// ���к�
				String s0 = String.valueOf(SQLSupport.sqlGetLabel(table_name));
				// ����
				String s1 = UtilSupport.subAfter(jl2.getText().toString().trim(), "��");
				// ��Ӧ�̱��
				String s2 = UtilSupport.subBefore(dmc[0].getDmc().getSelectedItem().toString().trim(), "(");
				// �ֿ���
				String s3 = UtilSupport.subBefore(dmc[1].getDmc().getSelectedItem().toString().trim(), "(");
				// ������
				String s4 = UtilSupport.subBefore(dmc[2].getDmc().getSelectedItem().toString().trim(), "(");
				// mt1--�����Ϣ012+��������3,mt2--��ע
				SQLSupport.sqlInsert(table_name, new String[] { s0, s1, s2, s3, s4, mt1[0].S(), mt1[1].S(), mt1[2].S(),
						mt1[3].S(), mt2.S(), VERIFING, ASSOCIATING });
				// ����--s1,ҩƷ���1,����8,����9,���10
				for (int i = 0; i < dtm.getRowCount(); i++) {
					String[] rowv = dtm.getRowValue(i);
					SQLSupport.sqlInsert(table_name + "_X", new String[] { s1, rowv[1], rowv[8], rowv[9], rowv[10] });
				}
				JOptionPane.showMessageDialog(null, "�ύ���ݳɹ���");
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
			// ��Ʊ��
			SQLSupport.sqlFillTable(table_name, mtb, "order by 1");
			dtm.setColumnFilter(MyDtm.ALL_PROHIBITED);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			super.actionPerformed(e);

			// ��˵���
			if (e.getSource().equals(jb1[0])) {
				if (selectRow == -1) {
					JOptionPane.showMessageDialog(null, "δѡ��һ�");
				} else {
					try {
						// ���Ѿ���ˣ���ʾֻ�ɲ鿴
						if (verifyValue.equals(DELETED) || verifyValue.equals(OVERDUE)) {
							JOptionPane.showMessageDialog(null, "�������Ѿ�ɾ����ʧЧ������ʹ���Ҳ����޸ġ�");
						} else {
							if (verifyValue.equals(VERIFIED)) {
								JOptionPane.showMessageDialog(null, "�������Ѿ���ˣ�ֻ���鿴�����޸ġ�");
							}

							// ҳ����ת�����Ӽ��
							JFrame jf = FrameSupport.reflectJF2(rfstr, tbN);
							jf.addWindowListener(new WindowAdapter() {
								@Override
								public void windowClosed(WindowEvent e) {
									// ����ҳ��ر�,���ֶ���������,����������Ϊ�ɼ�
									SQLSupport.sqlFillTable(tbN, mtb, "order by 1");
								}
							});
						}
					} catch (Exception e1) {
						System.out.println("����ʧ�ܣ�");
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
			// ��д����
			jl2s = F2P0jl2s + F1P1.inter_label;
			jl3s = F2P0jl3s;
			jbs = F2P0jbs;
			// ��д��ͷ
			colN = UtilSupport.concatAll(SQLSupport.sqlGetColN("YP"), fcolN);
		}

		@Override
		public void H() {
			super.H();
			// �������Ѿ���ˣ����ð�ť���ɱ༭
			if (!F1P1.verifyValue.equals(VERIFING)) {
				for (MyButton mb : jb) {
					mb.setVisible(false);
				}
			}
			// ���ñ�񲻿ɸ���
			dtm.setColumnFilter(MyDtm.ALL_PROHIBITED);
			dtm.addTableModelListener(new TableModelListener() {
				@Override
				public void tableChanged(TableModelEvent e) {
					int row = e.getFirstRow();
					int rowC = dtm.getRowCount();
					jl3[0].setText(String.format(F2P0jl3s[0], rowC));

					// �޸�����ʱ
					if (e.getColumn() == 8) {
						// �޸Ľ��
						float price = Float.parseFloat((dtm.getValueAt(row, 7).toString().trim()));
						int amount = Integer.parseInt(dtm.getValueAt(row, 8).toString().trim());
						dtm.setValueAt(UtilSupport.scaleF(price * amount, FLOAT_SCALE), row, 9);
					}
					jl3[1].setText(String.format(F2P0jl3s[1], dtm.getSum(9, FLOAT_SCALE)));
				}
			});
			// ���0~7�У�ҩƷ�����У�
			dtm.addDual(SQLSupport.sqlGetMultiValue("YP", table_name + "_X", "ҩƷ���", "����", F1P1.inter_label));
			// ���8~9�У�����У�
			for (int i = 0; i < dtm.getRowCount(); i++) {
				String moreCond = String.format("and %s = '%s'", "ҩƷ���", dtm.getValueAt(i, 0).toString().trim());
				String[] sValue = SQLSupport.sqlGetValue(table_name + "_X", "����", F1P1.inter_label,
						new String[] { mtb.getColN()[8], mtb.getColN()[9] }, moreCond);
				dtm.setValueAt(sValue[0], i, 8);
				dtm.setValueAt(sValue[1], i, 9);
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// ����޸ģ����ÿɸ�������
			if (e.getSource().equals(jb[0])) {
				dtm.setColumnFilter(new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 9 });
			} else if (e.getSource().equals(jb[1])) {
				String sl = F1P1.inter_label;
				// �������״̬����
				SQLSupport.sqlUpdate(table_name, "���״̬", VERIFIED, "����", sl, "");
				// ��ǰӦ�����
				float f1 = Float.parseFloat(dtm.getSum(9, FLOAT_SCALE));
				// ��ǰӦ�����
				float f2 = Float.parseFloat(SQLSupport.sqlGetUniqueValue(table_name, "����", sl, "Ӧ�����", ""));
				// ��ǰ�Ѹ����
				float f3 = Float.parseFloat(SQLSupport.sqlGetUniqueValue(table_name, "����", sl, "ʵ�����", ""));
				System.out.println("��ǰӦ����" + f1 + "����ǰӦ����" + f2 + "����ǰ�Ѹ���" + f3);
				if (f1 >= f3) {
					SQLSupport.sqlUpdate(table_name, "Ӧ�����", UtilSupport.scaleF(f1, FLOAT_SCALE), "����", sl, "");
					SQLSupport.sqlUpdate(table_name, "�������", UtilSupport.scaleF(f1 - f3, FLOAT_SCALE), "����", sl, "");
				} else if (f1 <= f2) {
					SQLSupport.sqlUpdate(table_name, "Ӧ�����", UtilSupport.scaleF(f1, FLOAT_SCALE), "����", sl, "");
					SQLSupport.sqlUpdate(table_name, "ʵ�����", UtilSupport.scaleF(f1, FLOAT_SCALE), "����", sl, "");
					SQLSupport.sqlUpdate(table_name, "�������", UtilSupport.scaleF(0, FLOAT_SCALE), "����", sl, "");
				}
				// ����ҩƷ��������
				String moreCond = String.format("and ���� = '%s'", sl);
				for (int i = 0; i < fcolN.length; i++) {
					for (int j = 0; j < dtm.getRowCount(); j++) {
						String aValue = dtm.getValueAt(j, 8 + i).toString().trim();
						String aKey = dtm.getValueAt(j, 0).toString().trim();
						SQLSupport.sqlUpdate(table_name + "_X", dtm.getColumnName(8 + i), aValue, "ҩƷ���", aKey,
								moreCond);
					}
				}
				JOptionPane.showMessageDialog(null, "���³ɹ���");
				JOptionPane.getFrameForComponent(this).dispose();
			}
		}
	}
}
