package com.cheers.taskfirst.service;

import com.cheers.taskfirst.model.TaskFirstUserDetails;

public interface TaskFirstUserDetailsService {
	public TaskFirstUserDetails findByUsername(String username);
}
