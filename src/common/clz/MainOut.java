package common.clz;

import java.awt.EventQueue;

import common.support.ColorSupport;
import common.support.FontSupport;
import common.support.SQLSupport;
import frame.app.contents.LM_LG;

public class MainOut {
	public static void main(String[] args) {
		new SQLSupport();
		FontSupport.loadIndyFont();
		ColorSupport.loadIndyColor();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new LM_LG();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
