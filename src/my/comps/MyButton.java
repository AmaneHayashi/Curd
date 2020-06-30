package my.comps;

import javax.swing.JButton;

public class MyButton extends JButton {

	private static final long serialVersionUID = -456471959245871519L;

	public MyButton() {
		init();

	}

	public MyButton(String s) {
		setText(s);
		init();
	}

	private void init() {
		setContentAreaFilled(false);
		// setBorder(new MyTextFieldBorder());
	}
}
