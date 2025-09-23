package com.medicaloperations.MedOps.entities.enums;

public enum AccountStatus {

	ACTIVE(1),
	BLOCKED(2),
	PENDING(3);
	
	private int code;
	
	private AccountStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	public static AccountStatus valueOf(int code) {
		for (AccountStatus value : AccountStatus.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}
		throw new IllegalArgumentException("Ivalid Account Status");
	}
	
	
	
	
}
