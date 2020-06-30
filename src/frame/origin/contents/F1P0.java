package frame.origin.contents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import common.clz.Parameters;
import common.support.UtilSupport;
import frame.origin.vars.CF1P0;
import my.comps.MyButton;
import my.comps.MyComboBox;
import my.comps.MyDtcr;
import my.comps.MyPanel;
import my.comps.MyTable;
import my.comps.MyText;

public abstract class F1P0 extends CF1P0 implements Parameters, ActionListener {

	private static final long serialVersionUID = -807436018885495616L;

	public F1P0() {
		init();
		H();
	}

	public abstract void init();

	public void H() {
		// ��������
		setLayout(new BorderLayout(BORDER_HGAP, BORDER_VGAP));
		jp = new MyPanel(OPAQUE_TYPE, null);
		/* jpUp (ͷ�˲���) */
		jpU = new MyPanel(OPAQUE_TYPE, null);

		// ͷ����
		jl1 = new JLabel(jl1s, SwingConstants.HORIZONTAL);
		jl1.setFont(fZSB52);
		jpU.add(jl1, BorderLayout.NORTH);

		// ����
		jl2 = new JLabel(jl2s, SwingConstants.RIGHT);
		jl2.setFont(fXHB36);
		jl2.setForeground(Color.RED);
		jpU.add(jl2, BorderLayout.CENTER);

		// ������Ϣ
		jp1 = new MyPanel(FLOW_TYPE, null);
		dmc = new MyComboBox[dmcs.length];
		for (int i = 0; i < dmc.length; i++) {
			dmc[i] = new MyComboBox(dmcs[i], dmcv[i]);
			jp1.add(dmc[i]);
		}

		jpU.add(jp1, BorderLayout.SOUTH);

		jp.add(jpU, BorderLayout.NORTH);

		/* jpCenter (�ж˲���) */
		jpC = new MyPanel(OPAQUE_TYPE, null);

		// �޸İ�ť
		try {
			jp2 = new MyPanel(GRID_TYPE, new int[] { 1, jb1s.length + jb2s.length + 1 });
			F();
			jb2 = new MyButton[jb2s.length];
			for (int i = 0; i < jb2.length; i++) {
				jb2[i] = new MyButton(jb2s[i]);
				jb2[i].addActionListener(this);
				jp2.add(jb2[i]);
			}
		} catch (NullPointerException e) {
			jp2 = new MyPanel(GRID_TYPE, new int[] { 1, jb1s.length + 1 });
			F();
		}

		jpC.add(jp2, BorderLayout.NORTH);

		// ��Ʒ���
		mtb = new MyTable(colN);
		dtm = mtb.getDtm();
		mtb.getJth().setBackground(cHEAD_F1);
		mtb.setDtcr(new MyDtcr(cSHEN_F1, cQIAN_F1));
		jpC.add(mtb.getJp(), BorderLayout.CENTER);

		// �˵���Ϣ
		jp3 = new MyPanel(GRID_TYPE, new int[] { 1, mt1s.length });
		mt1 = new MyText[mt1s.length];
		for (int i = 0; i < mt1.length; i++) {
			mt1[i] = new MyText(mt1s[i], 9);
			mt1[i].getTextField().setEditable(false);
			jp3.add(mt1[i]);
		}

		jpC.add(jp3, BorderLayout.SOUTH);
		jp.add(jpC, BorderLayout.CENTER);

		/* jpDown (ĩ�˲���) */
		// ��������
		jpD = new MyPanel(GRID_TYPE, new int[] { 1, jb3s.length });

		mt2 = new MyText(mt2s, 16);
		jpD.add(mt2);
		jb3 = new MyButton[jb3s.length];
		for (int i = 0; i < jb3.length; i++) {
			jb3[i] = new MyButton(jb3s[i]);
			jb3[i].addActionListener(this);
			jpD.add(jb3[i]);
		}
		jp.add(jpD, BorderLayout.SOUTH);

		// Panel����
		add(jp, BorderLayout.CENTER);
		add(new MyPanel(NULL_TYPE, null), BorderLayout.EAST);
		add(new MyPanel(NULL_TYPE, null), BorderLayout.WEST);
		setOpaque(false);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// ���滹�Ǽ���
		String str = jb2[0].getText().toString();
		// �˳�
		if (e.getSource().equals(jb3[1])) {
			JOptionPane.getFrameForComponent(F1P0.this).dispose();
		}
		// ����/����
		if (e.getSource().equals(jb2[0])) {
			// �������뱣��
			if (str.startsWith("����")) {
				if (dtm.isEachCellFilled()) {
					mtb.getJt().setEnabled(false);
					mt1[1].getTextField().setEditable(true);
					jb1[0].setEnabled(false);
					jb2[0].setText(UtilSupport.strReplace(str, "����", 0, 2));
				} else {
					JOptionPane.showMessageDialog(null, "����û����ɱ����д�����ܱ�����");
				}
			} // �������뼤��
			else if (str.startsWith("����")) {
				mtb.getJt().setEnabled(true);
				mt1[1].getTextField().setEditable(false);
				jb1[0].setEnabled(true);
				jb2[0].setText(UtilSupport.strReplace(str, "����", 0, 2));
				mt1[1].getTextField().setText("0");
			}
		}
		// ɾ��
		else if (e.getSource().equals(jb2[1])) {
			int selectRow = mtb.getJt().getSelectedRow();
			if (selectRow >= 0 && str.startsWith("����")) {
				// �˲���(10)��ʵ�ʱ���й�
				dtm.setValueAt(0, selectRow, 10);
				dtm.removeRow(selectRow);
			}
		}
	}

	private void F() {
		jb1 = new MyButton[jb1s.length];
		for (int i = 0; i < jb1.length; i++) {
			jb1[i] = new MyButton(jb1s[i]);
			jb1[i].addActionListener(this);
			jp2.add(jb1[i]);
		}
		jp2.add(new JLabel());
	}
}
