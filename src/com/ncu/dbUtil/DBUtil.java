package com.ncu.dbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/bill?useUnicode=true&characterEncoding=UTF-8";
	private static final String USERNAME = "root";
	private static final String POSSWORD = "5412";
	
	private static Connection conn = null;
	
	static{
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException{
		
		return DriverManager.getConnection(URL,USERNAME,POSSWORD);
	}
	
//	public static void main(String [] args) throws SQLException{
//		Connection c = DBUtil.getConnection();
//		if(c!=null){
//			System.out.println("success");
//		}
//	}
}
