package com.nix.hibernatedao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nix.daointerface.UserDao;
import com.nix.entity.User;

public class HibernateUserDao implements UserDao {

	private static final Logger logger = LoggerFactory.getLogger(HibernateUserDao.class);

	public HibernateUserDao() {
	}

	@Override
	public void create(User user) {
		if (user == null) {
			logger.error("cannot create a record from null reference",
					new NullPointerException("cannot create a record from null reference"));
			throw new NullPointerException("cannot create a record from null reference");
		}
		Transaction tx = null;

		try (Session session = DatabaseSessionFactory.getSession()) {
			tx = session.beginTransaction();
			if (user.getId() == null) {
				Long maxId = (Long) session.createQuery("select max(id) from APP_USERS").uniqueResult();
				user.setId(maxId + 1L);
			}
			session.save(user);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			logger.error("something went wrong with creating", e);
			throw new IllegalArgumentException("something went wrong upon creating", e);
		}
	}

	@Override
	public void update(User user) {
		if (user == null) {
			logger.error("cannot update a record from null reference",
					new NullPointerException("cannot update a record from null reference"));
			throw new NullPointerException("cannot update a record from null reference");
		}
		Transaction tx = null;

		try (Session session = DatabaseSessionFactory.getSession()) {
			tx = session.beginTransaction();

			session.update(user);
			tx.commit();

		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			logger.error("something went wrong with creating", e);
			throw new IllegalArgumentException("something went wrong upon updating", e);
		}
	}

	@Override
	public void remove(User user) {
		if (user == null) {
			logger.error("cannot create a record from null reference",
					new NullPointerException("cannot find a record from null reference"));
			throw new NullPointerException("cannot find a record from null reference");
		}
		Transaction tx = null;

		try (Session session = DatabaseSessionFactory.getSession()) {
			tx = session.beginTransaction();

			session.delete(user);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			logger.error("something went wrong with creating", e);
			throw new IllegalArgumentException("something went wrong upon removing", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAll() {
		List<User> userList = null;

		try (Session session = DatabaseSessionFactory.getSession()) {

			userList = (List<User>) session.createQuery("FROM APP_USERS").list();

		} catch (Exception e) {
			logger.error("something went wrong with global search", e);
			throw new IllegalArgumentException("something went wrong with global search", e);
		}
		return userList;
	}

	@Override
	public User findByLogin(String login) {
		if (login == null) {
			logger.error("cannot parse null login", new NullPointerException("cannot parse null login"));
			throw new NullPointerException("cannot parse null login");
		}

		User user = null;
		try (Session session = DatabaseSessionFactory.getSession()) {

			user = (User) session.createQuery("FROM APP_USERS where login = '" + login + "'").uniqueResult();

		} catch (Exception e) {
			logger.error("something went wrong with search by login", e);
			throw new IllegalArgumentException("something went wrong with search by login", e);
		}

		return user;
	}

	@Override
	public User findByEmail(String email) {
		if (email == null) {
			logger.error("cannot parse null email", new NullPointerException("cannot parse null email"));
			throw new NullPointerException("cannot parse null email");
		}
		User user = null;

		try (Session session = DatabaseSessionFactory.getSession()) {

			user = (User) session.createQuery("FROM APP_USERS where email = '" + email + "'").uniqueResult();

		} catch (Exception e) {
			logger.error("something went wrong with search by email", e);
			throw new IllegalArgumentException("something went wrong with search by email", e);
		}

		return user;
	}

	public User findUserByLoginAndPass(String login, String pass) {
		if (login == null || pass == null) {
			logger.error("name or pass cannot be null email",
					new NullPointerException("name or pass cannot be null email"));
			throw new NullPointerException("name or pass cannot be null email");
		}
		User user = null;

		try (Session session = DatabaseSessionFactory.getSession()) {

			user = (User) session
					.createQuery("FROM APP_USERS where login = '" + login + "' and password = '" + pass + "'")
					.uniqueResult();

		} catch (Exception e) {
			logger.error("something went wrong with search by login or pass", e);
			throw new IllegalArgumentException("something went wrong with search by login or pass", e);
		}
		return user;

	}

	public User findById(Long id) {
		if (id == null || id <= 0) {
			logger.error("Id should be a positive number",
					new IllegalArgumentException("Id should be a positive number"));
			throw new IllegalArgumentException("Id should be a positive number");
		}
		User user = null;

		try (Session session = DatabaseSessionFactory.getSession()) {

			user = (User) session.createQuery("FROM APP_USERS where id = '" + id + "'").uniqueResult();

		} catch (Exception e) {
			logger.error("something went wrong with search by id", e);
			throw new IllegalArgumentException("something went wrong with search by id", e);
		}

		return user;
	}
}