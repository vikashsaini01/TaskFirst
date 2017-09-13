package com.cheers.taskfirst.service;

import com.cheers.taskfirst.model.LoginUser;

public interface LoginUserService {
	public LoginUser findByUsername(String username);
	
	public LoginUser addLoginUser(LoginUser loginUser)throws IllegalArgumentException;
	
	public boolean enableLoginUser(String username)throws IllegalArgumentException;
}
