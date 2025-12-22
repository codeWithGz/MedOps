package com.medicaloperations.MedOps.entities.enums;

public enum ExamStatus {

	AGENDADO(1), PROCESSANDO(2), DISPONIVEL(3), CANCELADO(44);

	private int code;

	private ExamStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public static ExamStatus valueOf(int code) {
		for (ExamStatus value : ExamStatus.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}
		throw new IllegalArgumentException("Ivalid Account Status");
	}

}
