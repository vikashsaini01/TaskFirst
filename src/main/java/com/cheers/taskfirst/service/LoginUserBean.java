package com.cheers.taskfirst.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cheers.taskfirst.dao.LoginUserDao;
import com.cheers.taskfirst.model.LoginUser;

@Service
public class LoginUserBean implements LoginUserService {

	@Autowired
	LoginUserDao loginUserDao;   
	
	@Override
	public LoginUser findByUsername(String username) {
		return loginUserDao.findByUsername(username);
	}

	@Override
	public LoginUser addLoginUser(LoginUser loginUser)throws IllegalArgumentException {
		if(!isUserExists(loginUser))
			return loginUserDao.save(loginUser);
		else
			throw new IllegalArgumentException("Username " + loginUser.getUsername() +" already exists" );
	}
	
	private boolean isUserExists(LoginUser loginUser){
		if(loginUserDao.findByUsername(loginUser.getUsername())!=null){
			return true;
		}
		return false;
	}
	
	public boolean enableLoginUser(final String username)throws IllegalArgumentException{
		LoginUser loginUserPresent = findByUsername(username); 
		loginUserPresent.setEnabled(true);
		LoginUser loginUserUpdated = loginUserDao.save(loginUserPresent);
		if(loginUserUpdated.isEnabled()==true)
			return true;
		else
		{	
			throw new IllegalArgumentException("User couldn't be updated " );
		}
	}

}
