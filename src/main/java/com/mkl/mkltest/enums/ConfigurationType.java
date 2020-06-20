package com.mkl.mkltest.enums;

public enum ConfigurationType {
	DISPLAY(0), LOGICAL(1);

	private final int value;
	
	ConfigurationType(final int newValue) {
		value = newValue;
	}

	public int getValue() {
		return value;
	}
}
