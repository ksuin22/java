package com.yedam.common;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class DAO {
	
	//DAO ->Data Access Object
	//JDBC 연결
	//1) OJDBC를 추가
	//2) DAO
	
	//java -> DB연결할때 쓰는 객체
	protected Connection conn = null;
	
	//Select(조회) 결과값을 반환 받는 객체
	//Select한 결과를 Java로 전달
	protected ResultSet rs = null;
	
	
	//Query문(sql)을 가지고 실행하는 객체
	// sql문 실행해주는 객체 1
	protected PreparedStatement pstmt = null;
	
	//sql문 실행해주는 객체 2
	protected Statement stmt = null;
	
	
	//DB 접속정보
	Properties pro = new Properties();
	
//	String driver ="oracle.jdbc.driver.OracleDriver";
//	String url = "jdbc:oracle:thin:@localhost:1521:xe";
//	String id = "hr";
//	String pw = "hr";

	String driver = "";
	String url = "";
	String id = "";
	String pw = "";
	
	
	//DB 연결
	public void conn() {
		
		getProperties();
		
		try {
			
			//1.드라이버 로딩
			Class.forName(driver);
			
			//DB연결
			conn = DriverManager.getConnection(url,id,pw);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//DB연결 해제
	public void disconn() {
		try {
			//결과 조회하는 객체
			if(rs != null) {
				rs.close();
			}
			//sql 실행하는 객체
			if(stmt != null) {
				stmt.close();
			}
			if(pstmt != null) {
				pstmt.close();
			}
			//db 연결 객체
			if(conn != null) {
				conn.close();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	//DB접속정보 호출 메소드
	private void getProperties() {
		try {
			FileReader resource = new FileReader("src/config/db.properties");
			pro.load(resource);
			
			driver= pro.getProperty("driver");
			url = pro.getProperty("url");
			id = pro.getProperty("id");
			pw = pro.getProperty("pw");
		
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
	
	
//
}
