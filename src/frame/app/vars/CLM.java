package frame.app.vars;

import common.clz.Parameters;

public interface CLM extends Parameters {

	public interface LG extends CLM {
		// F-Login
		String FLjl1s = "CURD药品管理系统·登录";
		String FLjl2s = "（本系统不提供注册功能，请寻找数据管理员添加新的用户数据）";
		String FLjbs[] = { "登录", "重置", "退出" };
		String FLmts = "用户名：";
		String FLmps = "密    码：";
	}

	public interface MA extends CLM {
		// F-Main
		String FMjl1s = "欢迎使用CURD药品管理系统！";
		String FMjl2s = "欢迎您，%s业务高级工程师！";
		String FMjl3s = "（点击“基本 - 注销”可以实现账号切换）";
	}
}
