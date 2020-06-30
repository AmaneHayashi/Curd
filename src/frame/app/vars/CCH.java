package frame.app.vars;

import javax.swing.JFrame;

import common.clz.Parameters;

public interface CCH extends Parameters {

	public JFrame run();

	// F2
	String F2jbs[] = { "最小化", "返回" };
	String F2rfstr = UNION_ADDR + "CG_CJH$CGF2P1";
	String F2shN = null;
	// F2P1
	String F2P1jl1s = null;
	String F2P1jl2s = "记录数：";
	String F2P1mts[] = { "待查属性值（属性I）：", "待查属性值（属性II）：" };
	String F2P1jbs = "开始查询";
	String F2P1dmcs[] = { "请选择需要查询的属性（属性I）：", "请选择需要查询的属性（属性II）：" };
	String F2P1dmcv[] = null;
	String F2P1colN[] = null;
	String F2P1shN = null;

}
