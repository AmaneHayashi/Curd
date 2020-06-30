package common.support;

import java.sql.SQLException;

import common.clz.ISQL;
import my.comps.MyDtm;
import my.comps.MyTable;

/**
 * {@code SQLSupport}（SQL支持类，由{@code SQLTest}继承）
 * 
 * <p>
 * SQL支持类主要提供可用于SQL查询、更新、清空、插入等函数，既可直接对返回值进行处理，也有针对
 * <code>JTable(TableModel)</code>的解决方案。
 * <p>
 * 考虑到SQL函数的使用特性，建议本类下的函数均使用静态引用。
 * <p>
 * 具体地讲，SQL支持类的函数主要包括以下方面：
 * 
 * <UL>
 * <LI>SQL测试：<code>Test</code>
 * <LI>SQL查询：<code>sqlSearch</code>
 * <LI>
 * SQL取值：<code>sqlGetUniqueValue, sqlGetValue, sqlGetMultiValue, sqlFillTable, sqlGetTableHeader,
 *   sqlAutoLabel, sqlGetQualifiedRows, sqlAutoDmc</code>
 * <LI>SQL更新：<code>sqlUpdate, sqlRenewTable</code>
 * <LI>SQL删除：<code>sqlDelete, sqlDeteleTable</code>
 * <LI>SQL插入：<code>sqlWrite, sqlWriteTable</code>\
 * </UL>
 * 
 * @author Amane Hayashi
 * @since 1.0
 */
public class SQLSupport extends ISQL {

	/**
	 * {@code SQLSupport}类中的SQL语句变量
	 */
	private static String sql = null;

	/*
	 * public static void main(String[] args) { Test(); }
	 */

	/**
	 * SQL测试（顺次输出{@code Test}表中第一列的值）
	 * <p>
	 * SQL语句：
	 * 
	 * <pre>
	 * {@code
	 * select * from Test where 1 = '1' }
	 * </pre>
	 * 
	 * <p>
	 * 注：<code>where 1 = '1'</code>相当于没有选择，即等价于：<code>select * from Test</code>，下同。
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
				System.out.println("测试名：" + rs.getString(1));
			} while (rs.next());
		} catch (SQLException e) {
			System.out.println(EXCEPTION_STRING);
			e.printStackTrace();
		}
	}

	/**
	 * SQL查询（表{@code T}中是否存在一行，其{@code keyCol}列的值为{@code keyVal}？若存在，{@code keyVal}对应的行中，
	 * {@code sortCol}列的值是否等于{@code soreVal}？）
	 * <p>
	 * SQL语句：
	 * 
	 * <pre>
	 * {@code
	 * select * from T where keyCol = 'keyVal' moreCond }
	 * </pre>
	 * 
	 * <p>
	 * 注：{@code moreCond}用于在SQL语句中添加更多的条件，下同。如：
	 * <UL>
	 * <LI>{@code order by 1}，则表示将结果按第一列排序，SQL语句为：
	 * 
	 * <pre>
	 * {@code
	 * select * from T where keyCol = 'keyVal' order by 1}
	 * </pre>
	 * 
	 * <LI>{@code and keyCol2 = 'keyVal2'}，则表示多条件查询，SQL语句为：
	 * 
	 * <pre>
	 * {@code
	 * select * from T where keyCol = 'keyVal' and keyCol2 = 'keyVal2'}
	 * </pre>
	 * </UL>
	 * 
	 * @param T        表名
	 * @param keyCol   主列名
	 * @param keyVal   主列值
	 * @param sortCol  待查列名
	 * @param sortVal  待查列值
	 * @param moreCond 补充条件
	 * @return 如果{@code sortCol}列的值等于{@code sortVal}，返回{@code true}，否则{返回@code
	 *         false}
	 * @throws SQLException
	 */

