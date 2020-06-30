package frame.origin.contents;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import common.clz.Parameters;
import common.support.FrameSupport;
import frame.origin.vars.CFL;
import my.comps.MyButton;
import my.comps.MyPanel;
import my.comps.MyPsw;
import my.comps.MyText;

public abstract class FL extends CFL implements Parameters, KeyListener, ActionListener {

	private static final long serialVersionUID = 1114398421050250801L;

	public FL() {
		init();
		H();
	}

	public abstract void init();

	public void H() {
		// ���ñ���ͼƬ
		bg = new ImageIcon(LOGIN_PIC_PATH);
		jl = new JLabel(bg);
		jl.setBounds(new Rectangle(0, 0, bg.getIconWidth(), bg.getIconHeight()));
		jp = (JPanel) getContentPane();
		jp.setOpaque(false);
		jp.setLayout(new BorderLayout(GRID_HGAP, GRID_VGAP));
		getLayeredPane().add(jl, new Integer(Integer.MIN_VALUE));

		// ���ô����
		jl1 = new JLabel(jl1s, SwingConstants.HORIZONTAL);
		jl1.setFont(fFSB28);
		add(jl1, BorderLayout.NORTH);

		// ���õ�¼����
		jp1 = new MyPanel(GRID_TYPE, new int[] { 3, 1 });
		mt = new MyText(mts, 12);
		mp = new MyPsw(mps, 12);
		mt.getTextField().addKeyListener(this);
		mp.getPasswordField().addKeyListener(this);
		jl2 = new JLabel(jl2s, SwingConstants.HORIZONTAL);
		jl2.setFont(fSTB18);
		jp1.add(mt);
		jp1.add(mp);
		jp1.add(jl2);
		jp1.setOpaque(false);
		add(jp1, BorderLayout.CENTER);

		// ���ð�ť
		jp2 = new MyPanel(GRID_TYPE, new int[] { 1, 3 });
		jb = new MyButton[jbs.length];
		for (int i = 0; i < jb.length; i++) {
			jb[i] = new MyButton(jbs[i]);
			jb[i].addActionListener(this);
			jp2.add(jb[i]);
		}
		jp2.setOpaque(false);
		add(jp2, BorderLayout.SOUTH);

		// ���ô�������
		setTitle("LoginFrame Tester");
		setSize(bg.getIconWidth(), bg.getIconHeight());
		FrameSupport.setLocation(this, null, WINDOW_SCREEN_CENTER);
		FrameSupport.setTransparent(this, true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(jb[0])) {
			LoginJudgement();
		} else if (e.getSource().equals(jb[1])) {
			mt.getTextField().setText("");
			mp.getPasswordField().setText("");
		} else if (e.getSource().equals(jb[2])) {
			System.exit(0);
		}
	}

	public abstract void LoginJudgement();

	@Override
	public void keyTyped(KeyEvent e) {
		// �����¼��̵�Enter��
		if (e.getKeyChar() == '\n') {
			jb[0].doClick();
			// �����¼��̵�Esc��
		} else if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
			jb[2].doClick();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// ���ý������ͨ���������Ҽ������ƶ�
		int x = getX();
		int y = getY();
		switch (e.getKeyCode()) {
		case KeyEvent.VK_DOWN:
			y += STRIDE_VALUE;
			break;
		case KeyEvent.VK_UP:
			y -= STRIDE_VALUE;
			break;
		case KeyEvent.VK_RIGHT:
			x += STRIDE_VALUE;
			break;
		case KeyEvent.VK_LEFT:
			x -= STRIDE_VALUE;
			break;
		default:
			break;
		}
		setLocation(x, y);
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}