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

	// 设置JFrame的位置（默认位置，屏幕中心，父窗口中心）
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

	// 设置JFrame为透明窗口
	public static void setTransparent(JFrame jf, boolean b) {
		if (b) {
			jf.setUndecorated(true);
			if (com.sun.awt.AWTUtilities.isWindowOpaque(jf)) {
				com.sun.awt.AWTUtilities.setWindowOpacity(jf, (float) (1 - TRANSPARENT_VALUE));
			} else {
				JOptionPane.showMessageDialog(jf, "系统不支持 JDK版本过低或 JRE 系统库缺损");
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

	// 设置JFrame为菜单栏点两下退出
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

	// 设置JFrame
	private static Object reflectTo(String reflectStr, String tbN) throws Exception {
		Class<?> clz0, clz;
		try {
			clz0 = Class.forName(reflectStr.substring(0, reflectStr.indexOf("$")));// 截取
			clz = Class.forName(reflectStr);
		} catch (ClassNotFoundException e) {
			clz0 = Class.forName(UNION_ADDR + "CH");// 截取
			clz = Class.forName(UNION_ADDR + "CH$CHF2P1");
		}
		if (UNION_ADDR.contains(clz.getPackage().getName())) {
			Object ins0 = clz0.getDeclaredConstructor(String.class).newInstance(tbN);
			return clz.getDeclaredConstructors()[0].newInstance(ins0);
		}
		return null;
	}

	// 特别用于JF2的监控
	public static JFrame reflectJF2(String reflectStr, String tbN) throws Exception {
		Class<?> clz0 = Class.forName(reflectStr.substring(0, reflectStr.indexOf("$")));// 截取
		Class<?> clz = Class.forName(reflectStr);
		if (UNION_ADDR.contains(clz.getPackage().getName())) {
			Object ins0 = clz0.getDeclaredConstructor(String.class).newInstance(tbN);
			Object ins = clz.getDeclaredConstructors()[0].newInstance(ins0);
			return (JFrame) clz.getMethod("getJf", null).invoke(ins);
		}
		// 返回框体
		return null;
	}

	// 用于访问实例化的JFrame
	public static JFrame reflectFor(String reflectStr, String tbN) {
		Class<?> clz;
		System.out.println("转向地址:" + reflectStr);
		try {
			clz = Class.forName(reflectStr);
			// 实例化
			Object obj = clz.getDeclaredConstructor(String.class).newInstance(tbN);
			// 开始运行返回框体
			return (JFrame) clz.getMethod("run", null).invoke(obj);
		} catch (Exception e) {
			System.out.println("反射失败！");
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

	// 数据表中的标号赋值
	public static void setLabel(DefaultTableModel dtm, String formatPrefix) {
		int rowCount = dtm.getRowCount();
		int index = 1;
		// 若有上行
		if (rowCount != 1) {
			// 上行取值
			String str = dtm.getValueAt(rowCount - 2, 0).toString().trim();
			// 截取数字
			index = Integer.parseInt(str.substring(formatPrefix.length(), str.length())) + 1;
		}
		// 下行赋值
		dtm.setValueAt(String.format(formatPrefix + "%s", new DecimalFormat("000").format(index)), rowCount - 1, 0);
	}

	// 订单表的设置单号：XS_DH190430001001
	public static void setLabel(JLabel jl, String formatPrefix, String formTableName, String repoLabel) {
		String formatDate = new SimpleDateFormat("yyMMdd").format(new Date());
		String ftn = new DecimalFormat("000").format(SQLSupport.sqlGetLabel(formTableName));
		String rtn = new DecimalFormat("000").format(Integer.parseInt(repoLabel.substring(2, 5)));
		jl.setText("单号：" + formatPrefix + formatDate + rtn + ftn);
	}

	// 订单表的更新单号
	public static void updateLabel(JLabel jl, String repoLabel) {
		jl.setText(UtilSupport.strReplace(jl.getText(), repoLabel.substring(2, 5), 14, 17));
	}

}