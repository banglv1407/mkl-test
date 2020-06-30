package com.mkl.mkltest.controller.api;

import javax.validation.constraints.NotBlank;

public class UserRegister {
		@NotBlank(message = "userName can not blank")
		private String userName;
		@NotBlank(message = "password can not blank")
        private String password;
        @NotBlank(message = "phoneNumber can not blank")
        private String phoneNumber;
        @NotBlank(message = "bankAccountNumber can not blank")
        private String bankAccountNumber;
        private String bankCode;
        private Long birthDay;
        private String fullName;
        private String zalo;
        @NotBlank(message = "secretWord can not blank")
        private String secretWord;

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

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getBankAccountNumber() {
            return bankAccountNumber;
        }

        public void setBankAccountNumber(String bankAccountNumber) {
            this.bankAccountNumber = bankAccountNumber;
        }

        public String getZalo() {
            return zalo;
        }

        public void setZalo(String zalo) {
            this.zalo = zalo;
        }

        public Long getBirthDay() {
            return birthDay;
        }

        public void setBirthDay(Long birthDay) {
            this.birthDay = birthDay;
        }

        public String getBankCode() {
            return bankCode;
        }

        public void setBankCode(String bankCode) {
            this.bankCode = bankCode;
        }

        public String getSecretWord() {
            return secretWord;
        }

        public void setSecretWord(String secretWord) {
            this.secretWord = secretWord;
        }

	
}
