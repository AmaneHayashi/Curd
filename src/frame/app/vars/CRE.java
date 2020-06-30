package frame.app.vars;

import javax.swing.JFrame;

import common.clz.Parameters;

public interface CRE extends Parameters {

	public JFrame run();

	// F1
	String F1tbN = null;

	// F1P0
	String F1P0jl2s = null;
	String F1P0mt2s = "备注：";
	String F1P0dmcv[][] = null;
	String F1P0jb1s[] = { "导入导出" };
	String F1P0jb2s[] = null;
	String F1P0jb3s[] = { "保存", "退出" };
	String F1P0colN[] = null;

	// F1P1
	String F1P1mts[] = { "关联单号：", "仓库编号：" };
	String F1P1jtas = "说明：“待审核”的单据会使药品库存改变，不影响账务，允许修改。“已审核”的单据影响账务，不允许修改。";
	String F1P1jb1s[] = { "审核单据", "删除单据", "退出审核" };
	String F1P1jb2s = "查询";
	String F1P1colN = null;
	String F1P1tbN = null;

	// F2
	String F2jbs[] = { "最小化", "返回" };

	// F2P0
	String F2P0jbs[] = { "通过审核" };
	String F2P0jl3s[] = { "记录数：%d", "总金额：%s" };

	public interface RK extends CRE {
		// F1
		String F1jbs[] = { "新建仓库入库单", "审核仓库入库单" };
		String F1rfstr = UNION_ADDR + "CK_RK$CKF1P";

		// F1P0
		String F1P0jl1s = "药品库存·入库单";
		String F1P0mt1s[] = { "应付金额：", "实付金额：", "待付金额：", "订单日期：" };
		String F1P0dmcs[] = { "关联单号：", "入库仓库：", "经办人：" };

		// F1P1
		String F1P1jls = "药品库存·入库单审核";
		String F1P1rfstr = UNION_ADDR + "CK_RK$CKF2";

		// F2
		String F2rfstr = UNION_ADDR + "CK_RK$CKF2P0";

		// F2P0
		String F2P0jl1s = "药品入库单清单";
		String F2P0jl2s = "入库单号：";
	}

	public interface CK extends CRE {
		// F1
		String F1jbs[] = { "新建仓库出库单", "审核仓库出库单" };
		String F1rfstr = UNION_ADDR + "CK_CK$CKF1P";

		// F1P0
		String F1P0jl1s = "药品库存·出库单";
		String F1P0mt1s[] = { "应收金额：", "实收金额：", "待收金额：", "订单日期：" };
		String F1P0dmcs[] = { "关联单号：", "出库仓库：", "经办人：" };

		// F1P1
		String F1P1jls = "药品库存·出库单审核";
		String F1P1rfstr = UNION_ADDR + "CK_CK$CKF2";

		// F2
		String F2rfstr = UNION_ADDR + "CK_CK$CKF2P0";

		// F2P0
		String F2P0jl1s = "药品出库单清单";
		String F2P0jl2s = "出库单号：";
	}

	public interface FH extends CRE {
		// F1
		String F1jbs[] = { "新建销售发货单", "审核销售发货单" };
		String F1rfstr = UNION_ADDR + "XS_FH$XSF1P";

		// F1P0
		String F1P0jl1s = "药品销售·发货单";
		String F1P0mt1s[] = { "应收金额：", "实收金额：", "待收金额：", "订单日期：" };
		String F1P0dmcs[] = { "关联单号：", "客户名：", "经办人：" };

		// F1P1
		String F1P1mts[] = { "关联单号：", "客户编号：" };
		String F1P1jls = "药品销售·发货单审核";
		String F1P1rfstr = UNION_ADDR + "XS_FH$XSF2";

		// F2
		String F2rfstr = UNION_ADDR + "XS_FH$XSF2P0";

		// F2P0
		String F2P0jl1s = "药品发货单清单";
		String F2P0jl2s = "发货单号：";
	}
}
