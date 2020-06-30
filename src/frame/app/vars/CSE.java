package frame.app.vars;

import javax.swing.JFrame;

import common.clz.Parameters;

public interface CSE extends Parameters {

	public JFrame run();

	// F1
	String F1tbN = null;

	// F1P0
	String F1P0jl2s = null;
	String F1P0mt2s = "备注：";
	String F1P0dmcv[][] = null;
	String F1P0jb1s[] = { "添加药品", "导入导出" };
	String F1P0jb2s[] = { "保存当前表格", "删除" };
	String F1P0jb3s[] = { "保存", "退出" };
	String F1P0colN[] = null;

	// F1P1
	String F1P1jtas = "说明：“待审核”的单据会使药品库存改变，不影响账务，允许修改。“已审核”的单据影响账务，不允许修改。";
	String F1P1jb1s[] = { "审核单据", "删除单据", "退出审核" };
	String F1P1jb2s =  "查询" ;
	String F1P1colN = null;
	String F1P1tbN = null;

	// F2
	String F2jbs[] = { "最小化", "返回" };

	// F2P0
	String F2P0jbs[] = { "修改", "通过审核" };
	String F2P0jl3s[] = { "记录数：%d", "总金额：%s" };

	public interface DH extends CSE {
		// F1
		String F1jbs[] = { "新建销售订货单", "审核销售订货单" };
		String F1rfstr = UNION_ADDR + "XS_DH$XSF1P";

		// F1P0
		String F1P0jl1s = "药品销售·订货单";
		String F1P0mt1s[] = { "应收金额：", "实收金额：", "待收金额：", "订单日期：" };
		String F1P0dmcs[] = { "客户名：", "出库仓库：", "经办人：" };

		// F1P1
		String F1P1jls = "药品销售·订货单审核";
		String F1P1mts[] = { "客户编号：", "仓库编号：" };
		String F1P1rfstr = UNION_ADDR + "XS_DH$XSF2";

		// F2
		String F2rfstr = UNION_ADDR + "XS_DH$XSF2P0";

		// F2P0
		String F2P0jl1s = "药品订货单清单";
		String F2P0jl2s = "订货单号：";
	}

	public interface JH extends CSE {
		// F1
		String F1jbs[] = { "新建采购进货单", "审核采购进货单" };
		String F1rfstr = UNION_ADDR + "CG_JH$CGF1P";

		// F1P0
		String F1P0jl1s = "药品采购·进货单";
		String F1P0mt1s[] = { "应付金额：", "实付金额：", "待付金额：", "订单日期：" };
		String F1P0dmcs[] = { "供货商名：", "入库仓库：", "经办人：" };

		// F1P1
		String F1P1jls = "药品采购·进货单审核";
		String F1P1mts[] = { "供货商编号：", "仓库编号：" };
		String F1P1rfstr = UNION_ADDR + "CG_JH$CGF2";

		// F2
		String F2rfstr = UNION_ADDR + "CG_JH$CGF2P0";

		// F2P0
		String F2P0jl1s = "药品采购单清单";
		String F2P0jl2s = "采购单号：";
	}

	public interface QH extends CSE {
		// F1
		String F1jbs[] = { "新建仓库缺货单" };
		String F1rfstr = UNION_ADDR + "CK_QH$CKF1P";

		// F1P0
		String F1P0jl1s = "药品库存·缺货单";
		String F1P0mt1s[] = { "订单日期：" };
		String F1P0dmcs[] = { "客户名：", "出库仓库：", "经办人：" };
	}
}
