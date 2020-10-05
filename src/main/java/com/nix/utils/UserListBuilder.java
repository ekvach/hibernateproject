package com.nix.utils;

import java.util.Date;
import java.util.List;

import com.nix.entity.User;
import com.nix.hibernatedao.HibernateUserDao;

public class UserListBuilder {

	public List<User> buildFullUserList() {

		List<User> userList = new HibernateUserDao().findAll();

		for (User u : userList) {
			if (u.getBirthday() != null) {
				fillUserAge(u);
			}
		}
		return userList;
	}

	@SuppressWarnings("deprecation")
	public User fillUserAge(User u) {

		if (u.getBirthday() != null) {
			Date currentDate = new java.util.Date();
			Date userBirthday = new java.util.Date();
			userBirthday.setTime(u.getBirthday().getTime());
			u.setAge(currentDate.getYear() - userBirthday.getYear());
		}
		return u;
	}

	public List<User> buildUserWithoutPassList() {

		List<User> userList = new HibernateUserDao().findAll();

		for (User u : userList) {
			if (u.getBirthday() != null) {
				fillUserAge(u);
			}
			u.setPassword(null);
		}
		return userList;
	}

}
