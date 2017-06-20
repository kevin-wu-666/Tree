package com.wjl.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.wjl.utils.DBConnection;

public class ConfirmAction{
	/**
	 * 测试是添加按钮所处位置是不是孙节点
	 * @return
	 */
	
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public void confirm() throws SQLException{
		String id = ServletActionContext.getRequest().getParameter("Pid");
		conn = DBConnection.getDBConnection();
		String result;
		String sql = "select Pid from TreeNode where id = '"+id+"'";
		String result1 = null;
		rs = conn.prepareStatement(sql).executeQuery();
		while(rs.next()){
			result1 = rs.getString(1);
		}
		if (result1.equals("0")) {
			result = "isContent:false";
		}
		else {
			String sql2 = "select Pid from TreeNode where id = '"+result1+"'";
			String result2 = null;
			rs = conn.prepareStatement(sql2).executeQuery();
			while(rs.next()){
				result2 = rs.getString(1);
			}
			if (result2.equals("0")) {
				result = "isContent:false";
			}
			else{
				result = "isContent:true";
			}
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");  
	       try {
			PrintWriter out = response.getWriter();
			out.print(result);
	       } catch (IOException e) {
			e.printStackTrace();
		}
	}
}
