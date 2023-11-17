package com.cg.iba.util.date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TransactionDateConverter {

	private static String DATE = "yyyy-MM-dd";

	public static String convertLocalDateToString(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE);
		return date.format(formatter);
	}
}
