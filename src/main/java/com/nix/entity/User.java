package com.nix.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity(name = "APP_USERS")
public class User {

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Positive
	@Column(name = "ID")
	private Long id;
	@Column(name = "LOGIN")
	@NotNull
	@Size(min = 3)
	@NotBlank
	private String login;
	@Column(name = "PASSWORD")
	@NotNull
	@NotBlank
	@Size(min = 4)
	private String password;
	@Email
	@NotBlank
	@Column(name = "EMAIL")
	private String email;
	@Column(name = "FIRSTNAME")
	private String firstName;
	@Column(name = "LASTNAME")
	private String lastName;
	@Past
	@Temporal(TemporalType.DATE)
	@Column(name = "BIRTHDAY")
	private Date birthday;
//	@Positive
//	@Column(name = "USER_ROLEID")
//	private Long roleId;
	@Transient
	private int age;

	@ManyToOne
	@JoinColumn(name = "USER_ROLEID", nullable = false)
	@NotNull
	private Role userRole;

	public User() {
	}

	// Constructor with required fields
	public User(Long id, String login, String password, String email, Role userRole) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.email = email;
		this.userRole = userRole;
	}

	// Constructor with all the fields
	public User(Long id, String login, String password, String email, String firstName, String lastName, Date birthday,
			Role userRole) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.userRole = userRole;
	}

	// Constructor with fields from the site user creation form
	public User(String login, String password, String email, String firstName, String lastName, Date birthday,
			Role userRole) {
		this.login = login;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.userRole = userRole;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Role getUserRole() {
		return userRole;
	}

	public void setUserRole(Role userRole) {
		this.userRole = userRole;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", password=" + password + ", email=" + email + ", firstName="
				+ firstName + ", lastName=" + lastName + ", birthday=" + birthday + ", age=" + age + ", userRole="
				+ userRole + "]";
	}

}
