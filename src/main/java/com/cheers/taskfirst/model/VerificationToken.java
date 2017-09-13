package com.cheers.taskfirst.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class VerificationToken extends AbstractModelParent {

	private static final long serialVersionUID = 1L;
	
	@NotNull
	private String tokenValue;
	
	private Boolean tokenUsed;
	
	public VerificationToken(){}
	
	public VerificationToken(String tokenValue, LoginUser loginUser){
		this.tokenValue = tokenValue;
		this.loginUser = loginUser;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	@NotNull
	private LoginUser loginUser;

	public LoginUser getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(LoginUser loginUser) {
		this.loginUser = loginUser;
	}

	public String getTokenValue() {
		return tokenValue;
	}

	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}
	
	public Boolean isTokenUsed() {
		return tokenUsed;
	}

	public void setTokenUsed(Boolean tokenUsed) {
		this.tokenUsed = tokenUsed;
	}
}
