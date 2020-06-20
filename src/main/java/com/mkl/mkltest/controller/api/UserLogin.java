package com.mkl.mkltest.controller.api;

import javax.validation.constraints.NotBlank;

public class UserLogin {
	@NotBlank
	String userName;
	@NotBlank
	String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
