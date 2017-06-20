package com.wjl.treeNodeADMF;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wjl.model.TreeNode;
import com.wjl.utils.DBConnection;

import net.sf.json.JSONObject;

/**
 * @author 吴佳乐
 * 支持多个根节点的json数据
 */
public class DbToStandardMoreNodeJson {
	
	static Connection conn;
	static PreparedStatement pstmt;
	static ResultSet rs;
	static JSONObject obj;
	
	
	public static StringBuffer getJson(){
		List datas = getRootDatas();
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < datas.size(); i++){
//System.out.println("data:"+datas.get(i)+"datasize:"+datas.size());
			Map<Object, Object> map = (Map<Object, Object>) datas.get(i);//遍历获取根节点
//System.out.println("map:"+map.get("id"));
			String rootId = (String) map.get("id");
			String rootPid = (String) map.get("Pid");
			String rootName = (String) map.get("name");
			TreeNode root = new TreeNode(rootId ,rootPid ,rootName );//生成根节点
			try {
				String childSql = "select * from treeNode where Pid = '"+rootId+"'";
				rs = conn.prepareStatement(childSql).executeQuery();
				TreeNode childNode = null;
				while(rs.next()) {
					String childId = rs.getString(1);
					String childPid = rs.getString(2);
					String childName = rs.getString(3);
					childNode = new TreeNode(childId, childPid, childName);//生成子节点
					String sonSql = "select * from treeNode where Pid = '"+childId+"'";
					ResultSet rs1;
					rs1 = conn.prepareStatement(sonSql).executeQuery();
					TreeNode sonNode = null;
					while(rs1.next()) {
						String sonId = rs1.getString(1);
						String sonPid = rs1.getString(2);
						String sonName = rs1.getString(3);
						sonNode = new TreeNode(sonId, sonPid, sonName);//生成孙节点
						childNode.add(sonNode);
					}
					root.add(childNode);//将子节点添加到根节点
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			obj = JSONObject.fromObject(root);//有根
//			JSONArray obj = JSONArray.fromObject(root.getChildren());// 不要根
			sb.append(obj.toString()).append(",");
		}
			DBConnection.closeDB(conn,pstmt, rs);
			return sb;
		}
	
	public static List<Object> getRootDatas() {
		conn = DBConnection.getDBConnection();
		Map<String, String> map;
		List list = new ArrayList<>();
		try {
			String sql = "select * from treeNode where Pid = '0'";
			rs = conn.prepareStatement(sql).executeQuery();
			while (rs.next()) {
				map = new HashMap<>();
				map.put("id", rs.getString(1));
				map.put("Pid", rs.getString(2));
				map.put("name", rs.getString(3));
				list.add(map);
			} 
		} catch (Exception e) {
			// TODO: handle exception
		}
//		for (Object object:list) {
//			System.out.println(object);
//			System.out.println("------------------------------------------");
//		}
		return list;
	}
}