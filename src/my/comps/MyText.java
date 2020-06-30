package my.comps;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import common.clz.Parameters;

public class MyText extends JPanel implements Parameters {

	private static final long serialVersionUID = -8374200302380941176L;

	private JLabel jl;
	private JTextField jt;
	private String str;
	private int i;

	public MyText(String str, int i) {
		this.setStr(str);
		this.setI(i);
		setLayout(new FlowLayout());
		jl = new JLabel(str);
		jt = new JTextField(i);
		jt.setFont(fYHP20);
		add(jl);
		add(jt);
		jt.setOpaque(false);
		setOpaque(false);
	}

	public String S() {
		return getTextField().getText().toString().trim();
	}

	public JLabel getLabel() {
		return jl;
	}

	public JTextField getTextField() {
		return jt;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

}
