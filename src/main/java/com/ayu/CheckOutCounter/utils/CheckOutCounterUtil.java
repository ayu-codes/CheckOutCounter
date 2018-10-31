package com.ayu.CheckOutCounter.utils;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CheckOutCounterUtil {
	@Autowired
	private Environment checkOutCounterEnv;

	public String getMessage(Locale locale, String propertyName) {
		if (locale != null && checkOutCounterEnv.getProperty(locale.getLanguage() + "." + propertyName) != null) {
			return checkOutCounterEnv.getProperty(locale.getLanguage() + "." + propertyName);
		} else {
			return checkOutCounterEnv.getProperty(propertyName);
		}
	}
	
	public ResponseEntity<?> getResponse() {
		return null;
	}
}
