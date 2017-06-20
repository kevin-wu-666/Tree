package com.wjl.model;

import java.util.ArrayList;
/**
 * id 节点的id
 * Pid 节点的父id
 * name 节点的名字
 * children 节点的子节点
 * @author wujiale
 *
 */
public class TreeNode {
	private String id;
	private String Pid;
	private String name;
	private ArrayList<TreeNode> children = new ArrayList<TreeNode>();
	
	public TreeNode() {
		// TODO Auto-generated constructor stub
	}

	public TreeNode(String id, String pid, String name) {
		this.id = id;
		this.Pid = pid;
		this.name = name;
	}

	public void add(TreeNode node) {// 递归添加节点
		if ("0".equals(node.Pid)) {
			this.children.add(node);
		} else if (node.Pid.equals(this.id)) {
			this.children.add(node);
		} else {
			for (TreeNode tmp_node : children) {
				tmp_node.add(node);
			}
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return Pid;
	}

	public void setPid(String pid) {
		Pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<TreeNode> children) {
		this.children = children;
	}

	
}