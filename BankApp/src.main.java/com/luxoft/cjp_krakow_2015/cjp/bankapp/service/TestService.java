package com.luxoft.cjp_krakow_2015.cjp.bankapp.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.luxoft.cjp_krakow_2015.cjp.bankapp.database.NoDB;

public class TestService {

	public static boolean isEquals(Object o1, Object o2) {
		Field[] fieldsObject1 = o1.getClass().getDeclaredFields();
		Field[] fieldsObject2 = o2.getClass().getDeclaredFields();
		
		for(int i = 0; i < fieldsObject1.length; i++) {
			fieldsObject1[i].setAccessible(true);
			Annotation[] annotations = fieldsObject1[i].getAnnotations();
			for(Annotation a : annotations) {
				if(a instanceof NoDB) {
					fieldsObject1[i] = null;
				}
			}
		}
		for(int i = 0; i < fieldsObject2.length; i++) {
			fieldsObject2[i].setAccessible(true);
			Annotation[] annotations = fieldsObject2[i].getAnnotations();
			for(Annotation a : annotations) {
				if(a instanceof NoDB) {
					fieldsObject2[i] = null;
				}
			}
		}
		for(int i = 0; i < fieldsObject1.length; i++) {
			try {
				if(!fieldsObject1[i].get(o1).equals(fieldsObject2[i].get(o2)))
					return false;
			} catch (IllegalArgumentException | IllegalAccessException | NullPointerException e) {
				continue;
			}
		}

		return true;

	}
}
