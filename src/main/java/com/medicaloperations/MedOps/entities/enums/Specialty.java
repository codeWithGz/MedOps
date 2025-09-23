package com.medicaloperations.MedOps.entities.enums;

public enum Specialty {

	CLINICO(1), ENFERMEIRO(2), AUXENFERMAGEM(3), DENTISTA(4), FARMACEUTICO(5);
	
	private int code;

	private Specialty(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public static Specialty valueOf(int code) {
		for (Specialty value : Specialty.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}
		throw new IllegalArgumentException("Ivalid Account Status");
	}

}
