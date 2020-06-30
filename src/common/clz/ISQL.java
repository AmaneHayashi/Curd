package common.clz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class ISQL {

	protected static final String URL = "jdbc:sqlserver://localhost:1433;DatabaseName=DWBac";
	protected static final String USER = "sa";
	protected static final String PASSWORD = "amanedoris";
	protected static final String EXCEPTION_STRING = "SQL函数执行出现异常！";

	protected static Connection conn = null;
	protected static PreparedStatement ps = null;
	protected static ResultSet rs = null;
	protected static ResultSetMetaData rsmd = null;
	protected static Statement stmt = null;
	protected static String sql = "select * from %s";

	// 静态代码块（将加载驱动、连接数据库放入静态块中）
	static {
		try {
			// 1.加载驱动程序
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			// 2.获得数据库的连接
			conn = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("数据库连接成功！");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("未发现数据库支持类！");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("数据库连接失败！");
		} catch (NullPointerException e) {
		}
	}
	/**
	 * 获取连接函数（对外提供一个方法来获取数据库连接）
	 * 
	 * @return conn（Connection，数据库的链接）
	 */

	public static Connection getConnection() {
		return conn;
	}

	protected static ResultSet doSql(String sql) {
		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
			rsmd = rs.getMetaData();
			rs.next();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_STRING);
			e.printStackTrace();
		}
		return rs;
	}

	protected static int doScrollSql(String sql) {
	//	ResultSet rs1 = doSql(sql);//复制
		doSql(sql);
		try {
			rs.last();
			int rows = rs.getRow();
			rs.first();
			return rows;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_STRING);
			e.printStackTrace();
			return -1;
		}
	}

	protected static void doUpdSql(String sql) {
		try {
			conn.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println(EXCEPTION_STRING);
			e.printStackTrace();
		}
	}

	// 取多列
	protected static String[] rsMultiCols(Object sortCol) {
		String[] sResult = null;
		try {
			try {
				sResult = new String[((String[]) sortCol).length];
				for (int i = 0; i < sResult.length; i++) {
					sResult[i] = rs.getString(((String[]) sortCol)[i]).trim();
				}
			} catch (ClassCastException e) {
				sResult = new String[Integer.min(rsmd.getColumnCount(), Integer.parseInt(sortCol.toString()))];
				for (int i = 0; i < sResult.length; i++) {
					sResult[i] = rs.getString(i + 1).trim();
				}
			}
		} catch (SQLException e) {
			System.out.println(EXCEPTION_STRING);
			e.printStackTrace();
		}
		return sResult;
	}

	// 取多行
	protected static String[] rsMultiRows(Object sortCol, int rows) {
		String[] sResult = new String[rows];
		try {
			try {
				for (int i = 0; i < rows; i++) {
					sResult[i] = rs.getString(sortCol.toString()).trim();
					rs.next();
				}
			} catch (ClassCastException e) {
				for (int i = 0; i < rows; i++) {
					sResult[i] = rs.getString(Integer.parseInt(sortCol.toString())).trim();
					rs.next();
				}
			}
		} catch (SQLException e) {
			System.out.println(EXCEPTION_STRING);
			e.printStackTrace();
		}
		return sResult;
	}

	// 取多列多行
	protected static String[][] rsDual(Object sortCol, int rows) {
		String[][] ssResult = null;
		try {
			try {
				ssResult = new String[rows][((String[]) sortCol).length];
			} catch (ClassCastException e) {
				ssResult = new String[rows][Integer.parseInt(sortCol.toString())];
			} finally {
				for (int i = 0; i < rows; i++) {
					ssResult[i] = rsMultiCols(sortCol);
					rs.next();
				}
			}
		} catch (SQLException e) {
			System.out.println(EXCEPTION_STRING);
			e.printStackTrace();
		}
		return ssResult;
	}
}