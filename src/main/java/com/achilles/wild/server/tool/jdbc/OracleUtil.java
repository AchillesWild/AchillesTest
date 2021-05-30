package com.achilles.wild.server.tool.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class OracleUtil {
	private String user = "scott"; 
	private String pwd = "532398999"; 
	private String driver = "oracle.jdbc.driver.OracleDriver"; 
	private String url = "jdbc:oracle:thin:@localhost:1521:orcl"; 
//	private String url = "jdbc:oracle:thin:@localhost:1521:orcl";

	private Connection conn ; 
	private Statement stmt; 

	public void GetConnection(String sql) throws ClassNotFoundException, SQLException{ 
	    Class.forName(driver); 
	    System.out.println("111111111111111111111"); 
	conn = DriverManager.getConnection(url, user, pwd);
	System.out.println("数据库已连接====conn==="+conn);  
//	stmt = conn.createStatement(); 
//	ResultSet rs = stmt.executeQuery(sql); 
//	while(rs.next()){ 
//	String a = rs.getString(1); 
//	String b = rs.getString(2); 
//	String c = rs.getString(3); 
//	System.out.println(rs); 
//	} 
//	conn.close(); 
//	stmt.close(); 
	} 

	public static void main(String[] args) throws Exception, SQLException{ 
	String sql = "select * from emp"; 
	OracleUtil ts = new OracleUtil(); 
	ts.GetConnection(sql); 
	} 
}
