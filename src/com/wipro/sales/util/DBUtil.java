package com.wipro.sales.util;

import java.sql.*;
// oracle

public class DBUtil {
	
	public static Connection getDBConnected() {
		Connection con= null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","M_JAVA","123456");
//			System.out.println("Connected...");
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return con;
		
	}

}
