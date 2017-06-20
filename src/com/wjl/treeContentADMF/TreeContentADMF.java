package com.wjl.treeContentADMF;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.wjl.utils.DBConnection;

public class TreeContentADMF {
	static Connection conn;
	static PreparedStatement pstmt;
	static ResultSet rs;
	public static void delete(String id) {
		conn = DBConnection.getDBConnection();
		String sql = "delete from TreeContent where id = '"+id+"'";
		try {
			conn.prepareStatement(sql).executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBConnection.closeDB(conn,pstmt, rs);
		}
	}

	public static int modify(String id,String content) {
		conn = DBConnection.getDBConnection();
		String sql = "update TreeContent set content = ?"+"where id= ?";
		int a = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, content);
			pstmt.setString(2, id);
			a = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBConnection.closeDB(conn,pstmt, rs);		
		return a;
	}

	public static void add(String id, String content) {
		conn = DBConnection.getDBConnection();
		// TODO Auto-generated method stub
		String sql = "insert into treeContent (id,content) values(?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, content);
			pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			DBConnection.closeDB(conn,pstmt, rs);
		}
	}

	public static String get(String id) {
		conn = DBConnection.getDBConnection();
		String sql = "select content from TreeContent where id = '"+id+"'";
		String content = null;
		try {
			rs = conn.prepareStatement(sql).executeQuery();
			while(rs.next()){
				content = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBConnection.closeDB(conn,pstmt, rs);
		}
		if (content!=null) {
			return content;
		}
		else{
			return "";
		}
	}

}
