package com.nix.hibernatedao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseSessionFactory {
	private static SessionFactory factory = initializeSessionFactory();
	private static final Logger logger = LoggerFactory.getLogger(DatabaseSessionFactory.class);

	public DatabaseSessionFactory() {
	}

	private static SessionFactory initializeSessionFactory() {
		try {
			return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		} catch (Throwable ex) {
			logger.error("could not create a session factory",
					new IllegalArgumentException("could not create a session factory", ex));
			throw new IllegalArgumentException("could not create a session factory", ex);
		}
	}

	public static Session getSession() {
		return factory.openSession();
	}
}