	public static boolean sqlSearch(String T, String keyCol, String keyVal, String sortCol, String sortVal,
			String moreCond) {
		sql = "select * from %s where %s = '%s' %s";
		sql = String.format(sql, T, keyCol, keyVal, moreCond);
		System.out.println("SQL查询语句：" + sql);
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
	 * SQL取值（表{@code T}中是否存在一行，其{@code keyCol}列的值为{@code keyVal}？若存在，取出{@code keyVal}对应的行中，
	 * {@code sortCol}列的值）
	 * <p>
	 * SQL语句：
	 * 
	 * <pre>
	 * {@code
	 * select * from T where keyCol = 'keyVal' moreCond }
	 * </pre>
	 * 
	 * <p>
	 * 注：本函数适用于定行单列的取值。
	 * 
	 * @param T        表名
	 * @param keyCol   主列名
	 * @param keyVal   主列值
	 * @param sortCol  待查列名
	 * @param sortVal  待查列值
	 * @param moreCond 补充条件
	 * @return {@code keyVal}对应的行中，{@code sortCol}列的值
	 * @throws SQLException
	 */

	public static String sqlGetUniqueValue(String T, String keyCol, String keyVal, String sortCol, String moreCond) {
		sql = "select * from %s where %s = '%s' %s";
		sql = String.format(sql, T, keyCol, keyVal, moreCond);
		System.out.println("SQL定行单列取值：" + sql);
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
	 * SQL取值函数（T表的keyCol列中是否有keyVal？如果有，返回keyVal对应的行中，sortCol列的值）
	 * [一次取出一组或一个值（不定行，单列<行数与结果集有关>）。如果查询条件多于1个，在moreCond中写出]
	 * 
	 */

	public static Object sqlGetValue(String T, String keyCol, String keyVal, String sortCol, String moreCond) {
		sql = "select * from %s where %s = '%s' %s";
		sql = String.format(sql, T, keyCol, keyVal, moreCond);
		System.out.println("SQL不定行单列取值：" + sql);
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
	 * SQL取值函数（T表的keyCol列中是否有keyVal？如果有，返回keyVal对应的行中，sortCol列的值）
	 * [一次取出一组值（定行，多列<给定行数>）。如果查询条件多于1个，在moreCond中写出]
	 * 
	 */

	public static String[] sqlGetValue(String T, String keyCol, String keyVal, Object sortCol, String moreCond) {
		sql = "select * from %s where %s = '%s' %s";
		sql = String.format(sql, T, keyCol, keyVal, moreCond);
		System.out.println("SQL定行多列取值：" + sql);
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
	 * SQL取值函数（T表的keyCol列中是否有keyVal？如果有，返回keyVal对应的行中，sortCol列的值）
	 * [一次取出二维值（不定行，多列<行数与结果集有关>）。如果查询条件多于1个，在moreCond中写出]
	 * 
	 */

	public static Object sqlGetMultiValue(String T, String keyCol, String keyVal, Object sortCol, String moreCond) {
		sql = "select * from %s where %s = '%s' %s";
		sql = String.format(sql, T, keyCol, keyVal, moreCond);
		System.out.println("SQL不定行多列取值：" + sql);
		try {
			int row = doScrollSql(sql);
			rs.first();
			if (row == 1) {
				// 此时为定行多列
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
	 * 关联填充
	 */

	public static Object sqlGetMultiValue(String pT, String fT, String fKey, String xKey, String xValue) {
		String pCol = String.format("%s.%s", pT, fKey);
		String fCol = String.format("%s.%s", fT, fKey);
		String xCol = String.format("%s.%s", fT, xKey);
		sql = "select * from %s where %s in (select %s from %s where %s =%s AND %s ='%s')";
		sql = String.format(sql, pT, pCol, fCol, fT, pCol, fCol, xCol, xValue);
		System.out.println("SQL关联表填充:" + sql);
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
	 * SQL更新
	 */

	public static boolean sqlUpdate(String T, String updCol, String updVal, String keyCol, String keyVal,
			String moreCond) {
		sql = "update %s set %s = '%s' where %s = '%s' %s";
		sql = String.format(sql, T, updCol, updVal, keyCol, keyVal, moreCond);
		System.out.println("SQL更新（单值更新）：" + sql);
		doUpdSql(sql);
		return true;
	}

	/**
	 * SQL插入表
	 * <p>
	 * 若干行符合条件的内容写入SQL
	 */

	public static void sqlInsert(String T, String[] str) {
		for (int i = 0; i < str.length; i++) {
			str[i] = String.format("'%s'", str[i]);
		}
		sql = "insert into %s values(%s)";
		sql = String.format(sql, T, UtilSupport.array2str(str));
		System.out.println("SQL更新（一行值更新）:" + sql);
		doUpdSql(sql);
	}

	/**
	 * SQL删除表
	 * <p>
	 * 若干行符合条件的内容删除SQL
	 */

	public static void sqlDelete(String T, String delCol, String delValue, String moreCond) {
		sql = "delete from %s where %s = '%s' %s";
		sql = String.format(sql, T, delCol, delValue, moreCond);
		System.out.println("SQL删除（一行值删除）:" + sql);
		doUpdSql(sql);
	}

	/**
	 * SQL获取符合要求的行标
	 */

	public static int sqlGetQualifiedRows(String T, String keyCol, String keyVal, String moreCond) {
		sql = "select * from %s where %s = '%s' %s";
		sql = String.format(sql, T, keyCol, keyVal, moreCond);
		System.out.println("SQL寻找符合要求的行数:" + sql);
		return doScrollSql(sql);
	}

	/**
	 * SQL获取当前(下一个应填写)的标号
	 * 
	 * @throws SQLException
	 */

	public static int sqlGetLabel(String T) {
		System.out.println("SQL获取序列标号:\t");
		sqlGetUniqueValue(T, "1", "1", "序列号", "order by 1");
		try {
			rs.last();
			return Integer.parseInt(rs.getString("序列号")) + 1;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_STRING);
			// e.printStackTrace();
			return 1;
		}
	}

	/**
	 * SQL取表头
	 */

	public static String[] sqlGetColN(String T) {
		sql = "select * from %s";
		sql = String.format(sql, T);
		System.out.println("SQL获取表头：" + sql);
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
	 * SQL自动填充一张表（数据库中的表与待填表的内容要求一致）
	 */

	public static void sqlFillTable(String T, MyTable mtb, String moreCond) {
		MyDtm dtm = mtb.getDtm();
		// 先清零
		dtm.removeAll();
		System.out.println("SQL全表填充：\t");
		// 执行查询
		try {
			dtm.addDual((String[][]) sqlGetMultiValue(T, "1", "1", mtb.getColN(), moreCond));
		} catch (ClassCastException e) {
			dtm.addRow(sqlGetValue(T, "1", "1", mtb.getColN(), moreCond));
		}
	}

	/**
	 * SQL重写表
	 */

	public static void sqlRenewTable(String T, MyTable mtb) {
		System.out.println("SQL更新（重写SQL表）：\t");
		sqlDeleteTable(T);
		sqlWriteTable(T, mtb.getDtm());
	}

	/**
	 * SQL插入表（两张表相同）
	 */

	public static void sqlWriteTable(String T, MyDtm dtm) {
		System.out.println("SQL更新（更新一张表）:\t");
		for (int i = 0; i < dtm.getRowCount(); i++) {
			sqlInsert(T, dtm.getRowValue(i));
		}
	}

	/**
	 * SQL清空表
	 * <p>
	 * 不删除表，只清空数据
	 */

	public static void sqlDeleteTable(String T) {
		sql = "truncate table %s";
		sql = String.format(sql, T);
		System.out.println("SQL清空一张表:" + sql);
		doUpdSql(sql);
	}

	/**
	 * SQL填充JComboBox的数据
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