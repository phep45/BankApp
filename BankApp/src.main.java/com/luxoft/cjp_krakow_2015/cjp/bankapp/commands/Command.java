package com.luxoft.cjp_krakow_2015.cjp.bankapp.commands;

import java.io.IOException;

public interface Command {
	void execute() throws IOException;
	void printCommandInfo();
}
