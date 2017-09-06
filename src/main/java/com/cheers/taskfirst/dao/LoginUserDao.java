package com.cheers.taskfirst.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cheers.taskfirst.model.LoginUser;

@Repository
public interface LoginUserDao extends JpaRepository<LoginUser, Long>{
	

	public LoginUser findByUsername(String username);
	
	
}
