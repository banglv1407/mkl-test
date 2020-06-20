package com.mkl.mkltest.enums;

import java.util.HashMap;
import java.util.Map;

public enum Permission {
	CREATE_ROLE(0),
	UPDATE_ROLE(1),
	DELETE_ROLE(2),
	GET_ROLE(3),
	CREATE_POINT(4),
	UPDATE_POINT(5),
	/*
	DELETE_POINT,
	GET_POINT,
	CREATE_ROUTE,
	UPDATE_ROUTE,
	DELETE_ROUTE,
	GET_ROUTE,
	GET_TRIP,
	GRANT_DRIVER_TRIP,
	CANCEL_TRIP,
	START_TRIP,
	OPEN_STARTED_TRIP,
	LOCK_TRIP,
	CREATE_SEATMAP,
	UPDATE_SEATMAP,
	DELETE_SEATMAP,
	GET_SEATMAP,
	CREATE_SMS_PATTERN,
	UPDATE_SMS_PATTERN,
	DELETE_SMS_PATTERN,
	CREATE_SCHEDULE,
	UPDATE_SCHEDULE,
	DELETE_SCHEDULE,
	GET_SCHEDULE,
	CREATE_VEHICLE,
	UPDATE_VEHICLE,
	DELETE_VEHICLE,
	GET_VEHICLE,
	CREATE_AGENCY,
	UPDATE_AGENCY,
	DELETE_AGENCY,
	GET_AGENCY,
	CREATE_TICKET,
	UPDATE_TICKET,
	GET_TICKET,
	CANCEL_TICKET,
	TRANSFER_TICKET,
	PRINT_TICKET,
	CREATE_GOODS,
	UPDATE_GOODS,
	DELETE_GOODS,
	GET_GOODS,
	COLLECT_GOODS,
	PUSH_GOODS_TO_TRIP,
	CREATE_BILL,
	UPDATE_BILL,
	DELETE_BILL,
	GET_BILL,
	*/
	;
	int index;
	
	Permission(int i) {
		this.index=i;
	}
	
	public static Map<Integer, Permission> PermissionDirectory = new HashMap<Integer, Permission>();
	
	static {
        for (Permission permission: values()) {
        	PermissionDirectory.put(permission.getIndex(), permission);
        }
    }
	
	public static Permission indexOf(int index) {
        return (Permission) PermissionDirectory.get(index);
    }

    public int getIndex() {
        return index;
    }
	
}
