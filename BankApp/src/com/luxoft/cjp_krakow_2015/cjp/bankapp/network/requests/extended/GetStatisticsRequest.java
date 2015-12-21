package com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.extended;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests.Request;

public class GetStatisticsRequest implements Request {

	private static final long serialVersionUID = -8520142309659904575L;

	@Override
	public void printInfo() {
		System.out.println("Get bank statistics");
	}

}
