package com.inti.formation.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;



/**
 * The persistent class for the USER_TABLE database table.
 * 
 */
@Entity
@Table(name="USER_TABLE")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	@Column(name="USER_NAME")
	private String userName;

	@Column(name="USER_SALARY")
	private BigDecimal userSalary;

	public User() {
	}
	
	public User(long id, String userName) {
		this.id = id;
		this.userName = userName;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public BigDecimal getUserSalary() {
		return this.userSalary;
	}

	public void setUserSalary(BigDecimal userSalary) {
		this.userSalary = userSalary;
	}

}


