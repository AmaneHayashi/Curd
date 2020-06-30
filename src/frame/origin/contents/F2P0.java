package frame.origin.contents;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import common.clz.Parameters;
import frame.origin.vars.CF2P0;
import my.comps.MyButton;
import my.comps.MyDtcr;
import my.comps.MyPanel;
import my.comps.MyTable;

public abstract class F2P0 extends CF2P0 implements Parameters, ActionListener {

	private static final long serialVersionUID = 265811444329961514L;

	public F2P0() {
		init();
		H();
	}

	public abstract void init();

	public void H() {
		// 整体设置
		setLayout(new BorderLayout(BORDER_HGAP, BORDER_VGAP));
		jp = new MyPanel(OPAQUE_TYPE, null);

		/* jpUp (头端布局) */
		jpU = new MyPanel(OPAQUE_TYPE, null);

		// 头标题
		jl1 = new JLabel(jl1s, SwingConstants.HORIZONTAL);
		jl1.setFont(fZSB36);
		jpU.add(jl1, BorderLayout.NORTH);

		// 表二按钮组（左边，右边）

		jp1 = new MyPanel(GRID_TYPE, new int[] { 1, 1 + jbs.length });
		// 如果jl2s为null，则不用jl2s
		try {
			jl2 = new JLabel(jl2s.toString());
			jp1.add(jl2);
		} catch (NullPointerException e) {
		}
		jb = new MyButton[jbs.length];
		for (int i = 0; i < jb.length; i++) {
			jb[i] = new MyButton(jbs[i]);
			jb[i].addActionListener(this);
			jp1.add(jb[i]);
		}

		jpU.add(jp1, BorderLayout.CENTER);

		jp.add(jpU, BorderLayout.NORTH);

		/* jpDown (末端布局) */
		jpD = new MyPanel(OPAQUE_TYPE, null);

		// 表二
		mtb = new MyTable(colN);
		jt = mtb.getJt();
		dtm = mtb.getDtm();
		mtb.getJth().setBackground(cHEAD_F2);
		mtb.getJt().getTableHeader().setBackground(cHEAD_F2);
		mtb.setDtcr(new MyDtcr(cSHEN_F2, cQIAN_F2));

		jpD.add(mtb.getJp(), BorderLayout.CENTER);

		// 表二注解组
		jp2 = new MyPanel(GRID_TYPE, new int[] { 1, jl3s.length });
		jl3 = new JLabel[jl3s.length];
		for (int i = 0; i < jl3.length; i++) {
			jl3[i] = new JLabel(jl3s[i]);
			jp2.add(jl3[i]);
		}
		jl3[0].setText(jl3[0].getText() + dtm.getRowCount());
		jpD.add(jp2, BorderLayout.SOUTH);

		jp.add(jpD, BorderLayout.CENTER);

		// Panel特性
		add(jp, BorderLayout.CENTER);
		add(new MyPanel(NULL_TYPE, null), BorderLayout.EAST);
		add(new MyPanel(NULL_TYPE, null), BorderLayout.WEST);
		setOpaque(false);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(jb[0]) && dtm.isEachCellFilled()) {
			dtm.addRow(new Object[] {});
		} else if (e.getSource().equals(jb[1])) {
			int selectRow = jt.getSelectedRow();
			if (selectRow >= 0 && jt.isCellEditable(selectRow, 1)) {
				dtm.removeRow(jt.getSelectedRow());
			}
		}
	}

	public MyTable getMtb() {
		return mtb;
	}
}
