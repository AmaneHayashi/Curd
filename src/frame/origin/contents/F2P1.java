package frame.origin.contents;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import common.clz.Parameters;
import common.support.SQLSupport;
import common.support.UtilSupport;
import frame.origin.vars.CF2P1;
import my.comps.MyButton;
import my.comps.MyComboBox;
import my.comps.MyDtcr;
import my.comps.MyDtm;
import my.comps.MyPanel;
import my.comps.MyTable;
import my.comps.MyText;

public abstract class F2P1 extends CF2P1 implements Parameters, ActionListener {

	private static final long serialVersionUID = -6963410097686939911L;

	public F2P1() {
		init();
		H();
	}

	// JMenuBar
	// 整体页面设计
	// 模糊查询

	public abstract void init();

	public void H() {
		// 整体设置
		setLayout(new BorderLayout(BORDER_HGAP, BORDER_VGAP));
		jp = new MyPanel(OPAQUE_TYPE, null);
		/* 头端 */
		jpU = new MyPanel(OPAQUE_TYPE, null);

		// 头标题
		jl1 = new JLabel(jl1s, SwingConstants.HORIZONTAL);
		jl1.setFont(fZSB36);

		jpU.add(jl1, BorderLayout.NORTH);
		add(jpU, BorderLayout.NORTH);

		/* 中端 */
		jpC = new MyPanel(OPAQUE_TYPE, null);
		jp1 = new MyPanel(GRID_TYPE, new int[] { dmcs.length, mts.length });

		// 待查列名
		dmc = new MyComboBox[dmcs.length];
		for (int i = 0; i < dmc.length; i++) {
			dmc[i] = new MyComboBox(dmcs[i], dmcv[i]);
			jp1.add(dmc[i]);
		}

		mt = new MyText[mts.length];
		for (int i = 0; i < mt.length; i++) {
			mt[i] = new MyText(mts[i], 12);
			jp1.add(mt[i]);
		}
		jpC.add(jp1, BorderLayout.NORTH);

		jp2 = new MyPanel(OPAQUE_TYPE, null);

		jb = new MyButton(jbs);
		jb.addActionListener(this);
		jp2.add(jb, BorderLayout.NORTH);

		// 表二
		mtb = new MyTable(colN);
		dtm = mtb.getDtm();
		dtm.setColumnFilter(MyDtm.ALL_PROHIBITED);
		mtb.getJth().setBackground(cHEAD_F2);
		mtb.getJt().getTableHeader().setBackground(cHEAD_F2);
		mtb.setDtcr(new MyDtcr(cSHEN_F2, cQIAN_F2));
		jp2.add(mtb.getJp(), BorderLayout.CENTER);

		jpC.add(jp2, BorderLayout.CENTER);

		// 表二注解组
		jl2 = new JLabel(jl2s);
		jpC.add(jl2, BorderLayout.SOUTH);

		jp.add(jpC, BorderLayout.CENTER);

		// Panel特性
		add(jp, BorderLayout.CENTER);
		add(new MyPanel(NULL_TYPE, null), BorderLayout.EAST);
		add(new MyPanel(NULL_TYPE, null), BorderLayout.WEST);
		setOpaque(false);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String tbN = UtilSupport.strReplace(shN, "_", 2, 4);
		if (e.getSource().equals(jb)) {
			// 以2个为例
			String sItem[] = new String[dmcs.length];
			String sValue[] = new String[dmcs.length];
			for (int i = 0; i < sItem.length; i++) {
				sItem[i] = dmc[i].getDmc().getSelectedItem().toString().trim();
				sValue[i] = mt[i].getTextField().getText().toString().trim();
			}
			String moreCond = String.format("and %s like '%%%s%%' and %s like '%%%s%%' order by 1", sItem[0], sValue[0],
					sItem[1], sValue[1]);
			SQLSupport.sqlFillTable(tbN, mtb, moreCond);
		}
	}
}
