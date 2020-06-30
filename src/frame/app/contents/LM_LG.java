package frame.app.contents;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import common.support.SQLSupport;
import frame.app.vars.CLM;
import frame.origin.contents.FL;

public class LM_LG extends FL implements CLM.LG {

	private static final long serialVersionUID = -4324448828998614663L;

	@Override
	public void init() {
		jl1s = FLjl1s;
		jl2s = FLjl2s;
		jbs = FLjbs;
		mts = FLmts;
		mps = FLmps;
	}

	@Override
	// 登录判断函数
	public void LoginJudgement() {
		// 得到主列值
		String usr = mt.getTextField().getText();
		// 得到待查列值
		String pwd = new String(mp.getPasswordField().getPassword()).trim();
		// 判断输入值是否为空
		if (usr.trim().isEmpty() || pwd.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "请输入用户名与密码！", "提示", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// 执行查询：sqlSearch(表名，主列名，主列值，待查列名，待查列值);
		if (SQLSupport.sqlSearch("Usr", "用户名", usr, "密码", pwd, "")) {
			// 如果输入正确，实例化主页面，设置权限，跳转至主页面
			permission = SQLSupport.sqlGetUniqueValue("Usr", "用户名", usr, "权限", "");
			this.setVisible(false);
			new LM_MA().addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					// 若主页面关闭（注销），则手动设置重置，并重新设置为可见
					jb[1].doClick();
					setVisible(true);
					mt.getTextField().requestFocus();
				}
			});
		} else {
			// 输入错误，则给出提示
			System.out.println(SQLSupport.sqlGetUniqueValue("Usr", "*", "用户名", usr, "密码"));
			JOptionPane.showMessageDialog(null, "您的用户名或密码输入不正确，请重新输入!", "提示", JOptionPane.ERROR_MESSAGE);
		}
	}
}
