package com.wjl.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.wjl.dao.TreeContentDao;
import com.wjl.utils.DBConnection;

public class TreeContentAction extends ActionSupport{
	HttpServletRequest request = ServletActionContext.getRequest();
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public void getContent(){
		String id = request.getParameter("id");
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");  
	       try {
			PrintWriter out = response.getWriter();
			String content = TreeContentDao.get(id);
			out.print(content);
	       } catch (IOException e) {
			e.printStackTrace();
		} 
	}
	public String addContent(){
		String id = request.getParameter("id");
		String result = null;
		conn = DBConnection.getDBConnection();
		String sql = "select content from TreeContent where id = '"+id+"'";
		try {
			rs = conn.prepareStatement(sql).executeQuery();
			while(rs.next()){
				result = rs.getString(1);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (result==null) {
			String content = request.getParameter("content");
			TreeContentDao.add(id,content);
			return SUCCESS;
		}
		else{
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=UTF-8");  
		       try {
				PrintWriter out = response.getWriter();
				out.print("isNull:false");
				// isNull:true || false
		       } catch (IOException e) {
				e.printStackTrace();
			} 
		    return NONE;
		}
		
	}
	public String deleteContent(){
		String id = request.getParameter("id");
		TreeContentDao.delete(id);
		return SUCCESS;
	}
	public void modifyContent(){
		String id = request.getParameter("id");
		String content = request.getParameter("content");
		int a = TreeContentDao.modify(id,content);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");  
	       try {
			PrintWriter out = response.getWriter();
			out.print(a);
			// isNull:true || false
	       } catch (IOException e) {
			e.printStackTrace();
		}
	}
}
