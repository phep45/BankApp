package com.luxoft.cjp_krakow_2015.cjp.bankapp.network.requests;

import java.io.Serializable;

//still not sure about idea of sending this kind of objects as messages...
public interface Request extends Serializable {
	public void printInfo();
}
