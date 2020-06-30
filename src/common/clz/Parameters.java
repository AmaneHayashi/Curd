package common.clz;

import java.awt.Color;
import java.awt.Font;

public interface Parameters {

	// ����λ�ò���
	int WINDOW_DEFAULT = 0;// Ĭ�ϴ���
	int WINDOW_SCREEN_CENTER = 1;// ��������Ļ����
	int WINDOW_PARENT_CENTER = 2;// �����ڸ���������

	// ���ֲ���
	int OPAQUE_TYPE = 0;// ͸���߽粼��
	int NULL_TYPE = 1;// �ղ���
	int GRID_TYPE = 2;// ͸�����񲼾�
	int FLOW_TYPE = 3;// ͸����������

	// ����͸������
	double TRANSPARENT_VALUE = 0.08;

	// �ƶ���������
	int STRIDE_VALUE = 8;

	// ���岼�ֲ���
	int GRID_HGAP = 10;
	int GRID_VGAP = 10;
	int BORDER_HGAP = 30;
	int BORDER_VGAP = 10;

	// �������
	Font fSTB18 = new Font("��������", Font.BOLD, 18);
	Font fYHP20 = new Font("΢���ź�", Font.PLAIN, 20);
	Font fZSB52 = new Font("��������", Font.BOLD, 52);
	Font fZSB36 = new Font("��������", Font.BOLD, 36);
	Font fXHP40 = new Font("����ϸ��", Font.PLAIN, 40);
	Font fXHB36 = new Font("����ϸ��", Font.BOLD, 36);
	Font fXHP14 = new Font("����ϸ��", Font.PLAIN, 14);
	Font fFSB28 = new Font("���ķ���", Font.BOLD, 28);

	// ��ɫ����
	Color cHEAD_F1 = new Color(229, 209, 250);// �����ֱ�ͷ��ɫ
	Color cQIAN_F1 = new Color(243, 241, 252);// �����ֱ�񽻲�ǳɫ
	Color cSHEN_F1 = new Color(240, 226, 255);// �����ֱ�񽻲���ɫ

	Color cHEAD_F2 = new Color(159, 203, 251);// �Ӳ��ֱ�ͷ��ɫ
	Color cSHEN_F2 = new Color(189, 227, 241);// �Ӳ��ֱ�񽻲���ɫ
	Color cQIAN_F2 = new Color(245, 249, 255);// �Ӳ��ֱ�񽻲�ǳɫ

	// ͼƬ·��
	String LOGIN_PIC_PATH = "src/dorimi.jpg";// ��¼ҳ�汳��ͼ
	String MAIN_PIC_PATH = "src/curd0.jpg";// ��ҳ�汳��ͼ
	String F1_PIC_PATH = "src/curd.jpg";// ��ҳ�汳��ͼ
	String F2_PIC_PATH = "src/sky.jpg";// ��ҳ�汳��ͼ

	// ʵ������İ�Ŀ¼
	String UNION_ADDR = "frame.app.contents.";

	// �ܲ˵�
	String MENU[] = { "����(F)", "����(S)", "�ֿ�(R)", "�ɹ�(P)", "���ݹ���(D)" };

	// �ܱ����,����+��Ʒ+�ɹ�+����+����
	String CURD[][] = { { "����", "GL" }, // (0, 0), (0, 1)
			{ "����", "XS" }, // (1, 0), (1, 1)
			{ "�ֿ�", "CK" }, // (2, 0), (2, 1)
			{ "�ɹ�", "CG" }, // (3, 0), (3, 1)
			{ "����", "SJ" }// ,// (4, 0), (4, 1)
	};

	// �Ӳ˵�
	String MENUf[][] = { { "ע��", "��С��", "�˳�" }, { "������", "������", "��������ѯ", "�������ӵ���ѯ", "��������ѯ" },
			{ "��ⵥ", "���ⵥ", "ȱ����", "��ⵥ��ѯ", "���ⵥ��ѯ", "ȱ������ѯ", "ȱ�����ӵ���ѯ" }, { "������", "��������ѯ", "�������ӵ���ѯ" },
			{ "��Ӧ�̹���", "�ͻ�����", "Ա������", "ҩƷ����", "�ֿ����" } };

	// �������,����+��Ʒ+�ɹ�+����+����
	String CURDf[][] = { { "THIS IS A BAC-DAC PROJECT" }, // GL
			{ "DH", "FH", "CDH", "CDH_X", "CFH", }, // XS
			{ "RK", "CK", "QH", "CRK", "CCK", "CQH", "CQH_X" }, // SP
			{ "JH", "CJH", "CJH_X" }, // CG
			{ "GYS", "KH", "YG", "YP", "CK" } // SJ
	};

	char MNEMONICS[] = { 'f', 's', 'r', 'p', 'd', 'f', 'h' };

	// ���״̬
	String DELETED = "��ɾ��";
	String OVERDUE = "��ʧЧ";
	String VERIFING = "δ���";
	String VERIFIED = "�����";
	String ASSOCIATING = "δ����";
	String ASSOCIATED = "�ѹ���";
	String CHECKOUTING = "������";

	// float���͵�С���㳤��
	int FLOAT_SCALE = 2;
}
