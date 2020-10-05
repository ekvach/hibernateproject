package com.nix.hibernatedao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nix.daointerface.RoleDao;
import com.nix.entity.Role;

public class HibernateRoleDao implements RoleDao {

	private static final Logger logger = LoggerFactory.getLogger(HibernateRoleDao.class);

	public HibernateRoleDao() {
	}

	@Override
	public void create(Role role) {
		if (role == null) {
			logger.error("cannot create a record from null reference",
					new NullPointerException("cannot create a record from null reference"));
			throw new NullPointerException("cannot create a record from null reference");
		}

		Transaction tx = null;

		try (Session session = DatabaseSessionFactory.getSession()) {
			tx = session.beginTransaction();

			session.save(role);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			logger.error("something went wrong with creating", e);
			throw new IllegalArgumentException("something went wrong upon creating", e);
		}
	}

	@Override
	public void update(Role role) {
		if (role == null) {
			logger.error("cannot create a record from null reference",
					new NullPointerException("cannot create a record from null reference"));
			throw new NullPointerException("cannot create a record from null reference");
		}
		Transaction tx = null;

		try (Session session = DatabaseSessionFactory.getSession()) {
			tx = session.beginTransaction();

			session.update(role);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			logger.error("something went wrong with creating", e);
			throw new IllegalArgumentException("something went wrong upon updating", e);
		}
	}

	@Override
	public void remove(Role role) {
		if (role == null) {
			logger.error("cannot create a record from null reference",
					new NullPointerException("cannot create a record from null reference"));
			throw new NullPointerException("cannot create a record from null reference");
		}
		Transaction tx = null;

		try (Session session = DatabaseSessionFactory.getSession()) {
			tx = session.beginTransaction();

			session.delete(role);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			logger.error("something went wrong with creating", e);
			throw new IllegalArgumentException("something went wrong upon removing", e);
		}
	}

	@Override
	public Role findByName(String name) {
		if (name == null) {
			logger.error("cannot parse null name", new NullPointerException("cannot parse null name"));
			throw new NullPointerException("cannot parse null name");
		}

		Role role = null;
		Transaction tx = null;

		try (Session session = DatabaseSessionFactory.getSession()) {
			tx = session.beginTransaction();

			role = (Role) session.createQuery("FROM USER_ROLE where name = '" + name + "'").uniqueResult();

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			logger.error("something went wrong with search by login", e);
			throw new IllegalArgumentException("something went wrong with search by login", e);
		}

		return role;
	}

	@SuppressWarnings("unchecked")
	public List<Role> findAll() {
		List<Role> roleList = null;
		Transaction tx = null;

		try (Session session = DatabaseSessionFactory.getSession()) {
			tx = session.beginTransaction();

			roleList = (List<Role>) session.createQuery("FROM USER_ROLE").list();

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			logger.error("something went wrong with global search", e);
			throw new IllegalArgumentException("something went wrong with global search", e);
		}
		return roleList;
	}

}
