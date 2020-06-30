package frame.origin.contents;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import common.clz.Parameters;
import common.support.SQLSupport;
import common.support.UtilSupport;
import frame.origin.vars.CF1P1;
import my.comps.MyButton;
import my.comps.MyDtcr;
import my.comps.MyPanel;
import my.comps.MyTable;
import my.comps.MyText;

public abstract class F1P1 extends CF1P1 implements Parameters, ActionListener {

	private static final long serialVersionUID = 265811444329961514L;
	public static String inter_label, asso_label;
	public static int selectRow, verifyColInd, assoColInd;
	public static String verifyValue, assoValue = null;

	public F1P1() {
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
		jl = new JLabel(jls, SwingConstants.HORIZONTAL);
		jl.setFont(fZSB52);
		jpU.add(jl, BorderLayout.NORTH);

		// 初始按钮组
		jp1 = new MyPanel(GRID_TYPE, new int[] { 1, jb1s.length + 1 });
		jb1 = new MyButton[jb1s.length];
		for (int i = 0; i < jb1.length; i++) {
			jb1[i] = new MyButton(jb1s[i]);
			jb1[i].addActionListener(this);
			jp1.add(jb1[i]);
		}
		jta = new JTextArea(jtas);
		jta.setFont(fXHP14);
		jta.setLineWrap(true);
		jta.setEditable(false);
		jp1.add(jta);
		jpU.add(jp1, BorderLayout.SOUTH);

		jp.add(jpU, BorderLayout.NORTH);

		/* jpDown (末端布局) */
		jpD = new MyPanel(OPAQUE_TYPE, null);

		// 表一按钮组
		jp2 = new MyPanel(GRID_TYPE, new int[] { 1, mts.length + 1 });
		mt = new MyText[mts.length];
		for (int i = 0; i < mt.length; i++) {
			mt[i] = new MyText(mts[i], 12);
			jp2.add(mt[i]);
		}
		jb2 = new MyButton(jb2s);
		jb2.addActionListener(this);
		jp2.add(jb2);
		jpD.add(jp2, BorderLayout.NORTH);

		// 表一
		mtb = new MyTable(colN);
		mtb.getJth().setBackground(cHEAD_F1);
		mtb.setDtcr(new MyDtcr(cSHEN_F1, cQIAN_F1));
		dtm = mtb.getDtm();
		jpD.add(mtb.getJp(), BorderLayout.CENTER);

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
		selectRow = mtb.getJt().getSelectedRow();
		try {
			verifyColInd = UtilSupport.filterOrder(mtb.getColN(), (s) -> s.equals("审核状态"));
			assoColInd = UtilSupport.filterOrder(mtb.getColN(), (s) -> s.equals("关联状态"));
			verifyValue = dtm.getValueAt(selectRow, verifyColInd).toString().trim();
			// 单号传递
			inter_label = dtm.getValueAt(selectRow, 1).toString().trim();
			asso_label = dtm.getValueAt(selectRow, 2).toString().trim();
			assoValue = dtm.getValueAt(selectRow, assoColInd).toString().trim();
		} catch (ArrayIndexOutOfBoundsException e1) {
		}
		// 删除单据,在数据库更新
		if (e.getSource().equals(jb1[1])) {
			if (selectRow >= 0) {
				// 先在表格删除
				int result = JOptionPane.showOptionDialog(null, "是否根据当前的表单更新数据库？", "提示", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, null, null);
				// 更新数据库
				try {
					String id = dtm.getValueAt(selectRow, 1).toString().trim();
					if (result == 0 && id != null) {
						SQLSupport.sqlUpdate(tbN, "审核状态", DELETED, "单号", id, "");
					}
					dtm.setValueAt(DELETED, selectRow, verifyColInd);
				} catch (ArrayIndexOutOfBoundsException e1) {
				}
			}
		}
		// 退出时更新数据库
		else if (e.getSource().equals(jb1[2])) {
			JOptionPane.getFrameForComponent(this).dispose();
		}
		else if(e.getSource().equals(jb2)) {
			String s2 = mt[0].getTextField().getText().toString().trim();
			String s3 = mt[1].getTextField().getText().toString().trim();
			String t2 = mtb.getColN()[2];
			String t3 = mtb.getColN()[3];
			String moreCond = String.format("and %s like '%%%s%%' and %s like '%%%s%%' order by 1", t2, s2,
					t3, s3);
			SQLSupport.sqlFillTable(tbN, mtb, moreCond);
		}
	}
}
