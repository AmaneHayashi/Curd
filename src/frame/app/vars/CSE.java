package frame.app.vars;

import javax.swing.JFrame;

import common.clz.Parameters;

public interface CSE extends Parameters {

	public JFrame run();

	// F1
	String F1tbN = null;

	// F1P0
	String F1P0jl2s = null;
	String F1P0mt2s = "��ע��";
	String F1P0dmcv[][] = null;
	String F1P0jb1s[] = { "���ҩƷ", "���뵼��" };
	String F1P0jb2s[] = { "���浱ǰ���", "ɾ��" };
	String F1P0jb3s[] = { "����", "�˳�" };
	String F1P0colN[] = null;

	// F1P1
	String F1P1jtas = "˵����������ˡ��ĵ��ݻ�ʹҩƷ���ı䣬��Ӱ�����������޸ġ�������ˡ��ĵ���Ӱ�����񣬲������޸ġ�";
	String F1P1jb1s[] = { "��˵���", "ɾ������", "�˳����" };
	String F1P1jb2s =  "��ѯ" ;
	String F1P1colN = null;
	String F1P1tbN = null;

	// F2
	String F2jbs[] = { "��С��", "����" };

	// F2P0
	String F2P0jbs[] = { "�޸�", "ͨ�����" };
	String F2P0jl3s[] = { "��¼����%d", "�ܽ�%s" };

	public interface DH extends CSE {
		// F1
		String F1jbs[] = { "�½����۶�����", "������۶�����" };
		String F1rfstr = UNION_ADDR + "XS_DH$XSF1P";

		// F1P0
		String F1P0jl1s = "ҩƷ���ۡ�������";
		String F1P0mt1s[] = { "Ӧ�ս�", "ʵ�ս�", "���ս�", "�������ڣ�" };
		String F1P0dmcs[] = { "�ͻ�����", "����ֿ⣺", "�����ˣ�" };

		// F1P1
		String F1P1jls = "ҩƷ���ۡ����������";
		String F1P1mts[] = { "�ͻ���ţ�", "�ֿ��ţ�" };
		String F1P1rfstr = UNION_ADDR + "XS_DH$XSF2";

		// F2
		String F2rfstr = UNION_ADDR + "XS_DH$XSF2P0";

		// F2P0
		String F2P0jl1s = "ҩƷ�������嵥";
		String F2P0jl2s = "�������ţ�";
	}

	public interface JH extends CSE {
		// F1
		String F1jbs[] = { "�½��ɹ�������", "��˲ɹ�������" };
		String F1rfstr = UNION_ADDR + "CG_JH$CGF1P";

		// F1P0
		String F1P0jl1s = "ҩƷ�ɹ���������";
		String F1P0mt1s[] = { "Ӧ����", "ʵ����", "������", "�������ڣ�" };
		String F1P0dmcs[] = { "����������", "���ֿ⣺", "�����ˣ�" };

		// F1P1
		String F1P1jls = "ҩƷ�ɹ������������";
		String F1P1mts[] = { "�����̱�ţ�", "�ֿ��ţ�" };
		String F1P1rfstr = UNION_ADDR + "CG_JH$CGF2";

		// F2
		String F2rfstr = UNION_ADDR + "CG_JH$CGF2P0";

		// F2P0
		String F2P0jl1s = "ҩƷ�ɹ����嵥";
		String F2P0jl2s = "�ɹ����ţ�";
	}

	public interface QH extends CSE {
		// F1
		String F1jbs[] = { "�½��ֿ�ȱ����" };
		String F1rfstr = UNION_ADDR + "CK_QH$CKF1P";

		// F1P0
		String F1P0jl1s = "ҩƷ��桤ȱ����";
		String F1P0mt1s[] = { "�������ڣ�" };
		String F1P0dmcs[] = { "�ͻ�����", "����ֿ⣺", "�����ˣ�" };
	}
}
