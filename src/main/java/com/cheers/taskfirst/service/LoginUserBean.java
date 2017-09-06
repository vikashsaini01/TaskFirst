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

}
