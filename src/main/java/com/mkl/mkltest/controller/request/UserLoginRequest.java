package com.mkl.mkltest.controller.request;

// import javax.validation.constraints.NotBlank;

public class UserLoginRequest {
		// @NotBlank(message = "userName can not blank")
		private String userName;
		// @NotBlank(message = "password can not blank")
        private String password;


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
}
