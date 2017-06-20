package com.test.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.wjl.treeNodeADMF.DbToStandardMoreNodeJson;
import com.wjl.utils.DBConnection;

public class Testtest {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	@Test
	public void testConnection() throws SQLException  {//测试查看与数据库连接是否成功
		conn = DBConnection.getDBConnection();
		if (conn!=null) {
System.out.println("success");
System.out.println(conn);
System.out.println("------------------------------------------");
		}
		String sql = "select content from TreeContent where id = '1496287431375'";
		rs = conn.prepareStatement(sql).executeQuery();
		while(rs.next()){
			System.out.println(rs.getString(1));
			System.out.println(rs);
		}
		
		List<String> ids = new ArrayList<String>();
		String sql1 = ("select id from TreeNode where Pid = '0'");
		rs = conn.prepareStatement(sql1).executeQuery();
		while(rs.next()){
			ids.add(rs.getString(1));
		}
		for(Iterator<String> it = ids.iterator();it.hasNext();){
			System.out.println(it.next());
		}
		DBConnection.closeDB(conn,pstmt, rs);
	}
	
	@Test
	public void testResult() throws Exception{//测试查看是否将根节点添加到List集合中
		conn = DBConnection.getDBConnection();
		Map<String, String> map;
		List list = new ArrayList<>();
		String sql = "select * from TreeNode where Pid = '0'";
		rs = conn.prepareStatement(sql).executeQuery();
		while(rs.next()){
			map = new HashMap<>();
			map.put("id", rs.getString(1));
			map.put("Pid", rs.getString(2));
			map.put("name", rs.getString(3));
			list.add(map);
		}
		for (Object object:list) {
			System.out.println(object);
			System.out.println("------------------------------------------");
		}
		DBConnection.closeDB(conn,pstmt, rs);
	}
	
	@Test
	public void testJson() throws SQLException{//测试查看返回json格式是否正确
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append(DbToStandardMoreNodeJson.getJson());
		sb.append("]");
		sb.deleteCharAt(sb.lastIndexOf(","));
		System.out.println(sb.length());
		System.out.println(sb.toString());
	}
	
	
	@Test
	public void testcontent(){
		String content = "描述符 1：基本信息 2：形态特征";
		content = content.replace(" ", "<br>");
		System.out.println(content);
	}
	
	@Test
	public void testDelete() throws Exception{
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
System.out.println("可得它是根节点");
			String sql1 = ("select id from TreeNode where Pid = '"+id+"'");
			rs = conn.prepareStatement(sql1).executeQuery();
			while(rs.next()){
				ids.add(rs.getString(1));
			}
			//ids = 9.1 9.2
			if (ids!=null) {//判断是否有子节点
System.out.println("可得它有子节点");				
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
System.out.println("可得它有孙节点");						 
						String deleteSql1 = "delete from TreeNode where Pid = '"+string+"'";
						conn.prepareStatement(deleteSql1).executeUpdate();//删除所有孙节点
System.out.println("删除孙节点");						
					}
				}
				String deleteSql2 = "delete from TreeNode where Pid = '"+id+"'";
				conn.prepareStatement(deleteSql2).executeUpdate();//删除所有子节点
System.out.println("删除子节点");						
			}
			String deleteSql3 = "delete from TreeNode where id = '"+id+"'";
			conn.prepareStatement(deleteSql3).executeUpdate();//删除他自己
System.out.println("删除他自己");
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
System.out.println("可得它是子节点");				
				String sql2 = ("select id from TreeNode where Pid = '"+id+"'");
				rs = conn.prepareStatement(sql2).executeQuery();
				while(rs.next()){
					ids.add(rs.getString(1));
				}
				if (ids!=null) {//可得这个子节点也有子节点
System.out.println("可得它有孙节点");					
					String deleteSql = "delete from TreeNode where Pid = '"+id+"'";//删除它的所有子节点
					conn.prepareStatement(deleteSql).executeUpdate();
System.out.println("删除它的孙节点");					
				}
				String deleteSql1 = "delete from TreeNode where id = '"+id+"'";//删除该子节点
				conn.prepareStatement(deleteSql1).executeUpdate();
System.out.println("删除他自己");				
			}
			else {//可得它必是孙节点
System.out.println("可得它是孙节点");				
				String sql2 = "delete from TreeNode where id = '"+id+"'";
				conn.prepareStatement(sql2).executeUpdate();
System.out.println("删除他自己");				
			}
			DBConnection.closeDB(conn,pstmt, rs);
		}
	}
	
}
