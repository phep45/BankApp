package com.luxoft.cjp_krakow_2015.cjp.bankapp.tests;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Client;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.Gender;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.EmailException;
import com.luxoft.cjp_krakow_2015.cjp.bankapp.models.exceptions.InvalidClientNameException;

public class SerializationTest {

	private final String filename = "TEST_CLIENT";
	
	@Test
	public void testSerialization() throws IOException, ClassNotFoundException, InvalidClientNameException, EmailException {
		Client client = new Client("Ala Kot", Gender.FEMALE, "ala@ma.kota", "Kraków", 1000);
		
		FileOutputStream fout = new FileOutputStream(filename+".dat");
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(client);
		oos.close();
		
		FileInputStream fin = new FileInputStream(filename+".dat");
		ObjectInputStream ois = new ObjectInputStream(fin);
		Client deserializedCient = (Client) ois.readObject();
		ois.close();
		
		assertEquals(client, deserializedCient);
	}

}
