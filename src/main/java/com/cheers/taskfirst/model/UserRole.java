package com.cheers.taskfirst.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;

@Entity
public class UserRole  extends AbstractModelParent implements GrantedAuthority {

	@NotNull
	private String  authority;
	
	private static final long serialVersionUID = 1L;

	public UserRole(String authority){
		this.authority = authority ; 
	}
	
	@Override
	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	
}
