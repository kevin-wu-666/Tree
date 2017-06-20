package com.test.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.wjl.utils.DBConnection;

public class TestDelete {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	@Test
	public void a() throws Exception{
		String id = "9";
		List<String> ids = new ArrayList<String>();
		List<String> ids1 = new ArrayList<String>();
		conn = DBConnection.getDBConnection();
//		9		0		123
//		9.1		9		123
//		9.1.1	9.1		123
//		9.1.2	9.1		123
//		9.2		9		123
		String sql = "select Pid from TreeNode where id = '"+id+"'";
		rs = conn.prepareStatement(sql).executeQuery();
		String result = null;
		while(rs.next()){
			result = rs.getString(1);
		}
		if (result.equals("0")) {//可得它是根节点
			String sql1 = ("select id from TreeNode where Pid = '"+id+"'");
			rs = conn.prepareStatement(sql1).executeQuery();
			while(rs.next()){
				ids.add(rs.getString(1));
			}
			//ids = 9.1 9.2
			if (ids!=null) {//判断是否有子节点
				for (Iterator<String> iterator = ids.iterator(); iterator.hasNext();) {
					String string = iterator.next();
					// 9.1.1 9.1.2
					// 9.2.1 9.2.2
					String sql2 = "select id from TreeNode where Pid = '"+string+"'";
					rs = conn.prepareStatement(sql2).executeQuery();
					while(rs.next()){
						ids1.add(rs.getString(1));
					}
					 if (ids1!=null) {//判断是否有孙节点
						String deleteSql1 = "delete from TreeNode where Pid = '"+string+"'";
						conn.prepareStatement(deleteSql1).executeUpdate();//删除所有孙节点
					}
				}
				String deleteSql2 = "delete from TreeNode where Pid = '"+id+"'";
				conn.prepareStatement(deleteSql2).executeUpdate();//删除所有子节点
			}
			String deleteSql3 = "delete from TreeNode where id = '"+id+"'";
			conn.prepareStatement(deleteSql3).executeUpdate();//删除他自己
			DBConnection.closeDB(conn,pstmt, rs);
		}
		else {
			String sql1 = "select Pid from TreeNode where id = '"+result+"'";
			rs = conn.prepareStatement(sql1).executeQuery();
			String result1 = null;
			while(rs.next()){
				result1 = rs.getString(1);
			}
			if (result1.equals("0")) {//可得出它必是子节点
				String sql2 = ("select id from TreeNode where Pid = '"+id+"'");
				rs = conn.prepareStatement(sql2).executeQuery();
				while(rs.next()){
					ids.add(rs.getString(1));
				}
				if (ids!=null) {//可得这个子节点也有子节点
					String deleteSql = "delete from TreeNode where Pid = '"+id+"'";//删除它的所有子节点
					conn.prepareStatement(deleteSql).executeUpdate();
				}
				String deleteSql1 = "delete from TreeNode where id = '"+id+"'";//删除该子节点
				conn.prepareStatement(deleteSql1).executeUpdate();
			}
			else {//可得它必是孙节点
				String sql2 = "delete from TreeNode where id = '"+id+"'";
				conn.prepareStatement(sql2).executeUpdate();
			}
			DBConnection.closeDB(conn,pstmt, rs);
		}
	}
}
