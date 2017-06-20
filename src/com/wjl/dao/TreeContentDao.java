package com.wjl.dao;

import com.wjl.treeContentADMF.TreeContentADMF;

public class TreeContentDao {

	public static String get(String id) {
		return TreeContentADMF.get(id);
	}
	
	public static void delete(String id) {
		TreeContentADMF.delete(id);
	}

	public static int modify(String id,String content) {
		return TreeContentADMF.modify(id,content);
	}

	public static void add(String id,String content) {
		TreeContentADMF.add(id,content);
	}

}
