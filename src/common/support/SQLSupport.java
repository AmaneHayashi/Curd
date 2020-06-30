package common.support;

import java.sql.SQLException;

import common.clz.ISQL;
import my.comps.MyDtm;
import my.comps.MyTable;

/**
 * {@code SQLSupport}��SQL֧���࣬��{@code SQLTest}�̳У�
 * 
 * <p>
 * SQL֧������Ҫ�ṩ������SQL��ѯ�����¡���ա�����Ⱥ������ȿ�ֱ�ӶԷ���ֵ���д���Ҳ�����
 * <code>JTable(TableModel)</code>�Ľ��������
 * <p>
 * ���ǵ�SQL������ʹ�����ԣ����鱾���µĺ�����ʹ�þ�̬���á�
 * <p>
 * ����ؽ���SQL֧����ĺ�����Ҫ�������·��棺
 * 
 * <UL>
 * <LI>SQL���ԣ�<code>Test</code>
 * <LI>SQL��ѯ��<code>sqlSearch</code>
 * <LI>
 * SQLȡֵ��<code>sqlGetUniqueValue, sqlGetValue, sqlGetMultiValue, sqlFillTable, sqlGetTableHeader,
 *   sqlAutoLabel, sqlGetQualifiedRows, sqlAutoDmc</code>
 * <LI>SQL���£�<code>sqlUpdate, sqlRenewTable</code>
 * <LI>SQLɾ����<code>sqlDelete, sqlDeteleTable</code>
 * <LI>SQL���룺<code>sqlWrite, sqlWriteTable</code>\
 * </UL>
 * 
 * @author Amane Hayashi
 * @since 1.0
 */
public class SQLSupport extends ISQL {

	/**
	 * {@code SQLSupport}���е�SQL������
	 */
	private static String sql = null;

	/*
	 * public static void main(String[] args) { Test(); }
	 */

	/**
	 * SQL���ԣ�˳�����{@code Test}���е�һ�е�ֵ��
	 * <p>
	 * SQL��䣺
	 * 
	 * <pre>
	 * {@code
	 * select * from Test where 1 = '1' }
	 * </pre>
	 * 
	 * <p>
	 * ע��<code>where 1 = '1'</code>�൱��û��ѡ�񣬼��ȼ��ڣ�<code>select * from Test</code>����ͬ��
	 * 
	 * @return void
	 * @throws SQLException
	 */

	public static void Test() {
		sql = "select * from %s where %s = '%s' %s";
		try {
			sql = String.format(sql, "Test", "1", "1", "");
			doSql(sql);
			do {
				System.out.println("��������" + rs.getString(1));
			} while (rs.next());
		} catch (SQLException e) {
			System.out.println(EXCEPTION_STRING);
			e.printStackTrace();
		}
	}

	/**
	 * SQL��ѯ����{@code T}���Ƿ����һ�У���{@code keyCol}�е�ֵΪ{@code keyVal}�������ڣ�{@code keyVal}��Ӧ�����У�
	 * {@code sortCol}�е�ֵ�Ƿ����{@code soreVal}����
	 * <p>
	 * SQL��䣺
	 * 
	 * <pre>
	 * {@code
	 * select * from T where keyCol = 'keyVal' moreCond }
	 * </pre>
	 * 
	 * <p>
	 * ע��{@code moreCond}������SQL�������Ӹ������������ͬ���磺
	 * <UL>
	 * <LI>{@code order by 1}�����ʾ���������һ������SQL���Ϊ��
	 * 
	 * <pre>
	 * {@code
	 * select * from T where keyCol = 'keyVal' order by 1}
	 * </pre>
	 * 
	 * <LI>{@code and keyCol2 = 'keyVal2'}�����ʾ��������ѯ��SQL���Ϊ��
	 * 
	 * <pre>
	 * {@code
	 * select * from T where keyCol = 'keyVal' and keyCol2 = 'keyVal2'}
	 * </pre>
	 * </UL>
	 * 
	 * @param T        ����
	 * @param keyCol   ������
	 * @param keyVal   ����ֵ
	 * @param sortCol  ��������
	 * @param sortVal  ������ֵ
	 * @param moreCond ��������
	 * @return ���{@code sortCol}�е�ֵ����{@code sortVal}������{@code true}������{����@code
	 *         false}
	 * @throws SQLException
	 */

