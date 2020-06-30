package my.listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class MyKeyListener extends KeyAdapter {

	private JTextField jt;
	private final static int PRECISION = 2;

	public MyKeyListener(JTextField jt) {
		this.jt = jt;
	}

	public void keyTyped(KeyEvent e) {
		String text = jt.getText(); // 当前输入框内容
		char ch = e.getKeyChar(); // 准备附加到输入框的字符

		// 限制不能输入非数字和小数点
		if (!(ch >= '0' && ch <= '9') && ch != '.') {
			e.consume(); // 销毁当前输入字符

			// 限制不能是小数点开头
		} else if ("".equals(text) && ch == '.') {
			e.consume();

		} else if (text.contains(".")) {

			// 限制不能重复输入小数点
			if (ch == '.') {
				e.consume();

				// 限制小数位数
			} else {
				int idx = text.indexOf('.');
				String tmp = text.substring(idx + 1);
				if (tmp.length() >= PRECISION) {
					e.consume();
				}
			}
		}
	}
}
