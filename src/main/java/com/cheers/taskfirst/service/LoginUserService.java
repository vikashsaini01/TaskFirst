package com.cheers.taskfirst.service;

import com.cheers.taskfirst.model.LoginUser;
import com.cheers.taskfirst.model.UserRole;

public interface LoginUserService {
	public LoginUser findByUsername(String username);
	
	public LoginUser addLoginUser(LoginUser loginUser)throws IllegalArgumentException;
	
	public LoginUser updateLoginUser(LoginUser loginUser)throws IllegalArgumentException;
	
	public boolean enableLoginUser(String username)throws IllegalArgumentException;
	
	public UserRole findByAuthority(String authority);
}
