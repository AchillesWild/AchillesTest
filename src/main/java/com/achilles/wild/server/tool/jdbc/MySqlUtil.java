package com.achilles.wild.server.tool.jdbc;

import java.sql.*;
import java.util.*;

/**
 * mysql 连接
 * @author Pang
 *
 */
public class MySqlUtil {

	static String urlString = "jdbc:mysql://localhost:3306/info?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&allowPublicKeyRetrieval=true&useSSL=false";
	static String name = "root";
	static String password = "root";

	public static String updateBat(List<String> sqlSet){
		Connection conn = getConnection();
		Statement statement = null;
		int[] updates = null;
		try {
			conn.setAutoCommit(false);  
			statement = conn.createStatement();
			
			for (String sql : sqlSet) {
				statement.addBatch(sql);
			}
			updates =statement.executeBatch();
			conn.commit();  
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}  
			e.printStackTrace();
		}finally{
			
			try {
				conn.rollback();  
				if(statement!=null)
					conn.close();
			} catch (SQLException e) {
					e.printStackTrace();
			}
			try {
			   if(conn!=null)
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return Arrays.toString(updates);
	}
	
	
	/**
	 * 批量修改
	 * @param sqlSet  生成好的sql
	 * @return
	 */
	public static String updateBatch(List<String> sqlSet){
		Connection conn = getConnection();
		Statement statement = null;
		try {
			statement = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		int[] updates = null;
		try {
			for (String sql : sqlSet) {
				statement.addBatch(sql);
			}
			updates =statement.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(statement!=null)
					conn.close();
			} catch (SQLException e) {
					e.printStackTrace();
			}
			try {
			   if(conn!=null)
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return Arrays.toString(updates);
	}
	
	/**
	 * 单条修改
	 * @param sql
	 * @return
	 */
	public static int update(String sql){
		Connection conn = getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		int update = 0;
		try {
			update = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(stmt!=null)
					conn.close();
			} catch (SQLException e) {
					e.printStackTrace();
			}
			try {
			   if(conn!=null)
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return update;
	}
	
	/**
	 * 获取 ResultSet
	 * @param sql
	 * @return
	 */
	public static ResultSet getResultSet(String sql){
		Connection conn = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * 查询数据Map
	 * @param sql
	 * @return
	 */
	public static Map<String,Object> getMapBysql(String sql){
		ResultSet rs = getResultSet(sql);
		Map<String,Object> map = null;
		try {
			ResultSetMetaData md = rs.getMetaData();
			int coloumCount = md.getColumnCount();
			while (rs.next()) {
				map = new HashMap<String,Object>();
				for (int i = 1; i <= coloumCount; i++) {
					map.put(md.getColumnName(i), rs.getObject(i));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return map;
	}
	
	/**
	 * 查询数据List
	 * @param sql
	 * @return
	 */
	public static List<Map<String,Object>> getListBysql(String sql){
		ResultSet rs = getResultSet(sql);
		List<Map<String,Object>> resultList = null;
		try {
			ResultSetMetaData md = rs.getMetaData();
			int coloumCount = md.getColumnCount();
			resultList = new ArrayList<Map<String,Object>>();
			while (rs.next()) {
				Map<String,Object> map = new HashMap<String,Object>();
				for (int i = 1; i <= coloumCount; i++) {
					map.put(md.getColumnName(i), rs.getObject(i));
				}
				resultList.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return resultList;
	}
	
	/**
	 * 获取数据库连接
	 * @return
	 */
	public static Connection getConnection() {
		Connection con = null; //定义一个MYSQL链接对象
		try {
            con = DriverManager.getConnection(urlString, name, password); //链接本地MYSQL
//            con.setAutoCommit(false);
        } catch (Exception e) {
        	e.printStackTrace();
        }
		return con;
	}
	
	public static void closeAll(Connection conn, PreparedStatement ps, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
