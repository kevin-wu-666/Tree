package com.wjl.dao;

import java.sql.SQLException;

import com.wjl.treeNodeADMF.DbToStandardMoreNodeJson;
import com.wjl.treeNodeADMF.TreeNodeADM;

public class TreeNodeDao {
	
	public static String getJsonData(){
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append(DbToStandardMoreNodeJson.getJson());
		sb.append("]");
		sb.deleteCharAt(sb.lastIndexOf(","));
		return sb.toString();
	}

	public static void delete(String id) {
		try {
			
			TreeNodeADM.delete(id);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void modify(String id, String name) {
		// TODO Auto-generated method stub
		TreeNodeADM.modify(id,name);
	}

	public static void add(String id, String pid, String name) {
		// TODO Auto-generated method stub
		TreeNodeADM.add(id, pid,name);
	}
}
