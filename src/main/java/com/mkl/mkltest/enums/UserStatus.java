package com.mkl.mkltest.enums;

public enum UserStatus {
    NEW(0), // User mới
    NORMAL(1), // User đang hoạt động
    LOCKED(2), // User bị khóa
    NEED_CENSORED(3), // User cần kiểm duyệt
    ON_THE_TRIP(4), // User đang chạy xe
	DELETED(5),// User đã bị xóa
	;
	
    private final int value;

    UserStatus(final int newValue) {
        value = newValue;
    }

    public int getValue() {
        return value;
    }
}