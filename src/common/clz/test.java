package common.clz;

import java.sql.SQLException;
import common.support.*;

public class test extends ISQL{

	public static void main(String[] args) {
		System.out.println(getRange(new int[] {1, 2, 3, 4, 5}));;
	}

	public static int getRange(int aArray[]) {
		return getMax(aArray) - getMin(aArray);
	}
	
	public static int getMax(int[] a) {
		int max = 0;
		for (int i = 0; i < a.length; i++) {
			max = Integer.max(max, a[i]);
		}
		return max;
	}
	
	public static int getMin(int a[]) {
		int min = getMax(a);
		for(int i = 0; i < a.length; i ++) {
			min = Integer.min(min, a[i]);			
		}
		return min;
	}
	
	// ȡ���ж���
	public static String[][] rsDual(String sortCol[], int rows) {
		String[][] ssResult = new String[sortCol.length][rows];
		try {
			for (int i = 0; i < ssResult.length; i++) {
				// ��ÿһ��ȡ����
				ssResult[i] = rsMultiRows(sortCol[i], rows);
				rs.first();
			}
		} catch (SQLException e) {
			System.out.println(EXCEPTION_STRING);
			e.printStackTrace();
		}
		return UtilSupport.Transpose(ssResult);
	}

	public static void Test() {
		sql = "select * from %s where %s = '%s' %s";
		sql = String.format(sql, "CG_JH", "���״̬", "�����", "");
		String sResult[] = new String[doScrollSql(sql)];
		int i = 0;
		try {
			do {
				sResult[i++] = rs.getString("����").trim();
			} while (rs.next());
		} catch (SQLException e) {
			System.out.println(EXCEPTION_STRING);
			e.printStackTrace();
		}
	}
}
