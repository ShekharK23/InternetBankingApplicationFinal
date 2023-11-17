package com.cg.iba.util.date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DebitCardUtil {
	public String generateNewExpiryDate() {
	    LocalDate currentDate = LocalDate.now();
	    LocalDate newExpiryDate = currentDate.plusYears(2);
	    String strdate = newExpiryDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
	    return strdate;
	}
	
	public long generateNewCardNumber() {
		long newCardNumber = (long) (Math.random()*10); 
	    return newCardNumber;
	}
	
}
