package com.cg.iba.serviceimpl;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.cg.iba.entity.EMail;

@Service
public class EMailServiceImpl {

	@Autowired
	private JavaMailSender mailSender;

	@Value("$(spring.mail.username)")
	private String fromMail;

	public void sendMail(EMail mailStructure) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(fromMail);
		simpleMailMessage.setSubject(mailStructure.getSubject());
		simpleMailMessage.setText(mailStructure.getBody());
		simpleMailMessage.setTo(mailStructure.getRecipient());

		mailSender.send(simpleMailMessage);
	}

	public int sendOtp() {
		Random r = new Random();
		return r.nextInt(999999);
	}
}
