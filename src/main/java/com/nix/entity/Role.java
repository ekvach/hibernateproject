package com.nix.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity(name = "USER_ROLE")
public class Role {

	@Id
	@Positive
	private Long id;

	@NotNull
	private String name;

	public Role() {
	}

	public Role(Long id) {
		this.id = id;
	}

	public Role(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
