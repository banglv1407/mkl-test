package com.mkl.mkltest.exception;

public enum ErrorCode {
    UNKNOWN(100),
    NULL_OR_EMPTY(101),
    MISSING(102),
    INVALID(103),
    ALREADY_EXIST(104),
    NOT_EXIST(105),
    ERROR_SAVE(106),
    NOT_HAVE_PERMISSION(107),
    TOKEN_EXPIRED(108),
    DUPLICATE(109),
    IMAGE_NULL(110),
    PRICE_NOT_MATCH(111),
    EXCEED_DEBT_AGENCY(112),
    SEAT_BOOKED(113),
    COMPANY_LOCKED(114),
    ROUTE_LOCKED(115),
    TRIP_LOCKED(116),
    TRIP_STATUS_INVALID(117),
    BOOK_ONLINE_LOCKED(118),
    TICKET_EXIST(119),
    POINT_ORDER_INVALID(120),
    POINT_DO_NOT_SELL_TICKET(121),
    PASSWORD_NOT_MATCH(122),
    EXCEED_VERIFY_CODE(123),
    NOT_ALLOW_PICKING_DROPPING_AT_HOME(124),
    ACCESS_DENIED(125),
    ACCESS_PENDING(126),
    SEND_OTP_ERROR(127),
    EXEED_TIME_BOOK_TICKET(128),
    EXEED_PRINT_TICKET(129);

    private final int value;

    ErrorCode(final int newValue) {
        value = newValue;
    }

    public int getValue() {
        return value;
    }
}
