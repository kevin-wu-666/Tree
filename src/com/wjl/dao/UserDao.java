package com.wjl.dao;

import com.wjl.model.User;
import com.wjl.userADMF.UserADMF;

public class UserDao {
	public static User login(String userName,String userPassword){
		return UserADMF.login(userName,userPassword);
	}

	public static User register(String email) {
		return UserADMF.register(email);
	}
}
