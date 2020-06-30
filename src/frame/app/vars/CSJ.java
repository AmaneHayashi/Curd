package frame.app.vars;

import javax.swing.JFrame;

import common.clz.Parameters;

public interface CSJ extends Parameters {

	public JFrame run();

	public interface CSJ_C extends CSJ {
		// F2
		String F2jbs[] = { "最小化", "返回", "保存并退出" };
		String F2rfstr = UNION_ADDR + "SJ$SJF2P0";
		String F2tbN = null;

		// F2P0
		String F2P0jl1s = "%s·基本信息表";
		String F2P0jl2s = null;
		String F2P0jl3s[] = { "记录数：" };
		String F2P0jbs[] = { "增加", "修改", "删除" };
		String F2P0colN[] = null;
	}

	public interface CSJ_X extends CSJ {
		// F2
		String F2jmbs[] = { "最小化", "取消" };
		String F2rfstr = UNION_ADDR + "SJ_X$SJF2P0";
		String F2tbN = null;
		// F2P0
		String F2P0jl1s = "%s·基本信息表";
		String F2P1jl2s = null;
		String F2P0jl3s[] = { "记录数：" };
		String F2P0jbs[] = { "添加" };
		String F2P0colN[] = null;
	}
}
