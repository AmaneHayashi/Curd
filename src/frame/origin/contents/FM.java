package frame.origin.contents;

import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import common.clz.Parameters;
import common.support.*;
import frame.origin.vars.CFM;
import my.listeners.MyWindow;

public abstract class FM extends CFM implements Parameters, ActionListener {

	private static final long serialVersionUID = 6941722890892459517L;

	public FM() {
		init();
		H();
	}

	public abstract void init();

	public void H() {
		// 设置背景图片
		bg = new ImageIcon(MAIN_PIC_PATH);
		jl = new JLabel(bg);
		jl.setBounds(new Rectangle(0, 0, bg.getIconWidth(), bg.getIconHeight()));
		jp = (JPanel) getContentPane();
		jp.setOpaque(false);
		jp.setLayout(new GridLayout(3, 1, GRID_HGAP, GRID_VGAP));
		getLayeredPane().add(jl, new Integer(Integer.MIN_VALUE));

		// 设置菜单栏
		jmb = new JMenuBar();
		jm = new JMenu[MENU.length];
		jmi = new JMenuItem[MENU.length][];

		for (int i = 0; i < jm.length; i++) {
			jm[i] = new JMenu(MENU[i]);
			jm[i].setEnabled(i % (jm.length - 1) != 0 ? false : true);
			jm[i].setMnemonic(MNEMONICS[i]);
			jmb.add(jm[i]);
		}
		for (int i = 0; i < jm.length; i++) {
			jmi[i] = new JMenuItem[MENUf[i].length];
			for (int j = 0; j < MENUf[i].length; j++) {
				jmi[i][j] = new JMenuItem(MENUf[i][j]);
				jm[i].add(jmi[i][j]).addActionListener(this);
			}
		}
		jmb.getComponent().setBackground(cQIAN_F1);

		setJMenuBar(jmb);

		// 设置介绍内容
		jl1 = new JLabel(jl1s, SwingConstants.HORIZONTAL);
		jl1.setFont(fZSB52);
		jp.add(jl1);
		jl2 = new JLabel(jl2s, SwingConstants.HORIZONTAL);
		jl2.setFont(fXHP40);
		jp.add(jl2);
		jl3 = new JLabel(jl3s, SwingConstants.HORIZONTAL);
		jl3.setFont(fFSB28);
		jp.add(jl3);

		// 权限初始化
		initPermission();

		// 设置窗体特征
		setTitle("MainFrame Tester");
		setSize(bg.getIconWidth(), bg.getIconHeight());
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		FrameSupport.setDoubleClickExit(this, true);
		FrameSupport.setLocation(this, null, WINDOW_SCREEN_CENTER);
		FrameSupport.setTransparent(this, true);

		setVisible(true);
		setResizable(false);
	}

	public void initPermission() {
		int label = UtilSupport.filterOrder(UtilSupport.Transpose(CURD)[0], (s) -> s.equals(FL.permission));
		if (FL.permission.equals(CURD[0][0])) {
			for (int i = 1; i < MENU.length; i++)
				jm[i].setEnabled(true);
		} else {
			jmi[4][2].setEnabled(false);
			jm[label].setEnabled(true);
			switch (label) {
			case 1:
				jmi[4][0].setEnabled(false);
				jmi[4][4].setEnabled(false);
				break;
			case 2:
				jmi[4][0].setEnabled(false);
				jmi[4][1].setEnabled(false);
				break;
			case 3:
				jm[2].setEnabled(true);
				for (int i = 0; i < 5; i++)
					jmi[2][i].setEnabled(false);
				jmi[2][5].setEnabled(true);
				jmi[4][1].setEnabled(false);
				jmi[4][4].setEnabled(false);
				break;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.setVisible(false);
		String class_name, table_name;
		Rectangle r = aE(e);
		if (r.x == 0) {
			switch (r.y) {
			case 0:
				this.dispose();
				break;
			case 1:
				this.setExtendedState(JFrame.ICONIFIED);
				break;
			case 2:
				System.exit(0);
				break;
			}
		} else {
			if (MENUf[r.x][r.y].contains("管理")) {
				class_name = UNION_ADDR + CURD[r.x][1];
				table_name = CURDf[r.x][r.y];
			} else {
				table_name = CURD[r.x][1] + "_" + CURDf[r.x][r.y];
				if (MENUf[r.x][r.y].contains("查询")) {
					class_name = UNION_ADDR + "CH";
				} else {
					class_name = UNION_ADDR + CURD[r.x][1] + "_" + CURDf[r.x][r.y];
				}
			}
			FrameSupport.reflectFor(class_name, table_name).addWindowListener((MyWindow) l -> setVisible(true));
		}
	}

	private Rectangle aE(ActionEvent e) {
		for (int i = 0; i < CURDf.length; i++) {
			for (int j = 0; j < CURDf[2].length; j++) {
				try {
					if (e.getSource().equals(jm[i].getMenuComponent(j))) {
						return new Rectangle(i, j, i, j);
					}
				} catch (ArrayIndexOutOfBoundsException ee) {
				}
			}
		}
		return null;
	}
}