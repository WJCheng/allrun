package com.tarena.allrun.entity;

import java.io.Serializable;

public class UserEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String User;
	private String pwd;
	private String cPwd;
	private String name;
	
	
	public UserEntity() {
		super();
	}
	public String getUser() {
		return User;
	}
	public void setUser(String user) {
		User = user;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getcPwd() {
		return cPwd;
	}
	public void setcPwd(String cPwd) {
		this.cPwd = cPwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
