package com.nix;

import java.util.Date;
import java.util.Properties;

import org.hibernate.cfg.Configuration;

import com.nix.entity.Role;
import com.nix.entity.User;
import com.nix.hibernatedao.HibernateUserDao;

public class Main {

	public static void main(String[] args) {

//        String baseInitQuery = new TextFromResourceReader().readTextFromResource("dbForDockerInit.sql");
//		UserDao userDao = new JdbcUserDao();
//        try (Connection con = ((AbstractJdbcDao) userDao).createConnection()) {
//            con.createStatement().execute(baseInitQuery);
//        } catch (Exception e) {
//            throw new IllegalArgumentException(e);
//        }
//
//        Role roleAdmin = new Role(1L, "Admin");
//        Role roleCleaner = new Role(2L, "Cleaner");
//        Role roleDirector = new Role(3L, "Director");
//
//        RoleDao roleDao = new JdbcRoleDao();
//        roleDao.create(roleAdmin);
//        roleDao.create(roleCleaner);
//        roleDao.create(roleDirector);
//
//        User userAdmin = new User(1L, "admin", "passwordA", "emailAdmin", 1L);
//        User userCleaner = new User(2L, "cleaner", "passwordC", "emailCleaner", 2L);
//        User userDirector = new User(3L, "ceo", "passwordCeo", "emailCeo", 3L);
//        User userWithAllFields = new User(11L, "FullUser", "passwordA",
//                "emailAdmin@email.com", "Jack", "Jonson",
//                new Date(new GregorianCalendar(2000, Calendar.SEPTEMBER, 5).getTimeInMillis()), 2L);

//        System.out.println("users created");

//        userDao.create(userAdmin);
//        userDao.create(userCleaner);
//        userDao.create(userDirector);
//        userDao.create(userWithAllFields);

//        System.out.println("users added");
//		Date currentDate = new Date();
//		Date userBirthday = new Date();
//		List<User> userList = userDao.findAll();
//		for (User u : userList) {
//			userBirthday.setTime(u.getBirthday().getTime());
//			int age = currentDate.getYear() - userBirthday.getYear();
//			System.out.println(u.toString());
//		}

//        System.out.println("users printed");
//        userDao.remove(userAdmin);
//        userDao.remove(userCleaner);
//        userDao.remove(userDirector);
//        userDao.remove(userWithAllFields);

//		User user = userDao.findById(11L);
//		System.out.println(user.toString());

//		userDao.create(new User(143L, "1234", "1234", "123@email.com", new Role(2L)));

//		HibernateUserDao userDao = new HibernateUserDao(
//				new Configuration().configure("hibernate-test.cfg.xml").buildSessionFactory());
		HibernateUserDao userDao = new HibernateUserDao();
//		User user = userDao.findByLogin("ceo");

//		System.out.println(user.toString());

		System.out.println("-------------------------------");

//		List<User> userList = userDao.findAll();
//		for (User u : userList) {
//			System.out.println(u.toString());
//		}

//		User user = new User(11L, "adminqq", "passwordAqq", "emailAdmin@email.comqq", "Jack", "Jonson",
//				new Date(new GregorianCalendar(2000, 8, 05).getTimeInMillis()), new Role(1L));
		User user = new User();
		user.setLogin("4444");
		user.setBirthday(new Date(1212121212));
		user.setPassword("4444");
		user.setEmail("email.sss@email.om");
		user.setUserRole(new Role(2L));
//		user.setId(12L);
//		User user1 = userDao.findById(1L);
//		System.out.println(user1.toString());
//		userDao.create(user);

		user = userDao.findByLogin("user");
		System.out.println(user.toString());

		Configuration config = new Configuration();
		Properties properties = new Configuration().configure().getProperties();
		String DB_DRIVER = properties.getProperty("hibernate.connection.driver_class");
		String DB_URI = properties.getProperty("hibernate.connection.url");

		System.out.println(DB_DRIVER);
		System.out.println(DB_URI);

//		user = userDao.findById(4L);Ц
//		System.out.println(user.toString());

//		System.out.println(user.getUserRole().getName());
//		System.out.println(user.getBirthday().toString());

//		user = userDao.findUserByLoginAndPass("admin", "admin");
//
//		System.out.println(user.getUserRole().getName());

//		userDao.remove(user);

	}

}
