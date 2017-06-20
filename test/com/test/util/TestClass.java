package com.test.util;

import java.sql.Connection;

import org.junit.Test;

import com.wjl.utils.DBConnection;

public class TestClass {
	@Test
	public void testClass(){
		Connection connection = DBConnection.getDBConnection();
		if (connection!=null) {
			System.out.println("success");
			System.out.println(DBConnection.url);
		}
		
	}
}
