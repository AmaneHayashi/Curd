package my.comps;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import common.clz.Parameters;

public class MyPanel extends JPanel implements Parameters {

	private static final long serialVersionUID = -2455692474836192967L;

	public MyPanel(int typeFlag, int[] param) {
		setOpaque(false);
		switch (typeFlag) {
		case (OPAQUE_TYPE):
			setLayout(new BorderLayout(BORDER_HGAP, BORDER_VGAP));
			break;
		case (NULL_TYPE):
			add(new JLabel());
			break;
		case (GRID_TYPE):
			try {
				setLayout(new GridLayout(param[0], param[1], GRID_HGAP, GRID_VGAP));
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println(" ‰»Îµƒparams¥ÌŒÛ£°");
			}
			break;
		case (FLOW_TYPE):
			setLayout(new FlowLayout());
			break;
		default:
			break;
		}
	}
}
