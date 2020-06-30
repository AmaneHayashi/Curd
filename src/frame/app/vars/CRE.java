package frame.app.vars;

import javax.swing.JFrame;

import common.clz.Parameters;

public interface CRE extends Parameters {

	public JFrame run();

	// F1
	String F1tbN = null;

	// F1P0
	String F1P0jl2s = null;
	String F1P0mt2s = "��ע��";
	String F1P0dmcv[][] = null;
	String F1P0jb1s[] = { "���뵼��" };
	String F1P0jb2s[] = null;
	String F1P0jb3s[] = { "����", "�˳�" };
	String F1P0colN[] = null;

	// F1P1
	String F1P1mts[] = { "�������ţ�", "�ֿ��ţ�" };
	String F1P1jtas = "˵����������ˡ��ĵ��ݻ�ʹҩƷ���ı䣬��Ӱ�����������޸ġ�������ˡ��ĵ���Ӱ�����񣬲������޸ġ�";
	String F1P1jb1s[] = { "��˵���", "ɾ������", "�˳����" };
	String F1P1jb2s = "��ѯ";
	String F1P1colN = null;
	String F1P1tbN = null;

	// F2
	String F2jbs[] = { "��С��", "����" };

	// F2P0
	String F2P0jbs[] = { "ͨ�����" };
	String F2P0jl3s[] = { "��¼����%d", "�ܽ�%s" };

	public interface RK extends CRE {
		// F1
		String F1jbs[] = { "�½��ֿ���ⵥ", "��˲ֿ���ⵥ" };
		String F1rfstr = UNION_ADDR + "CK_RK$CKF1P";

		// F1P0
		String F1P0jl1s = "ҩƷ��桤��ⵥ";
		String F1P0mt1s[] = { "Ӧ����", "ʵ����", "������", "�������ڣ�" };
		String F1P0dmcs[] = { "�������ţ�", "���ֿ⣺", "�����ˣ�" };

		// F1P1
		String F1P1jls = "ҩƷ��桤��ⵥ���";
		String F1P1rfstr = UNION_ADDR + "CK_RK$CKF2";

		// F2
		String F2rfstr = UNION_ADDR + "CK_RK$CKF2P0";

		// F2P0
		String F2P0jl1s = "ҩƷ��ⵥ�嵥";
		String F2P0jl2s = "��ⵥ�ţ�";
	}

	public interface CK extends CRE {
		// F1
		String F1jbs[] = { "�½��ֿ���ⵥ", "��˲ֿ���ⵥ" };
		String F1rfstr = UNION_ADDR + "CK_CK$CKF1P";

		// F1P0
		String F1P0jl1s = "ҩƷ��桤���ⵥ";
		String F1P0mt1s[] = { "Ӧ�ս�", "ʵ�ս�", "���ս�", "�������ڣ�" };
		String F1P0dmcs[] = { "�������ţ�", "����ֿ⣺", "�����ˣ�" };

		// F1P1
		String F1P1jls = "ҩƷ��桤���ⵥ���";
		String F1P1rfstr = UNION_ADDR + "CK_CK$CKF2";

		// F2
		String F2rfstr = UNION_ADDR + "CK_CK$CKF2P0";

		// F2P0
		String F2P0jl1s = "ҩƷ���ⵥ�嵥";
		String F2P0jl2s = "���ⵥ�ţ�";
	}

	public interface FH extends CRE {
		// F1
		String F1jbs[] = { "�½����۷�����", "������۷�����" };
		String F1rfstr = UNION_ADDR + "XS_FH$XSF1P";

		// F1P0
		String F1P0jl1s = "ҩƷ���ۡ�������";
		String F1P0mt1s[] = { "Ӧ�ս�", "ʵ�ս�", "���ս�", "�������ڣ�" };
		String F1P0dmcs[] = { "�������ţ�", "�ͻ�����", "�����ˣ�" };

		// F1P1
		String F1P1mts[] = { "�������ţ�", "�ͻ���ţ�" };
		String F1P1jls = "ҩƷ���ۡ����������";
		String F1P1rfstr = UNION_ADDR + "XS_FH$XSF2";

		// F2
		String F2rfstr = UNION_ADDR + "XS_FH$XSF2P0";

		// F2P0
		String F2P0jl1s = "ҩƷ�������嵥";
		String F2P0jl2s = "�������ţ�";
	}
}
