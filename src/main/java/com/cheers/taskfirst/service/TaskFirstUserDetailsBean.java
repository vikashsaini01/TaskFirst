package com.cheers.taskfirst.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cheers.taskfirst.dao.TaskFirstUserDetailsDao;
import com.cheers.taskfirst.model.TaskFirstUserDetails;

@Service
public class TaskFirstUserDetailsBean implements TaskFirstUserDetailsService {

	@Autowired
	TaskFirstUserDetailsDao taskFirstUserDetailsDao;   
	
	@Override
	public TaskFirstUserDetails findByUsername(String username) {
		return taskFirstUserDetailsDao.findByUsername(username);
	}

}
