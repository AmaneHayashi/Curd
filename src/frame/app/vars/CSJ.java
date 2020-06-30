package frame.app.vars;

import javax.swing.JFrame;

import common.clz.Parameters;

public interface CSJ extends Parameters {

	public JFrame run();

	public interface CSJ_C extends CSJ {
		// F2
		String F2jbs[] = { "��С��", "����", "���沢�˳�" };
		String F2rfstr = UNION_ADDR + "SJ$SJF2P0";
		String F2tbN = null;

		// F2P0
		String F2P0jl1s = "%s��������Ϣ��";
		String F2P0jl2s = null;
		String F2P0jl3s[] = { "��¼����" };
		String F2P0jbs[] = { "����", "�޸�", "ɾ��" };
		String F2P0colN[] = null;
	}

	public interface CSJ_X extends CSJ {
		// F2
		String F2jmbs[] = { "��С��", "ȡ��" };
		String F2rfstr = UNION_ADDR + "SJ_X$SJF2P0";
		String F2tbN = null;
		// F2P0
		String F2P0jl1s = "%s��������Ϣ��";
		String F2P1jl2s = null;
		String F2P0jl3s[] = { "��¼����" };
		String F2P0jbs[] = { "���" };
		String F2P0colN[] = null;
	}
}
