package com.cheers.taskfirst.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cheers.taskfirst.dao.VerificationTokenDao;
import com.cheers.taskfirst.model.VerificationToken;



@Service
public class VerificationTokenBean implements VerificationTokenService {

	
	
	@Autowired
	VerificationTokenDao verificationTokenDao;

	@Override
	public VerificationToken findByTokenValue(String tokenValue) {
		return verificationTokenDao.findTopByTokenValueAndTokenUsedIsNullOrderByCreatedOnDesc(tokenValue);
	}

	@Override
	public VerificationToken addVerificationToken(VerificationToken verificationToken) {
		if (verificationToken == null ) {
			throw new IllegalArgumentException("VerificationToken must not be null");
		}
		VerificationToken verificationTokenCreated = verificationTokenDao.save(verificationToken);
		return verificationTokenCreated;
	}

	@Override
	public VerificationToken updateVerificationToken(VerificationToken verificationToken) {
		
		VerificationToken verificationTokenUpdated = verificationTokenDao.save(verificationToken);
		return verificationTokenUpdated;
	}
	
	@Override
	public boolean enableUserByToken(VerificationToken verificationToken) {
		
		VerificationToken verificationTokenUpdated = null; 
		if(verificationToken!=null )
		{
			verificationToken.getLoginUser().setEnabled(true);
			verificationToken.setTokenUsed(true);			
			verificationTokenUpdated = verificationTokenDao.save(verificationToken);
		}
		if(verificationTokenUpdated!=null)
			return true;
		else
			return false;
	}

	@Override
	public boolean isTokenValid(VerificationToken verificationToken) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(verificationToken.getCreatedOn());
		calendar.add(Calendar.MINUTE, EXPIRATION);
		if(calendar.getTime().compareTo(Calendar.getInstance().getTime()) >= 0)
			return true;
		return false;
	}

}
