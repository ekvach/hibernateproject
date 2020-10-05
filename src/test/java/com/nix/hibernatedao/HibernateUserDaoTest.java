package com.nix.hibernatedao;

import java.sql.ResultSet;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nix.entity.Role;
import com.nix.entity.User;
import com.nix.utils.ConfigPropertyLoader;

public class HibernateUserDaoTest {

	private static IDatabaseTester tester = null;

	@BeforeClass
	public static void setUpClass() throws Exception {
		String DB_DRIVER = ConfigPropertyLoader.getPropertyByName("hibernate.connection.driver_class");
		String DB_URI = ConfigPropertyLoader.getPropertyByName("hibernate.connection.url");
		tester = new JdbcDatabaseTester(DB_DRIVER, DB_URI);
		tester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
		tester.setTearDownOperation(DatabaseOperation.NONE);
	}

	@Before
	public void setUр() throws Exception {
		IDataSet dataSet = new FlatXmlDataSetBuilder()
				.build(HibernateUserDaoTest.class.getClassLoader().getResource("testDbData.xml"));
		tester.setDataSet(dataSet);
		tester.onSetup();
	}

	@After
	public void tearDown() throws Exception {
		tester.onTearDown();
	}

	@Test
	public void testCreateRequiredFields() throws Exception {
		HibernateUserDao userDao = new HibernateUserDao();

		userDao.create(new User(4L, "admin", "passwordA", "emailAdmin@email.com", new Role(1L)));

		IDataSet actualDataSet = tester.getConnection().createDataSet();
		ITable actualTable = actualDataSet.getTable("APP_USERS");

		IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
				.build(HibernateUserDaoTest.class.getClassLoader().getResource("testCreateUserExpected.xml"));
		ITable expectedTable = expectedDataSet.getTable("APP_USERS");

		Assertion.assertEquals(expectedTable, actualTable);
	}

	@Test
	public void testCreateAllFields() throws Exception {
		HibernateUserDao userDao = new HibernateUserDao();

		userDao.create(new User(4L, "admin", "passwordA", "emailAdmin@email.com", "Jack", "Jonson",
				new Date(new GregorianCalendar(2000, 8, 05).getTimeInMillis()), new Role(1L)));

		IDataSet actualDataSet = tester.getConnection().createDataSet();
		ITable actualTable = actualDataSet.getTable("APP_USERS");

		IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
				.build(HibernateUserDaoTest.class.getClassLoader().getResource("testCreateUserAllFieldsExpected.xml"));
		ITable expectedTable = expectedDataSet.getTable("APP_USERS");

		Assertion.assertEquals(expectedTable, actualTable);
	}

	@Test
	public void testUpdate() throws Exception {
		HibernateUserDao userDao = new HibernateUserDao();

		userDao.update(new User(1L, "Clerk", "newPass", "newemail@mail.com", "Rob", "Bobbins",
				new Date(new GregorianCalendar(2002, 8, 10).getTimeInMillis()), new Role(2L)));

		IDataSet actualDataSet = tester.getConnection().createDataSet();
		ITable actualTable = actualDataSet.getTable("APP_USERS");

		IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
				.build(HibernateUserDaoTest.class.getClassLoader().getResource("testUpdateUsersExpected.xml"));
		ITable expectedTable = expectedDataSet.getTable("APP_USERS");

		Assertion.assertEquals(expectedTable, actualTable);
	}

	@Test
	public void testRemove() throws Exception {
		HibernateUserDao userDao = new HibernateUserDao();

		userDao.remove(new User(1L, "Clerk", "newPass", "newemail@mail.com", "Rob", "Bobbins",
				new Date(new GregorianCalendar(2002, 10, 10).getTimeInMillis()), new Role(1L)));

		IDataSet actualDataSet = tester.getConnection().createDataSet();
		ITable actualTable = actualDataSet.getTable("APP_USERS");

		IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
				.build(HibernateUserDaoTest.class.getClassLoader().getResource("testRemoveUserExpected.xml"));
		ITable expectedTable = expectedDataSet.getTable("APP_USERS");

		Assertion.assertEquals(expectedTable, actualTable);
	}

	@Test
	public void testFindAll() throws Exception {
		HibernateUserDao userDao = new HibernateUserDao();

		List<User> list = userDao.findAll();

		ResultSet rs = tester.getConnection().getConnection().createStatement().executeQuery("SELECT * FROM APP_USERS");

		int i = 0;
		while (rs.next()) {
			Assert.assertEquals(list.get(i).getEmail(), rs.getString("EMAIL"));
			i++;
		}

		rs.close();
	}

	@Test
	public void testFindByLogin() {
		HibernateUserDao userDao = new HibernateUserDao();

		User user = userDao.findByLogin("ekvach");

		Assert.assertEquals("No records found by Login", new Long(1), user.getId());
		Assert.assertEquals("No records found by Login", "testemail1@test.com", user.getEmail());
	}

	@Test
	public void testFindByEmail() {
		HibernateUserDao userDao = new HibernateUserDao();

		User user = userDao.findByEmail("testemail1@test.com");

		Assert.assertEquals("No records found by Email", new Long(1), user.getId());
		Assert.assertEquals("No records found by Email", "ekvach", user.getLogin());
	}

	@Test
	public void testFindUserByLoginAndPass() {
		HibernateUserDao userDao = new HibernateUserDao();

		User user = userDao.findUserByLoginAndPass("ekvach", "kvachpass");

		Assert.assertEquals("No records found by login and pass", new Long(1), user.getId());
		Assert.assertEquals("No records found by login and pass", "ekvach", user.getLogin());
		Assert.assertEquals("No records found by login and pass", "kvachpass", user.getPassword());
	}

	@Test(expected = NullPointerException.class)
	public void testCreateNull() {
		HibernateUserDao userDao = new HibernateUserDao();

		userDao.create(null);
	}

	@Test(expected = NullPointerException.class)
	public void testUpdateNull() {
		HibernateUserDao userDao = new HibernateUserDao();

		userDao.update(null);
	}

	@Test(expected = NullPointerException.class)
	public void testRemoveNull() {
		HibernateUserDao userDao = new HibernateUserDao();

		userDao.remove(null);
	}

	@Test(expected = NullPointerException.class)
	public void testFindByLoginNull() {
		HibernateUserDao userDao = new HibernateUserDao();

		userDao.findByLogin(null);
	}

	@Test(expected = NullPointerException.class)
	public void testFindByEmailNull() throws Exception {
		HibernateUserDao userDao = new HibernateUserDao();

		userDao.findByEmail(null);
	}

	@Test(expected = NullPointerException.class)
	public void testFindUserByLoginAndPassNull() throws Exception {
		HibernateUserDao userDao = new HibernateUserDao();

		userDao.findUserByLoginAndPass(null, null);
	}

	@Test(expected = NullPointerException.class)
	public void testFindUserByLoginAndPassNullLogin() throws Exception {
		HibernateUserDao userDao = new HibernateUserDao();

		userDao.findUserByLoginAndPass(null, "kvachpass");
	}

	@Test(expected = NullPointerException.class)
	public void testFindUserByLoginAndPassNullPass() throws Exception {
		HibernateUserDao userDao = new HibernateUserDao();

		userDao.findUserByLoginAndPass("ekvach", null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindByIdNull() throws Exception {
		HibernateUserDao userDao = new HibernateUserDao();

		userDao.findById(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindByIdNegative() throws Exception {
		HibernateUserDao userDao = new HibernateUserDao();

		userDao.findById(-5L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindByIdZero() throws Exception {
		HibernateUserDao userDao = new HibernateUserDao();

		userDao.findById(0L);
	}
}
