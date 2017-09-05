package com.cheers.taskfirst.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cheers.taskfirst.model.TaskFirstUserDetails;

@Repository
public interface TaskFirstUserDetailsDao extends JpaRepository<TaskFirstUserDetails, Long>{
	

	public TaskFirstUserDetails findByUsername(String username);
	
	
}
