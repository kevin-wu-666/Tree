package com.wjl.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
	public static String username;
	public static String password;
	public static String driver;
	public static String url;
	public static Connection connection;
	
	public static Connection getDBConnection(){
		try {
			InputStream inStream = DBConnection.class.getResourceAsStream("/jdbc.properties");
			Properties properties = new Properties();
			properties.load(inStream);//加载配置文件
			
			username = properties.getProperty("jdbc.username");
			password = properties.getProperty("jdbc.password");
			driver = properties.getProperty("jdbc.driver");
			url = properties.getProperty("jdbc.url");
			
			
			Class.forName(driver);
//			connection = DriverManager.getConnection(url);
			connection = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("找不到jdbc驱动文件！");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("找不到数据库配置文件！");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("数据库相关链接信息错误！");
		}
		return connection;
	}
	
	public static void closeDB(Connection connection,PreparedStatement preparedStatement,ResultSet resultSet){
		if (resultSet!=null) {
				try {
					resultSet.close();
					if (preparedStatement!=null) {
						preparedStatement.close();
						if (connection!=null) {
							connection.close();
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
		}
	}
}
