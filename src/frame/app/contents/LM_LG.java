package frame.app.contents;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import common.support.SQLSupport;
import frame.app.vars.CLM;
import frame.origin.contents.FL;

public class LM_LG extends FL implements CLM.LG {

	private static final long serialVersionUID = -4324448828998614663L;

	@Override
	public void init() {
		jl1s = FLjl1s;
		jl2s = FLjl2s;
		jbs = FLjbs;
		mts = FLmts;
		mps = FLmps;
	}

	@Override
	// ��¼�жϺ���
	public void LoginJudgement() {
		// �õ�����ֵ
		String usr = mt.getTextField().getText();
		// �õ�������ֵ
		String pwd = new String(mp.getPasswordField().getPassword()).trim();
		// �ж�����ֵ�Ƿ�Ϊ��
		if (usr.trim().isEmpty() || pwd.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "�������û��������룡", "��ʾ", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// ִ�в�ѯ��sqlSearch(������������������ֵ������������������ֵ);
		if (SQLSupport.sqlSearch("Usr", "�û���", usr, "����", pwd, "")) {
			// ���������ȷ��ʵ������ҳ�棬����Ȩ�ޣ���ת����ҳ��
			permission = SQLSupport.sqlGetUniqueValue("Usr", "�û���", usr, "Ȩ��", "");
			this.setVisible(false);
			new LM_MA().addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					// ����ҳ��رգ�ע���������ֶ��������ã�����������Ϊ�ɼ�
					jb[1].doClick();
					setVisible(true);
					mt.getTextField().requestFocus();
				}
			});
		} else {
			// ��������������ʾ
			System.out.println(SQLSupport.sqlGetUniqueValue("Usr", "*", "�û���", usr, "����"));
			JOptionPane.showMessageDialog(null, "�����û������������벻��ȷ������������!", "��ʾ", JOptionPane.ERROR_MESSAGE);
		}
	}
}
