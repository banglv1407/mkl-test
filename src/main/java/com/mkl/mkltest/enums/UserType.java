package com.mkl.mkltest.enums;

public enum UserType {
	OWNER(0), CUSTOMER(1), OFFICE_STAFF(2), DRIVER_STAFF(3), AGENCY(4);

	private final int value;
	
	UserType(final int newValue) {
		value = newValue;
	}

	public int getValue() {
		return value;
	}
}
