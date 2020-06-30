package frame.origin.contents;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import common.clz.Parameters;
import common.support.FrameSupport;
import frame.origin.vars.CF1;

public abstract class F1 extends CF1 implements Parameters, ActionListener {

	private static final long serialVersionUID = 87577210340324169L;

	public F1() {
		init();
		H();
	}

	public abstract void init();

	public void H() {
		// ���ñ���ͼƬ
		bg = new ImageIcon(F1_PIC_PATH);
		jl = new JLabel(bg);

		jl.setBounds(new Rectangle(0, 0, bg.getIconWidth(), bg.getIconHeight()));
		getLayeredPane().add(jl, new Integer(Integer.MIN_VALUE));

		jp = (JPanel) getContentPane();
		jp.setOpaque(false);

		// ���ò˵�

		jmb = new JMenuBar();
		jb = new JButton[jbs.length];

		for (int i = 0; i < jb.length; i++) {
			jb[i] = new JButton(jbs[i]);
			jb[i].addActionListener(this);
			jb[i].setBorder(BorderFactory.createEmptyBorder(5, 3, 5, 10));
			jmb.add(jb[i]);
		}
		jmb.getComponent().setBackground(cQIAN_F1);
		jb[0].doClick();
		setJMenuBar(jmb);

		// ���ô�������
		setSize(bg.getIconWidth(), bg.getIconHeight());

		FrameSupport.setDoubleClickExit(this, true);
		FrameSupport.setLocation(this, null, WINDOW_SCREEN_CENTER);
		FrameSupport.setTransparent(this, true);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < jb.length; i++) {
			jb[i].setBackground(cQIAN_F1);
			jb[i].setEnabled(true);
			if (e.getSource().equals(jb[i])) {
				try {
					// ����ʵ��
					FrameSupport.repaintTo(rfstr + i, tbN, jp);
					jb[i].setEnabled(false);
				} catch (Exception e1) {
					System.out.println("����ʧ�ܣ�");
					e1.printStackTrace();
				}
				jb[i].setBackground(Color.pink);
			}
		}
	}
}