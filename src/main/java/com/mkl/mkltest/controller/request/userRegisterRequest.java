package com.mkl.mkltest.controller.request;

// import javax.validation.constraints.NotBlank;

public class userRegisterRequest {
	public static class UserRegisterRequest {
		// @NotBlank(message = "userName can not blank")
		private String userName;
		// @NotBlank(message = "password can not blank")
        private String password;
        private String phone;
        private String birthYear;
        private String fullName;


		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getBirthYear() {
            return birthYear;
        }

        public void setBirthYear(String birthYear) {
            this.birthYear = birthYear;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

	}
}
