package com.nix.utils;

import com.nix.hibernatedao.HibernateUserDao;

public class IsUserExistValidator {

	public static boolean validate(String login, String pass) {
		if (login == null || pass == null) {
			throw new IllegalArgumentException("name or pass cannot be null");
		}
		HibernateUserDao userDao = new HibernateUserDao();

		return (userDao.findUserByLoginAndPass(login, pass) != null);
	}
}