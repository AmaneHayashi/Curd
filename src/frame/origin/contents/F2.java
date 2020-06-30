package frame.origin.contents;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import common.clz.Parameters;
import common.support.FrameSupport;
import frame.origin.vars.CF2;
import my.comps.MyButton;

public abstract class F2 extends CF2 implements Parameters, ActionListener {

	private static final long serialVersionUID = -7308251110111081306L;

	public F2() {
		init();
		H();
	}

	public abstract void init();

	public void H() {
		// …Ë÷√±≥æ∞Õº∆¨
		bg = new ImageIcon(F2_PIC_PATH);
		jl = new JLabel(bg);
		jl.setBounds(new Rectangle(0, 0, bg.getIconWidth(), bg.getIconHeight()));
		getLayeredPane().add(jl, new Integer(Integer.MIN_VALUE));

		// …Ë÷√≤Àµ•
		jmb = new JMenuBar();
		jb = new MyButton[jbs.length];
		for (int i = 0; i < jb.length; i++) {
			jb[i] = new MyButton(jbs[i]);
			jb[i].addActionListener(this);
			jb[i].setBorder(BorderFactory.createEmptyBorder(5, 3, 5, 10));
			jmb.add(jb[i]);
		}
		jmb.getComponent().setBackground(cQIAN_F2);
		setJMenuBar(jmb);

		jp = (JPanel) getContentPane();
		jp.setOpaque(false);

		try {
			FrameSupport.repaintTo(rfstr, shN, jp);
		} catch (Exception e) {
			System.out.println("∑¥…‰ ß∞‹£°");
			e.printStackTrace();
		}

		// …Ë÷√¥∞ÃÂÃÿ’˜
		setTitle("Frame12 Tester");
		setSize(bg.getIconWidth(), bg.getIconHeight());

		FrameSupport.setLocation(this, null, WINDOW_SCREEN_CENTER);
		FrameSupport.setTransparent(this, true);

		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setVisible(true);
		setResizable(false);
	}

	public JFrame getJf() {
		return this;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(jb[0]))
			this.setExtendedState(JFrame.ICONIFIED);
		else if (e.getSource().equals(jb[1])) {
			this.dispose();
		}

	}
}