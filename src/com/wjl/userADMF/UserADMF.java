package com.wjl.userADMF;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.wjl.model.User;
import com.wjl.utils.DBConnection;

public class UserADMF {
	
	static Connection conn ;
	static PreparedStatement pstmt;
	static ResultSet rs;
	
	public static User login(String userName,String userPassword){
		User user = null;
		conn = DBConnection.getDBConnection();
		String sql = "select * from user where username=? and password=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userName);
			pstmt.setString(2, userPassword);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				user = new User(rs.getString(2),rs.getString(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBConnection.closeDB(conn,pstmt, rs);
		return user;
	}

	public static User register(String email) {
		User user = null;
		conn = DBConnection.getDBConnection();
		String sql = "select * from user where email=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				user = new User(email);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
}
