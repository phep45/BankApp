package com.luxoft.cjp_krakow_2015.cjp.bankapp.models;

public enum Gender {

	MALE("Mr."),
	FEMALE("Mrs.");
	
	private String salutation;
	
	public String getSalutation() {
		return salutation;
	}
	
	private Gender(String salutation) {
		this.salutation = salutation;
	}
}
