package org.test.controller;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

public class OraclConnectionTest {

	private static final String Driver="oracle.jdbc.OracleDriver";
	private static final String url="jdbc:oracle:thin:@192.168.200.156:1521:xe";
	private static final String user="portfolio";
	private static final String pw="1234";
	
	@Test
	public void testConnection() {
		
		try {
			Class.forName(Driver);
			Connection con=DriverManager.getConnection(url, user, pw);
			System.out.println("성공");
			System.out.println(con);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("실패");
		}
		

	}
	
}
