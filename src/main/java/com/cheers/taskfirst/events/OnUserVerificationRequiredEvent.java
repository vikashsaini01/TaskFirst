package com.cheers.taskfirst.events;

import org.springframework.context.ApplicationEvent;

import com.cheers.taskfirst.model.LoginUser;
import com.cheers.taskfirst.model.VerificationToken.VerifyFor;

public class OnUserVerificationRequiredEvent extends ApplicationEvent{
	
	

	private static final long serialVersionUID = 1L;

	private LoginUser loginUser;
	private String applicationUrl;
	private VerifyFor verifyFor;
	
	
	
	public VerifyFor getVerifyFor() {
		return verifyFor;
	}
	
	public void setVerifyFor(VerifyFor verifyFor) {
		this.verifyFor = verifyFor;
	}
	
	public OnUserVerificationRequiredEvent(LoginUser source, String applicationURL, VerifyFor verifyFor) {
		super(source);
		this.applicationUrl = applicationURL;
		this.loginUser = source;
		this.verifyFor = verifyFor;
	}
	public LoginUser getLoginUser() {
		return loginUser;
	}
	public void setLoginUser(LoginUser loginUser) {
		this.loginUser = loginUser;
	}
	public String getApplicationUrl() {
		return applicationUrl;
	}
	public void setApplicationUrl(String applicationUrl) {
		this.applicationUrl = applicationUrl;
	}
	
	
	
}