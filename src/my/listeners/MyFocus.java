package my.listeners;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public interface MyFocus extends FocusListener {

	@Override
	public default void focusGained(FocusEvent e) {

	}

	/*
	 * @Override public default void focusLost(FocusEvent e) {
	 * 
	 * }
	 */
}
