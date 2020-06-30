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
	String fcolN[] = new String[] { "ҩƷ����", "ҩƷ���" };

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
			String moreCond = String.format("and ����״̬ = '%s'", ASSOCIATING);
			dmcv = new String[][] { SQLSupport.sqlFillDmc("XS_DH", "���״̬", VERIFIED, moreCond, new int[] { 1, 2, 3 }),
					SQLSupport.sqlFillDmc("KH", "1", "1", "", new int[] { 0, 1 }),
					SQLSupport.sqlFillDmc("YG", "ְ��", "���Ա", "", new int[] { 0, 1 }) };
			jb1s = F1P0jb1s;
			jb3s = F1P0jb3s;
			colN = UtilSupport.concatAll(new String[] { "���" }, SQLSupport.sqlGetColN("YP"), fcolN);
		}

		@Override
		public void H() {
			super.H();
			// ����������Ŀ
			JComboBox<Object> dmc0 = dmc[0].getDmc();
			JComboBox<Object> dmc1 = dmc[1].getDmc();
			// �޸�����
			mt1[3].setEnabled(false);
			mt1[3].getTextField().setText(new SimpleDateFormat("YYYY-MM-dd").format(new Date()));
			// ���µ��ݣ��£�
			dmc0.addItemListener((l) -> updateDmc(dmc, l.getItem().toString().trim()));
			// д����
			FrameSupport.setLabel(jl2, table_name, table_name, dmc1.getSelectedItem().toString().trim());// ͭǮ
			// �޸ĵ���
			dmc1.addItemListener((l) -> FrameSupport.updateLabel(jl2, dmc1.getSelectedItem().toString().trim()));// ͭǮ

			dtm.setRowFilter(dtm.getRowCount());// ͭǮ
			dtm.setColumnFilter(MyDtm.ALL_PROHIBITED);// ͭǮ
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
			// ��ʼ������
			dmc0.setSelectedIndex(0);
			try {
				updateDmc(dmc, dmc0.getItemAt(0).toString().trim());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "��ǰû�п��Թ��������۵�������ϵ���۲��š�");
			}
			dmc1.setEnabled(false);
		}

		public void updateDmc(MyComboBox[] dmc, String updItem) {
			dtm.removeAll();
			String id = UtilSupport.subBefore(updItem, "(");
			String client = UtilSupport.substringBetween(updItem, "(", ",");
			// ����dmc1���ͻ��뵥�ŵĿͻ���Ӧ��
			JComboBox<Object> dmc1 = dmc[1].getDmc();
			dmc1.setSelectedIndex(UtilSupport.filterOrder(dmc[1].getsValue(), (s) -> s.contains(client)));
			// ���1~8�У�ҩƷ�����У�
			Object obj = SQLSupport.sqlGetMultiValue("YP", "XS_DH_X", "ҩƷ���", "����", id);
			if (obj instanceof String[]) {
				dtm.addRow(UtilSupport.concatAll(new String[1], (String[]) obj));
			} else if (obj instanceof String[][]) {
				for (int i = 0; i < ((String[][]) obj).length; i++) {
					dtm.addRow(UtilSupport.concatAll(new String[1], ((String[][]) obj)[i]));
				}
			}
			// ���9~10�У�����У�
			for (int i = 0; i < dtm.getRowCount(); i++) {
				String moreCond = String.format("and %s = '%s'", "ҩƷ���", dtm.getValueAt(i, 1).toString().trim());
				String[] sValue = SQLSupport.sqlGetValue("XS_DH_X", "����", id,
						new String[] { mtb.getColN()[9], mtb.getColN()[10] }, moreCond);
				dtm.setValueAt(sValue[0], i, 9);
				dtm.setValueAt(sValue[1], i, 10);
			}
			// ���������
			String[] $ = SQLSupport.sqlGetValue("XS_DH", "����", id, new String[] { "Ӧ�ս��", "ʵ�ս��", "���ս��" }, "");
			for (int i = 0; i < mt1s.length - 1; i++) {
				mt1[i].getTextField().setText($[i]);
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// �����·���
			if (e.getSource().equals(jb1[0])) {
			}
			// ����
			else if (e.getSource().equals(jb3[0]) && dtm.isReadyToSave()) {
				// ���к�
				String s0 = String.valueOf(SQLSupport.sqlGetLabel(table_name));
				// ����
				String s1 = UtilSupport.subAfter(jl2.getText().toString().trim(), "��");
				// ��������
				String s2 = UtilSupport.subBefore(dmc[0].getDmc().getSelectedItem().toString().trim(), "(");
				// �ֿ���
				String s3 = UtilSupport.subBefore(dmc[1].getDmc().getSelectedItem().toString().trim(), "(");
				// ������
				String s4 = UtilSupport.subBefore(dmc[2].getDmc().getSelectedItem().toString().trim(), "(");
				// mt1--�����Ϣ012+��������3,mt2--��ע
				SQLSupport.sqlInsert(table_name, new String[] { s0, s1, s2, s3, s4, mt1[3].S(), mt2.S(), VERIFING });
				SQLSupport.sqlUpdate("XS_DH", "����״̬", ASSOCIATED, "����", s2, "");
				// ����--s1,ҩƷ���1,����8,����9,���10
				JOptionPane.showMessageDialog(null, "�ύ���ݳɹ���");
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
			// ��Ʊ��
			SQLSupport.sqlFillTable(table_name, mtb, "order by 1");
			dtm.setColumnFilter(MyDtm.ALL_PROHIBITED);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			super.actionPerformed(e);
			if (selectRow != -1 && e.getSource().equals(jb1[0])) {
				if (verifyValue.equals(DELETED)) {
					JOptionPane.showMessageDialog(null, "��⵽�������Ѿ�ʧЧ�������ṩ��˹��ܡ�");
					dtm.setValueAt(OVERDUE, mtb.getJt().getSelectedRow(), verifyColInd);
					SQLSupport.sqlUpdate(table_name, "���״̬", OVERDUE, "����", F1P1.inter_label, "");
				} else if (verifyValue.equals(OVERDUE)) {
					JOptionPane.showMessageDialog(null, "��⵽�������Ѿ�ʧЧ�������ṩ��˹��ܡ�");
				}
				// ҳ����ת,���Ӽ��
				else {
					if (verifyValue.equals(VERIFIED)) {
						JOptionPane.showMessageDialog(null, "�������Ѿ�ͨ����ˣ������ṩ��˹��ܡ�");
					}
					try {
						JFrame jf = FrameSupport.reflectJF2(rfstr, tbN);
						jf.addWindowListener(new WindowAdapter() {
							@Override
							public void windowClosed(WindowEvent e) {
								// ����ҳ��ر�,���ֶ���������,����������Ϊ�ɼ�
								SQLSupport.sqlFillTable(tbN, mtb, "order by 1");
							}
						});
					} catch (Exception e1) {
						System.out.println("����ʧ�ܣ�");
						e1.printStackTrace();
					}
				}
			} else {
				if (e.getSource().equals(jb1[0]))
					JOptionPane.showMessageDialog(null, "δѡ��һ�");
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
			// ���1~8�У�ҩƷ�����У�
			dtm.addTableModelListener(new TableModelListener() {
				@Override
				public void tableChanged(TableModelEvent e) {
					int rowC = dtm.getRowCount();
					jl3[0].setText(String.format(F2P0jl3s[0], rowC));
					// �޸�����ʱ
					jl3[1].setText(String.format(F2P0jl3s[1], dtm.getSum(9, FLOAT_SCALE)));
				}
			});
			// ���0~7�У�ҩƷ�����У�
			dtm.addDual(SQLSupport.sqlGetMultiValue("YP", "XS_DH_X", "ҩƷ���", "����", F1P1.asso_label));
			// ���8~9�У�����У�
			for (int i = 0; i < dtm.getRowCount(); i++) {
				String moreCond = String.format("and %s = '%s'", "ҩƷ���", dtm.getValueAt(i, 0).toString().trim());
				String[] sValue = SQLSupport.sqlGetValue("XS_DH_X", "����", F1P1.asso_label,
						new String[] { mtb.getColN()[8], mtb.getColN()[9] }, moreCond);
				dtm.setValueAt(sValue[0], i, 8);
				dtm.setValueAt(sValue[1], i, 9);
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// ����޸ģ����ÿɸ�������
			if (e.getSource().equals(jb[0])) {
				// �������״̬
				SQLSupport.sqlUpdate(table_name, "���״̬", VERIFIED, "����", F1P1.inter_label, "");
				// SQLSupport.sqlUpdate("XS_DH", "����״̬", ASSOCIATED, "����", F1P1.asso_label, "");
				JOptionPane.showMessageDialog(null, "���³ɹ���");
				JOptionPane.getFrameForComponent(this).dispose();
			}
		}
	}
}
