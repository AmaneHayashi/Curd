package my.comps;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import common.clz.Parameters;

public class MyTable implements Parameters {

	private JTable jt;
	private JPanel jp;
	private JTableHeader jth;
	private MyDtm dtm;
	private DefaultTableCellRenderer dtcr;
	private String colN[];
	private Dimension jtd = new Dimension(705, 350);

	public void setDtcr(DefaultTableCellRenderer dtcr) {
		this.dtcr = dtcr;
		for (String s : colN) {
			jt.getColumn(s).setCellRenderer(dtcr);
		}
		dtcr.setHorizontalAlignment(JLabel.CENTER);// 表格数据居中
		jt.setDefaultRenderer(Object.class, dtcr);
	}

	public MyTable(String[] colN) {
		// 创建内容面板
		JScrollPane jsp;

		jp = new JPanel(new GridLayout(0, 1));
		jp.setOpaque(false);

		// 设置表格只有订单号和份额可编辑
		this.colN = colN;
		dtm = new MyDtm(colN, null, 0);

		// 表格设置
		jt = new JTable(dtm);
		setColumnWidth();
		jt.setRowHeight(32);
		jt.setSelectionBackground(new Color(217, 226, 254));
		jt.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// 表头设置
		jth = jt.getTableHeader();
		jth.setResizingAllowed(true);
		jth.setReorderingAllowed(false);

		// 滚动页面设置
		jsp = new JScrollPane(jt);
		jsp.setOpaque(false);
		jsp.getViewport().setOpaque(false);
		jsp.getVerticalScrollBar().setEnabled(false);
		jsp.setPreferredSize(jtd);

		jp.add(jsp);
		jt.setVisible(true);

	}

	public void setColumnWidth() {
		int len[] = new int[colN.length];
		int sumLen = 0;
		for (int i = 0; i < len.length; i++) {
			len[i] = colN[i].toString().length();
			sumLen += len[i];
		}
		int unitLen = (int) jtd.getWidth() / sumLen;
		for (int j = 0; j < len.length; j++) {
			jt.getColumn(colN[j]).setPreferredWidth(unitLen * len[j]);
		}
	}

	public String[] getQualifiedColN(int beginIndex, int endIndex) {
		String[] qCsN = new String[endIndex - beginIndex];
		for (int i = beginIndex; i < endIndex; i++) {
			qCsN[i - beginIndex] = colN[i];
		}
		return qCsN;
	}

	public JTable getJt() {
		return jt;
	}

	public void setJt(JTable jt) {
		this.jt = jt;
	}

	public JPanel getJp() {
		return jp;
	}

	public void setJp(JPanel jp) {
		this.jp = jp;
	}

	public MyDtm getDtm() {
		return dtm;
	}

	public void setMyDtm(MyDtm dtm) {
		this.dtm = dtm;
	}

	public DefaultTableCellRenderer getDtcr() {
		return dtcr;
	}

	public JTableHeader getJth() {
		return jth;
	}

	public void setJth(JTableHeader jth) {
		this.jth = jth;
	}

	public String[] getColN() {
		return colN;
	}

	public void setColN(String[] colN) {
		this.colN = colN;
	}
}