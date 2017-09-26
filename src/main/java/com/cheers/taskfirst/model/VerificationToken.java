package com.cheers.taskfirst.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;



@Entity
public class VerificationToken extends AbstractModelParent {

	public enum VerifyFor{
		New_USER, RESET_PASSWORD
	};
	
	private static final long serialVersionUID = 1L;
	
	@NotNull
	private String tokenValue;
	
	private Boolean tokenUsed;
	
	private VerifyFor verifyFor;
	
	public VerifyFor getVerifyFor() {
		return verifyFor;
	}

	public void setVerifyFor(VerifyFor verifyFor) {
		this.verifyFor = verifyFor;
	}

	public Boolean getTokenUsed() {
		return tokenUsed;
	}

	public VerificationToken(){}
	
	public VerificationToken(String tokenValue, LoginUser loginUser){
		this.tokenValue = tokenValue;
		this.loginUser = loginUser;
	}
	public VerificationToken(String tokenValue, LoginUser loginUser, VerifyFor verifyFor){
		this.tokenValue = tokenValue;
		this.loginUser = loginUser;
		this.verifyFor = verifyFor;
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
	
	public void setTokenUsed(Boolean tokenUsed) {
		this.tokenUsed = tokenUsed;
	}
	
	public VerificationToken setTokenUsedToTrue() {
		this.setTokenUsed(true);
		return this;
	}
}
