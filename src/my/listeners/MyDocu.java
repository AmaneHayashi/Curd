package my.listeners;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public interface MyDocu extends DocumentListener {

	@Override
	public default void insertUpdate(DocumentEvent e) {

	}
	/*
	 * @Override public default void removeUpdate(DocumentEvent e) {
	 * 
	 * }
	 */

	@Override
	public default void changedUpdate(DocumentEvent e) {

	}
}
