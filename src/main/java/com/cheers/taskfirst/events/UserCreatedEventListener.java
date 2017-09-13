package com.cheers.taskfirst.events;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.cheers.taskfirst.model.LoginUser;
import com.cheers.taskfirst.model.VerificationToken;
import com.cheers.taskfirst.service.LoginUserService;
import com.cheers.taskfirst.service.VerificationTokenService;

@Component
public class UserCreatedEventListener implements ApplicationListener<OnUserCreatedEvent> {

	@Autowired
	LoginUserService loginUserService;

	@Autowired
	VerificationTokenService verificationTokenService;

	@Autowired
	JavaMailSender mailSender;
	
	@Override
	public void onApplicationEvent(OnUserCreatedEvent onUserCreatedevent) {
		LoginUser loginUser = onUserCreatedevent.getLoginUser();
		String tokenValue = UUID.randomUUID().toString();
		String verificationUrl = "http://localhost:8080"+onUserCreatedevent.getApplicationUrl() + "/verifyemail?token=" + tokenValue;
		VerificationToken verificationToken = new VerificationToken(tokenValue, loginUser);
		//VerificationToken verificationTokenAdded =
				verificationTokenService.addVerificationToken(verificationToken);
		
		String userEmail = loginUser.getUsername();
		String subject = "Verify email for your TaskFirst account" ;
		
		SimpleMailMessage emailMessage = new SimpleMailMessage();
		emailMessage.setTo(userEmail);
		emailMessage.setSubject(subject);
		emailMessage.setText(verificationUrl);
		
		mailSender.send(emailMessage);

	}

}