package common.support;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import common.clz.Parameters;

public class FrameSupport implements Parameters {

	private static int xOld = 0;
	private static int yOld = 0;

	// ����JFrame��λ�ã�Ĭ��λ�ã���Ļ���ģ����������ģ�
	public static void setLocation(JFrame jf, JFrame jfParent, int flag) {
		switch (flag) {
		case WINDOW_DEFAULT:
			jf.setLocation(300, 300);
			break;
		case WINDOW_SCREEN_CENTER:
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int screenX = screenSize.width / 2;
			int screenY = screenSize.height / 2;
			jf.setLocation(screenX - jf.getWidth() / 2, screenY - jf.getHeight() / 2);
			break;
		case WINDOW_PARENT_CENTER:
			int parentX = jfParent.getX();
			int parentY = jfParent.getY();
			jf.setLocation(parentX - jf.getWidth() / 2, parentY - jf.getHeight() / 2);
			break;
		}
	}

	// ����JFrameΪ͸������
	public static void setTransparent(JFrame jf, boolean b) {
		if (b) {
			jf.setUndecorated(true);
			if (com.sun.awt.AWTUtilities.isWindowOpaque(jf)) {
				com.sun.awt.AWTUtilities.setWindowOpacity(jf, (float) (1 - TRANSPARENT_VALUE));
			} else {
				JOptionPane.showMessageDialog(jf, "ϵͳ��֧�� JDK�汾���ͻ� JRE ϵͳ��ȱ��");
			}

			jf.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					xOld = e.getX();
					yOld = e.getY();
				}
			});

			jf.addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseDragged(MouseEvent e) {
					int xOnScreen = e.getXOnScreen();
					int yOnScreen = e.getYOnScreen();
					int xx = xOnScreen - xOld;
					int yy = yOnScreen - yOld;
					jf.setLocation(xx, yy);
				}
			});
		}
	}

	// ����JFrameΪ�˵����������˳�
	public static void setDoubleClickExit(JFrame jf, boolean b) {
		if (b) {
			JMenuBar jmb = jf.getJMenuBar();
			jmb.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2)
						System.exit(0);
				}
			});
		}
	}

	// ����JFrame
	private static Object reflectTo(String reflectStr, String tbN) throws Exception {
		Class<?> clz0, clz;
		try {
			clz0 = Class.forName(reflectStr.substring(0, reflectStr.indexOf("$")));// ��ȡ
			clz = Class.forName(reflectStr);
		} catch (ClassNotFoundException e) {
			clz0 = Class.forName(UNION_ADDR + "CH");// ��ȡ
			clz = Class.forName(UNION_ADDR + "CH$CHF2P1");
		}
		if (UNION_ADDR.contains(clz.getPackage().getName())) {
			Object ins0 = clz0.getDeclaredConstructor(String.class).newInstance(tbN);
			return clz.getDeclaredConstructors()[0].newInstance(ins0);
		}
		return null;
	}

	// �ر�����JF2�ļ��
	public static JFrame reflectJF2(String reflectStr, String tbN) throws Exception {
		Class<?> clz0 = Class.forName(reflectStr.substring(0, reflectStr.indexOf("$")));// ��ȡ
		Class<?> clz = Class.forName(reflectStr);
		if (UNION_ADDR.contains(clz.getPackage().getName())) {
			Object ins0 = clz0.getDeclaredConstructor(String.class).newInstance(tbN);
			Object ins = clz.getDeclaredConstructors()[0].newInstance(ins0);
			return (JFrame) clz.getMethod("getJf", null).invoke(ins);
		}
		// ���ؿ���
		return null;
	}

	// ���ڷ���ʵ������JFrame
	public static JFrame reflectFor(String reflectStr, String tbN) {
		Class<?> clz;
		System.out.println("ת���ַ:" + reflectStr);
		try {
			clz = Class.forName(reflectStr);
			// ʵ����
			Object obj = clz.getDeclaredConstructor(String.class).newInstance(tbN);
			// ��ʼ���з��ؿ���
			return (JFrame) clz.getMethod("run", null).invoke(obj);
		} catch (Exception e) {
			System.out.println("����ʧ�ܣ�");
			e.printStackTrace();
			return null;
		}
	}

	public static void repaintTo(String reflectStr, String tbN, JPanel jp) throws Exception {
		jp.removeAll();
		jp.add((Component) reflectTo(reflectStr, tbN));
		jp.validate();
	}

	public static void refreshLabel(DefaultTableModel dtm, String formatPrefix, String format) {
		int rowCount = dtm.getRowCount();
		for (int i = 1; i <= rowCount; i++) {
			dtm.setValueAt(String.format(formatPrefix + "%s", new DecimalFormat(format).format(i)), i - 1, 0);
		}
	}

	// ���ݱ��еı�Ÿ�ֵ
	public static void setLabel(DefaultTableModel dtm, String formatPrefix) {
		int rowCount = dtm.getRowCount();
		int index = 1;
		// ��������
		if (rowCount != 1) {
			// ����ȡֵ
			String str = dtm.getValueAt(rowCount - 2, 0).toString().trim();
			// ��ȡ����
			index = Integer.parseInt(str.substring(formatPrefix.length(), str.length())) + 1;
		}
		// ���и�ֵ
		dtm.setValueAt(String.format(formatPrefix + "%s", new DecimalFormat("000").format(index)), rowCount - 1, 0);
	}

	// ����������õ��ţ�XS_DH190430001001
	public static void setLabel(JLabel jl, String formatPrefix, String formTableName, String repoLabel) {
		String formatDate = new SimpleDateFormat("yyMMdd").format(new Date());
		String ftn = new DecimalFormat("000").format(SQLSupport.sqlGetLabel(formTableName));
		String rtn = new DecimalFormat("000").format(Integer.parseInt(repoLabel.substring(2, 5)));
		jl.setText("���ţ�" + formatPrefix + formatDate + rtn + ftn);
	}

	// ������ĸ��µ���
	public static void updateLabel(JLabel jl, String repoLabel) {
		jl.setText(UtilSupport.strReplace(jl.getText(), repoLabel.substring(2, 5), 14, 17));
	}

}