package my.comps;

import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import common.clz.Parameters;

public class MyComboBox extends JPanel implements Parameters {

	private static final long serialVersionUID = 1050805006086740918L;

	private JComboBox<Object> dmc;
	private JLabel jl;
	private String[] sValue;

	public MyComboBox(String jlStr, String[] sValue) {
		setLayout(new FlowLayout());
		this.setsValue(sValue);
		jl = new JLabel(jlStr);
		dmc = new JComboBox<Object>(sValue);
		dmc.setFont(fYHP20);
		add(jl);
		add(dmc);
		dmc.setBackground(cQIAN_F2);
		dmc.setOpaque(false);
		dmc.setAutoscrolls(true);
		setOpaque(false);
	}

	public JComboBox<Object> getDmc() {
		return dmc;
	}

	public void setDmc(JComboBox<Object> dmc) {
		this.dmc = dmc;
	}

	public JLabel getJl() {
		return jl;
	}

	public void setJl(JLabel jl) {
		this.jl = jl;
	}

	public String[] getsValue() {
		return sValue;
	}

	public void setsValue(String[] sValue) {
		this.sValue = sValue;
	}
}
