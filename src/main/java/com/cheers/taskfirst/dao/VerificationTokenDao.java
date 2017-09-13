package com.cheers.taskfirst.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cheers.taskfirst.model.VerificationToken;

@Repository
public interface VerificationTokenDao extends JpaRepository<VerificationToken, Long>{
	
	
	/**
	 * Deprecated
	 * Use findByTokenValueAndTokenUsedIsNullOrderByCreatedOnDesc(String tokenValue) instead
	 * @param tokenValue
	 * @return
	 */
	@Deprecated
	public VerificationToken findByTokenValue(String tokenValue);
	
	public VerificationToken findTopByTokenValueAndTokenUsedIsNullOrderByCreatedOnDesc(String tokenValue);
}
