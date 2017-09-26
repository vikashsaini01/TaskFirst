package com.cheers.taskfirst.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cheers.taskfirst.model.UserRole;

@Repository
public interface UserRoleDao extends JpaRepository<UserRole, Long> {

		public UserRole findByAuthority(String authority);
		
		public UserRole findById(Long id);
}
