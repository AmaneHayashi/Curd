package common.clz;

import java.awt.Color;
import java.awt.Font;

public interface Parameters {

	// 窗体位置参数
	int WINDOW_DEFAULT = 0;// 默认窗体
	int WINDOW_SCREEN_CENTER = 1;// 窗体在屏幕中央
	int WINDOW_PARENT_CENTER = 2;// 窗体在父窗体中央

	// 布局参数
	int OPAQUE_TYPE = 0;// 透明边界布局
	int NULL_TYPE = 1;// 空布局
	int GRID_TYPE = 2;// 透明网格布局
	int FLOW_TYPE = 3;// 透明流动布局

	// 窗体透明参数
	double TRANSPARENT_VALUE = 0.08;

	// 移动步长参数
	int STRIDE_VALUE = 8;

	// 窗体布局参数
	int GRID_HGAP = 10;
	int GRID_VGAP = 10;
	int BORDER_HGAP = 30;
	int BORDER_VGAP = 10;

	// 字体参数
	Font fSTB18 = new Font("华文宋体", Font.BOLD, 18);
	Font fYHP20 = new Font("微软雅黑", Font.PLAIN, 20);
	Font fZSB52 = new Font("华文中宋", Font.BOLD, 52);
	Font fZSB36 = new Font("华文中宋", Font.BOLD, 36);
	Font fXHP40 = new Font("华文细黑", Font.PLAIN, 40);
	Font fXHB36 = new Font("华文细黑", Font.BOLD, 36);
	Font fXHP14 = new Font("华文细黑", Font.PLAIN, 14);
	Font fFSB28 = new Font("华文仿宋", Font.BOLD, 28);

	// 颜色参数
	Color cHEAD_F1 = new Color(229, 209, 250);// 父布局表头颜色
	Color cQIAN_F1 = new Color(243, 241, 252);// 父布局表格交叉浅色
	Color cSHEN_F1 = new Color(240, 226, 255);// 父布局表格交叉深色

	Color cHEAD_F2 = new Color(159, 203, 251);// 子布局表头颜色
	Color cSHEN_F2 = new Color(189, 227, 241);// 子布局表格交叉深色
	Color cQIAN_F2 = new Color(245, 249, 255);// 子布局表格交叉浅色

	// 图片路径
	String LOGIN_PIC_PATH = "src/dorimi.jpg";// 登录页面背景图
	String MAIN_PIC_PATH = "src/curd0.jpg";// 主页面背景图
	String F1_PIC_PATH = "src/curd.jpg";// 父页面背景图
	String F2_PIC_PATH = "src/sky.jpg";// 子页面背景图

	// 实例化类的包目录
	String UNION_ADDR = "frame.app.contents.";

	// 总菜单
	String MENU[] = { "基本(F)", "销售(S)", "仓库(R)", "采购(P)", "数据管理(D)" };

	// 总表管理,销售+商品+采购+数据+财务
	String CURD[][] = { { "管理", "GL" }, // (0, 0), (0, 1)
			{ "销售", "XS" }, // (1, 0), (1, 1)
			{ "仓库", "CK" }, // (2, 0), (2, 1)
			{ "采购", "CG" }, // (3, 0), (3, 1)
			{ "数据", "SJ" }// ,// (4, 0), (4, 1)
	};

	// 子菜单
	String MENUf[][] = { { "注销", "最小化", "退出" }, { "订货单", "发货单", "订货单查询", "订货单子单查询", "发货单查询" },
			{ "入库单", "出库单", "缺货单", "入库单查询", "出库单查询", "缺货单查询", "缺货单子单查询" }, { "进货单", "进货单查询", "进货单子单查询" },
			{ "供应商管理", "客户管理", "员工管理", "药品管理", "仓库管理" } };

	// 副表管理,销售+商品+采购+数据+财务
	String CURDf[][] = { { "THIS IS A BAC-DAC PROJECT" }, // GL
			{ "DH", "FH", "CDH", "CDH_X", "CFH", }, // XS
			{ "RK", "CK", "QH", "CRK", "CCK", "CQH", "CQH_X" }, // SP
			{ "JH", "CJH", "CJH_X" }, // CG
			{ "GYS", "KH", "YG", "YP", "CK" } // SJ
	};

	char MNEMONICS[] = { 'f', 's', 'r', 'p', 'd', 'f', 'h' };

	// 审核状态
	String DELETED = "已删除";
	String OVERDUE = "已失效";
	String VERIFING = "未审核";
	String VERIFIED = "已审核";
	String ASSOCIATING = "未关联";
	String ASSOCIATED = "已关联";
	String CHECKOUTING = "待出库";

	// float类型的小数点长度
	int FLOAT_SCALE = 2;
}
