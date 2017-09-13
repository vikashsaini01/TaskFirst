package com.cheers.taskfirst.events;

import org.springframework.context.ApplicationEvent;

import com.cheers.taskfirst.model.LoginUser;

public class OnUserCreatedEvent extends ApplicationEvent{

	private static final long serialVersionUID = 1L;

	private LoginUser loginUser;
	private String applicationUrl;
	public OnUserCreatedEvent(LoginUser source, String applicationURL) {
		super(source);
		this.applicationUrl = applicationURL;
		this.loginUser = source;
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