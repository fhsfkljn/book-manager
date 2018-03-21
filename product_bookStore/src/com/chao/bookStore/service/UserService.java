package com.chao.bookStore.service;

import java.sql.SQLException;

import com.chao.bookStore.dao.UserDao;
import com.chao.bookStore.domain.User;
import com.chao.bookStore.exception.UserException;

public class UserService {

	UserDao dao = new UserDao();
	//用户注册
	public void regist(User user) throws UserException {
		try {
			dao.addUser(user);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserException("由于特殊原因，您注册失败了！");
		}
	}

}
