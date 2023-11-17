package com.cg.iba.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EMail {

	private String recipient;
	private String subject;
	private String body;

	public EMail(String recepiant, String subject, String body) {
		super();
		this.recipient = recepiant;
		this.subject = "OTP for the Verification ";
		this.body = "This is the OTP for the forgot password";
	}
}
