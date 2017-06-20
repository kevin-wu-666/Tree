package com.wjl.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MyFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("destroy");
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		HttpSession session = request.getSession();
		String url = request.getRequestURI();
//System.out.println(request.getRequestURI());
		if(url.indexOf("login.jsp")!=-1||!url.endsWith("jsp")){
			arg2.doFilter(arg0, arg1);
			return;
		}
//System.out.println(session.getAttribute("user"));
			//因为登录后保存了username，所以可以先检查username判断是否登录
		if(session.getAttribute("user")!=null){
			arg2.doFilter(arg0, arg1);//已登录，则放行，
		}else{
			response.sendRedirect("login.jsp");//未登录，重定向到登录页面
//System.out.println("拦截一次未登陆的请求");
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("init");
	}

}
