package com.nix.hibernatedao;

import java.sql.ResultSet;
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
import com.nix.utils.ConfigPropertyLoader;

public class HibernateRoleDaoTest {

	private static IDatabaseTester tester = null;

	@BeforeClass
	public static void setUpClass() throws Exception {
		String DB_DRIVER = ConfigPropertyLoader.getPropertyByName("hibernate.connection.driver_class");
		String DB_URI = ConfigPropertyLoader.getPropertyByName("hibernate.connection.url");
		tester = new JdbcDatabaseTester(DB_DRIVER, DB_URI);
		tester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
		tester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
	}

	@Before
	public void setUp() throws Exception {
		IDataSet dataSet = new FlatXmlDataSetBuilder()
				.build(HibernateRoleDaoTest.class.getClassLoader().getResource("testDbData.xml"));
		tester.setDataSet(dataSet);
		tester.onSetup();
	}

	@After
	public void tearDown() throws Exception {
		tester.onTearDown();
	}

	@Test
	public void testCreate() throws Exception {
		HibernateRoleDao roleDao = new HibernateRoleDao();

		roleDao.create(new Role(3L, "Director"));

		IDataSet actualDataSet = tester.getConnection().createDataSet();
		ITable actualTable = actualDataSet.getTable("USER_ROLE");

		IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
				.build(HibernateRoleDaoTest.class.getClassLoader().getResource("testCreateRoleExpected.xml"));
		ITable expectedTable = expectedDataSet.getTable("USER_ROLE");

		Assertion.assertEquals(expectedTable, actualTable);
	}

	@Test
	public void testUpdate() throws Exception {
		HibernateRoleDao roleDao = new HibernateRoleDao();

		roleDao.update(new Role(1L, "Director"));

		IDataSet actualDataSet = tester.getConnection().createDataSet();
		ITable actualTable = actualDataSet.getTable("USER_ROLE");

		IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
				.build(HibernateRoleDaoTest.class.getClassLoader().getResource("testUpdateRoleExpected.xml"));
		ITable expectedTable = expectedDataSet.getTable("USER_ROLE");

		Assertion.assertEquals(expectedTable, actualTable);
	}

	@Test
	public void testRemove() throws Exception {
		HibernateRoleDao roleDao = new HibernateRoleDao();

		roleDao.remove(new Role(3L));

		IDataSet actualDataSet = tester.getConnection().createDataSet();
		ITable actualTable = actualDataSet.getTable("USER_ROLE");

		IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
				.build(HibernateRoleDaoTest.class.getClassLoader().getResource("testRemoveRoleExpected.xml"));
		ITable expectedTable = expectedDataSet.getTable("USER_ROLE");

		Assertion.assertEquals(expectedTable, actualTable);

	}

	@Test
	public void testFindByName() throws Exception {
		HibernateRoleDao roleDao = new HibernateRoleDao();

		Role role = roleDao.findByName("Admin");

		Assert.assertEquals("No records found by Role name", "Admin", role.getName());
		Assert.assertEquals("No records found by Role name", new Long(1), role.getId());

	}

	@Test
	public void testFindAll() throws Exception {
		HibernateRoleDao roleDao = new HibernateRoleDao();

		List<Role> list = roleDao.findAll();

		ResultSet rs = tester.getConnection().getConnection().createStatement().executeQuery("SELECT * FROM USER_ROLE");

		int i = 0;
		while (rs.next()) {
			Assert.assertEquals(list.get(i).getName(), rs.getString("NAME"));
			i++;
		}

		rs.close();
	}

	@Test(expected = NullPointerException.class)
	public void testCreateNull() {
		HibernateRoleDao roleDao = new HibernateRoleDao();

		roleDao.create(null);
	}

	@Test(expected = NullPointerException.class)
	public void testUpdateNull() {
		HibernateRoleDao roleDao = new HibernateRoleDao();

		roleDao.update(null);
	}

	@Test(expected = NullPointerException.class)
	public void testRemoveNull() {
		HibernateRoleDao roleDao = new HibernateRoleDao();

		roleDao.remove(null);
	}

	@Test(expected = NullPointerException.class)
	public void testFindByNameNull() {
		HibernateRoleDao roleDao = new HibernateRoleDao();

		roleDao.findByName(null);
	}
}
