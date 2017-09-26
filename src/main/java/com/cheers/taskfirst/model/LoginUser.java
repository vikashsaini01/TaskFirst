package com.cheers.taskfirst.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.userdetails.UserDetails;

@Entity

public class LoginUser extends AbstractModelParent implements UserDetails {

	private static final long serialVersionUID = 1L;

	@NotNull
	private String password;

	@Transient
	private String confirmpassword;


	@Email
	private String username;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "UserAuthority", joinColumns = @JoinColumn(name = "TaskFirstUserDetailsId", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "TaskFirstAuthorityId", referencedColumnName = "id"))
	private Set<UserRole> authorities;

	private boolean accountNonExpired = true;
	private boolean accountNonLocked = true;
	private boolean credentialsNonExpired = true;
	private boolean enabled = false;

	public LoginUser() {
	}

	public LoginUser(String username, String password) {
		this.username = username;
		this.password= password;
	}

	/**
	 * Clears password (Set to null) and returns the same object
	 * 
	 * @return Same LoginUser instance
	 */
	public LoginUser clearPassword() {
		setPassword(null);
		setConfirmpassword(null);
		return this;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<UserRole> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<UserRole> authorities) {
		this.authorities = authorities;
	}
	
	
	/**
	 * 
	 * @param userRole
	 * @return
	 */
	public boolean addAuthority(UserRole userRole){   
		// Should this be synchronized ??? Is it thread safe as multiple thread might access it (for same instance ?? Yes ?? No ?? )
		
		if(this.getAuthorities()==null) // This is added as by default hibernate returns null if no role 
		{
			this.setAuthorities(new HashSet<UserRole>());
		}
		return this.getAuthorities().add(userRole);
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}

}
