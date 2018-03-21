package com.chao.bookStore.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import com.chao.bookStore.domain.User;
import com.chao.bookStore.utils.C3P0Util;

public class UserDao {

	public void addUser(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDs());
		String sql = "INSERT INTO USER(username,PASSWORD,gender,email,telephone,introduce,activecode,state,registtime) "
				+ "VALUES(?,?,?,?,?,?,?,?,?)";
		qr.update(sql, user.getUsername(), user.getPassword(), user.getGender(), user.getEmail(), user.getTelephone(),
				user.getIntroduce(), user.getActiveCode(), user.getState(), user.getRegistTime());
	}

}
