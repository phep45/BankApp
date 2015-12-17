package com.luxoft.cjp_krakow_2015.cjp.bankapp.network.commands;

import java.io.Serializable;

//still not sure about idea of sending this kind of objects as messages...
public interface NetCommand extends Serializable {
	public void printInfo();
}
