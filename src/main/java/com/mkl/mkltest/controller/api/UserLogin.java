package com.mkl.mkltest.controller.api;

import javax.validation.constraints.NotBlank;

public class UserLogin {
		@NotBlank(message = "userLogin can not be blank")
		private String userLogin;
		@NotBlank(message = "password can not be blank")
		private String password;

		public String getUserLogin() {
			return userLogin;
		}

		public void setUserLogin(String userLogin) {
			this.userLogin = userLogin;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
}
