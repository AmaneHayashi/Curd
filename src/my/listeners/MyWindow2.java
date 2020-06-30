package my.listeners;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public interface MyWindow2 extends WindowListener {

	@Override
	public default void windowOpened(WindowEvent e) {
	}

	@Override
	public default void windowClosing(WindowEvent e) {
	}

	@Override
	public default void windowClosed(WindowEvent e) {
	}

	@Override
	public default void windowIconified(WindowEvent e) {
	}

	@Override
	public default void windowDeiconified(WindowEvent e) {
	}

	/*
	 * @Override public default void windowActivated(WindowEvent e) { }
	 */

	@Override
	public default void windowDeactivated(WindowEvent e) {
	}
}
