package com.mkl.mkltest.controller.api;

import javax.validation.constraints.NotBlank;

public class UserLogin {
	public static class UserLoginRequest {
		@NotBlank(message = "company can not be blank")
		private String company;
		@NotBlank(message = "phone can not be blank")
		private String phone;
		@NotBlank(message = "password can not be blank")
		private String password;

		public String getCompany() {
			return company;
		}

		public void setCompany(String company) {
			this.company = company;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}
}