	public static boolean sqlSearch(String T, String keyCol, String keyVal, String sortCol, String sortVal,
			String moreCond) {
		sql = "select * from %s where %s = '%s' %s";
		sql = String.format(sql, T, keyCol, keyVal, moreCond);
		System.out.println("SQL��ѯ��䣺" + sql);
		try {
			doSql(sql);
			do {
				return rs.getString(sortCol).trim().equals(sortVal) ? true : false;
			} while (rs.next());
		} catch (SQLException e) {
			System.out.println(EXCEPTION_STRING);
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * SQLȡֵ����{@code T}���Ƿ����һ�У���{@code keyCol}�е�ֵΪ{@code keyVal}�������ڣ�ȡ��{@code keyVal}��Ӧ�����У�
	 * {@code sortCol}�е�ֵ��
	 * <p>
	 * SQL��䣺
	 * 
	 * <pre>
	 * {@code
	 * select * from T where keyCol = 'keyVal' moreCond }
	 * </pre>
	 * 
	 * <p>
	 * ע�������������ڶ��е��е�ȡֵ��
	 * 
	 * @param T        ����
	 * @param keyCol   ������
	 * @param keyVal   ����ֵ
	 * @param sortCol  ��������
	 * @param sortVal  ������ֵ
	 * @param moreCond ��������
	 * @return {@code keyVal}��Ӧ�����У�{@code sortCol}�е�ֵ
	 * @throws SQLException
	 */

	public static String sqlGetUniqueValue(String T, String keyCol, String keyVal, String sortCol, String moreCond) {
		sql = "select * from %s where %s = '%s' %s";
		sql = String.format(sql, T, keyCol, keyVal, moreCond);
		System.out.println("SQL���е���ȡֵ��" + sql);
		try {
			doSql(sql);
			return rs.getString(sortCol).trim();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_STRING);
			// e.printStackTrace();
			return null;
		}
	}

	/**
	 * SQLȡֵ������T���keyCol�����Ƿ���keyVal������У�����keyVal��Ӧ�����У�sortCol�е�ֵ��
	 * [һ��ȡ��һ���һ��ֵ�������У�����<�����������й�>���������ѯ��������1������moreCond��д��]
	 * 
	 */

	public static Object sqlGetValue(String T, String keyCol, String keyVal, String sortCol, String moreCond) {
		sql = "select * from %s where %s = '%s' %s";
		sql = String.format(sql, T, keyCol, keyVal, moreCond);
		System.out.println("SQL�����е���ȡֵ��" + sql);
		try {
			int row = doScrollSql(sql);
			if (row == 1) {
				return rs.getString(sortCol).trim();
			} else {
				rs.first();
				return rsMultiRows(sortCol, row);
			}
		} catch (SQLException e) {
			System.out.println(EXCEPTION_STRING);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * SQLȡֵ������T���keyCol�����Ƿ���keyVal������У�����keyVal��Ӧ�����У�sortCol�е�ֵ��
	 * [һ��ȡ��һ��ֵ�����У�����<��������>���������ѯ��������1������moreCond��д��]
	 * 
	 */

	public static String[] sqlGetValue(String T, String keyCol, String keyVal, Object sortCol, String moreCond) {
		sql = "select * from %s where %s = '%s' %s";
		sql = String.format(sql, T, keyCol, keyVal, moreCond);
		System.out.println("SQL���ж���ȡֵ��" + sql);
		doSql(sql);
		try {
			rs.first();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_STRING);
			e.printStackTrace();
		}
		return rsMultiCols(sortCol);
	}

	/**
	 * SQLȡֵ������T���keyCol�����Ƿ���keyVal������У�����keyVal��Ӧ�����У�sortCol�е�ֵ��
	 * [һ��ȡ����άֵ�������У�����<�����������й�>���������ѯ��������1������moreCond��д��]
	 * 
	 */

	public static Object sqlGetMultiValue(String T, String keyCol, String keyVal, Object sortCol, String moreCond) {
		sql = "select * from %s where %s = '%s' %s";
		sql = String.format(sql, T, keyCol, keyVal, moreCond);
		System.out.println("SQL�����ж���ȡֵ��" + sql);
		try {
			int row = doScrollSql(sql);
			rs.first();
			if (row == 1) {
				// ��ʱΪ���ж���
				return rsMultiCols(sortCol);
			} else {
				return rsDual(sortCol, row);
			}
		} catch (SQLException e) {
			System.out.println(EXCEPTION_STRING);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * �������
	 */

	public static Object sqlGetMultiValue(String pT, String fT, String fKey, String xKey, String xValue) {
		String pCol = String.format("%s.%s", pT, fKey);
		String fCol = String.format("%s.%s", fT, fKey);
		String xCol = String.format("%s.%s", fT, xKey);
		sql = "select * from %s where %s in (select %s from %s where %s =%s AND %s ='%s')";
		sql = String.format(sql, pT, pCol, fCol, fT, pCol, fCol, xCol, xValue);
		System.out.println("SQL���������:" + sql);
		try {
			int row = doScrollSql(sql);
			rs.first();
			if (row == 1) {
				return rsMultiCols(rsmd.getColumnCount());
			} else {
				return rsDual(rsmd.getColumnCount(), row);
			}
		} catch (SQLException e) {
			System.out.println(EXCEPTION_STRING);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * SQL����
	 */

	public static boolean sqlUpdate(String T, String updCol, String updVal, String keyCol, String keyVal,
			String moreCond) {
		sql = "update %s set %s = '%s' where %s = '%s' %s";
		sql = String.format(sql, T, updCol, updVal, keyCol, keyVal, moreCond);
		System.out.println("SQL���£���ֵ���£���" + sql);
		doUpdSql(sql);
		return true;
	}

	/**
	 * SQL�����
	 * <p>
	 * �����з�������������д��SQL
	 */

	public static void sqlInsert(String T, String[] str) {
		for (int i = 0; i < str.length; i++) {
			str[i] = String.format("'%s'", str[i]);
		}
		sql = "insert into %s values(%s)";
		sql = String.format(sql, T, UtilSupport.array2str(str));
		System.out.println("SQL���£�һ��ֵ���£�:" + sql);
		doUpdSql(sql);
	}

	/**
	 * SQLɾ����
	 * <p>
	 * �����з�������������ɾ��SQL
	 */

	public static void sqlDelete(String T, String delCol, String delValue, String moreCond) {
		sql = "delete from %s where %s = '%s' %s";
		sql = String.format(sql, T, delCol, delValue, moreCond);
		System.out.println("SQLɾ����һ��ֵɾ����:" + sql);
		doUpdSql(sql);
	}

	/**
	 * SQL��ȡ����Ҫ����б�
	 */

	public static int sqlGetQualifiedRows(String T, String keyCol, String keyVal, String moreCond) {
		sql = "select * from %s where %s = '%s' %s";
		sql = String.format(sql, T, keyCol, keyVal, moreCond);
		System.out.println("SQLѰ�ҷ���Ҫ�������:" + sql);
		return doScrollSql(sql);
	}

	/**
	 * SQL��ȡ��ǰ(��һ��Ӧ��д)�ı��
	 * 
	 * @throws SQLException
	 */

	public static int sqlGetLabel(String T) {
		System.out.println("SQL��ȡ���б��:\t");
		sqlGetUniqueValue(T, "1", "1", "���к�", "order by 1");
		try {
			rs.last();
			return Integer.parseInt(rs.getString("���к�")) + 1;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_STRING);
			// e.printStackTrace();
			return 1;
		}
	}

	/**
	 * SQLȡ��ͷ
	 */

	public static String[] sqlGetColN(String T) {
		sql = "select * from %s";
		sql = String.format(sql, T);
		System.out.println("SQL��ȡ��ͷ��" + sql);
		try {
			doSql(sql);
			String str[] = new String[rsmd.getColumnCount()];
			for (int i = 0; i < rsmd.getColumnCount(); i++)
				str[i] = rsmd.getColumnName(i + 1);
			return str;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_STRING);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * SQL�Զ����һ�ű����ݿ��еı������������Ҫ��һ�£�
	 */

	public static void sqlFillTable(String T, MyTable mtb, String moreCond) {
		MyDtm dtm = mtb.getDtm();
		// ������
		dtm.removeAll();
		System.out.println("SQLȫ����䣺\t");
		// ִ�в�ѯ
		try {
			dtm.addDual((String[][]) sqlGetMultiValue(T, "1", "1", mtb.getColN(), moreCond));
		} catch (ClassCastException e) {
			dtm.addRow(sqlGetValue(T, "1", "1", mtb.getColN(), moreCond));
		}
	}

	/**
	 * SQL��д��
	 */

	public static void sqlRenewTable(String T, MyTable mtb) {
		System.out.println("SQL���£���дSQL����\t");
		sqlDeleteTable(T);
		sqlWriteTable(T, mtb.getDtm());
	}

	/**
	 * SQL��������ű���ͬ��
	 */

	public static void sqlWriteTable(String T, MyDtm dtm) {
		System.out.println("SQL���£�����һ�ű�:\t");
		for (int i = 0; i < dtm.getRowCount(); i++) {
			sqlInsert(T, dtm.getRowValue(i));
		}
	}

	/**
	 * SQL��ձ�
	 * <p>
	 * ��ɾ����ֻ�������
	 */

	public static void sqlDeleteTable(String T) {
		sql = "truncate table %s";
		sql = String.format(sql, T);
		System.out.println("SQL���һ�ű�:" + sql);
		doUpdSql(sql);
	}

	/**
	 * SQL���JComboBox������
	 */

	// CLASS CAST EXCEPTION!
	public static String[] sqlFillDmc(String T, String keyCol, String keyVal, String moreCond, int[] inds) {
		String str = "";
		try {
			String[][] ssValue = (String[][]) sqlGetMultiValue(T, keyCol, keyVal, UtilSupport.getMax(inds) + 1,
					moreCond);
			for (String s[] : ssValue) {
				String arg = s[inds[0]];
				String args = "";
				for (int i = 1; i < inds.length; i++) {
					args += s[inds[i]] + ",";
				}
				args = args.substring(0, args.length() - 1);
				str += String.format("%s(%s)", arg, args) + ":";
			}
		} catch (ClassCastException e) {
			String s[] = (String[]) sqlGetMultiValue(T, keyCol, keyVal, UtilSupport.getMax(inds) + 1, moreCond);
			String arg = s[inds[0]];
			String args = "";
			for (int i = 1; i < inds.length; i++) {
				args += s[inds[i]] + ",";
			}
			args = args.substring(0, args.length() - 1);
			str += String.format("%s(%s)", arg, args) + ":";
		}
		return UtilSupport.str2array(str, ":");
	}
}