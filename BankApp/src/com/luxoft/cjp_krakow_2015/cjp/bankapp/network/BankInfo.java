package com.luxoft.cjp_krakow_2015.cjp.bankapp.network;

import java.io.Serializable;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Bank;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.service.BankReport;

public class BankInfo implements Serializable {

	private static final long serialVersionUID = 6261508377320038034L;

	private final Bank bank;
	
	public BankInfo(Bank bank) {
		this.bank = bank;
	}

	public String getStatistics() {
		return BankReport.getFullReport(bank);
	}
	
	
}
