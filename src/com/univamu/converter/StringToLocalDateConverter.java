package com.univamu.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;

public class StringToLocalDateConverter implements Converter<String, LocalDate> {
	
	/**
	 * Convert a give String into LocalDate
	 * @param String
	 * @return Localdate
	 */
	@Override
	public LocalDate convert(String date) {
		if(date.isEmpty())
			return null;
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
		return LocalDate.parse(date, formatter);
	}
	
}
