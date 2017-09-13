package com.cheers.taskfirst.service;

import com.cheers.taskfirst.model.VerificationToken;

public interface VerificationTokenService {
	public static final int EXPIRATION = 60 * 24;

	public VerificationToken findByTokenValue(String tokenValue);
	
	public VerificationToken addVerificationToken(VerificationToken verificationToken);
	
	public VerificationToken updateVerificationToken(VerificationToken verificationToken);
	
	public boolean enableUserByToken(VerificationToken verificationToken) ;
	
	public boolean isTokenValid(VerificationToken verificationToken) ;
}
