package org.spring.cache.defaultcache;

import java.io.Serializable;

public class Account implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1778673112099163878L;
	private int id;
	private String name;
	private String password;
	public Account(String name) {
		this.name = name;
	}
	public Account(String name,String password) {
		this.name = name;
		this.password= password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public String getPassword() {
		return password;
	}
	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String string) {
		// TODO Auto-generated method stub

	}
}