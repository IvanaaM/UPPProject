package com.ftn.service;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private Environment env;
	
	public void sendEmail(String name, String email) {
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Aktivacija naloga");
		mail.setText("Dobrodosli " + name + "! \n\n " + "Da zavrsite registraciju i aktivirate Vas nalog kliknite na sledeci link:"
				+ "\n\n http://localhost:8080/user/confirmMail \n\n Naucna Centrala \n\n");
		
		javaMailSender.send(mail);

	}
}
