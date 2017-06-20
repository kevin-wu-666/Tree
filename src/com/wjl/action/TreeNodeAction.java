package com.wjl.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.wjl.dao.TreeNodeDao;

public class TreeNodeAction extends ActionSupport{
	
	
	public void getNode(){
		HttpServletResponse response = ServletActionContext.getResponse();  
        response.setContentType("text/html;charset=UTF-8");  
        try {
			PrintWriter out = response.getWriter();
			String json = TreeNodeDao.getJsonData();
			out.print(json);
        } catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public String deleteNode(){
		String id = ServletActionContext.getRequest().getParameter("id");
		TreeNodeDao.delete(id);
		return SUCCESS;
	}
	
	public String addNode(){
		String Pid = ServletActionContext.getRequest().getParameter("Pid");
		String id = ServletActionContext.getRequest().getParameter("id");
		String name = ServletActionContext.getRequest().getParameter("name");
		TreeNodeDao.add(id, Pid, name);
		return SUCCESS;
	}
	
	public String modifyNode(){
		String id = ServletActionContext.getRequest().getParameter("id");
		String name = ServletActionContext.getRequest().getParameter("name");
		TreeNodeDao.modify(id, name);
		return SUCCESS;
	}
	
}
