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
		String text = jt.getText(); // ��ǰ���������
		char ch = e.getKeyChar(); // ׼�����ӵ��������ַ�

		// ���Ʋ�����������ֺ�С����
		if (!(ch >= '0' && ch <= '9') && ch != '.') {
			e.consume(); // ���ٵ�ǰ�����ַ�

			// ���Ʋ�����С���㿪ͷ
		} else if ("".equals(text) && ch == '.') {
			e.consume();

		} else if (text.contains(".")) {

			// ���Ʋ����ظ�����С����
			if (ch == '.') {
				e.consume();

				// ����С��λ��
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
