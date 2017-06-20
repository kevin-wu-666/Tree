package com.wjl.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.core.config.Reconfigurable;
import org.apache.struts2.ServletActionContext;

import com.sun.net.httpserver.Authenticator.Success;
import com.wjl.dao.UserDao;
import com.wjl.model.User;
import com.wjl.utils.DBConnection;

public class UserAction{
	
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	HttpSession session = request.getSession();
	
	public void login() throws IOException{
		String username = request.getParameter("userName");
		String userPassword = request.getParameter("userPassword");
		User user = UserDao.login(username,userPassword);
		response.setContentType("text/html;charset=UTF-8");  
		PrintWriter out = response.getWriter();
		if (user != null) {
			session.setAttribute("user", user);
			out.print("欢迎 "+username+" ,登录成功！");
		}
		else {
			out.print("账号或密码错误,请重新输入！");
		}
	}
	
	public void register() throws IOException, SQLException{
		Connection conn;
		PreparedStatement pstmt;
		ResultSet rs;
		String username = request.getParameter("userName");
		String password = request.getParameter("userPassword");
		String email = request.getParameter("userEmail");
		User user = UserDao.register(email);
		conn = DBConnection.getDBConnection();
		response.setContentType("text/html;charset=UTF-8");  
		PrintWriter out = response.getWriter();
		if (user != null) {
			out.print("抱歉！此邮箱已经被占用！");
		}
		else{
			pstmt = conn.prepareStatement("select * from user where username = ?");
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			if (rs.next()){
				out.print("抱歉！此用户名已经被占用！");
			}
			else{
				String sql = "insert into user(username,password,email) values (?,?,?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, username);
				pstmt.setString(2, password);
				pstmt.setString(3, email);
				int a = pstmt.executeUpdate();
				if (a != 1) {
					out.println("发生未知错误");
				}
				else
					out.print("恭喜"+username+",注册成功！快去登录吧！");
				DBConnection.closeDB(conn,pstmt, rs);
			}
		}
	}
	public String logout(){
		session.removeAttribute("user");
		return "a";
	}
}
