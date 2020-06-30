package my.comps;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import common.clz.Parameters;

public class MyPsw extends JPanel implements Parameters {

	private static final long serialVersionUID = -8374200302380941176L;

	private JLabel jl;
	private JPasswordField jp;
	private String str;
	private int i;

	public MyPsw(String str, int i) {
		this.setStr(str);
		this.setI(i);
		setLayout(new FlowLayout());
		jl = new JLabel(str);
		jp = new JPasswordField(i);
		jp.setFont(fYHP20);
		jp.setOpaque(false);
		add(jl);
		add(jp);
		setOpaque(false);
	}

	public JLabel getLabel() {
		return jl;
	}

	public JPasswordField getPasswordField() {
		return jp;
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