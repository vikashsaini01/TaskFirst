package com.cheers.taskfirst.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.cheers.taskfirst.validations.ValidConfirmPassword;

@Entity
@ValidConfirmPassword
public class LoginUser extends AbstractModelParent {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min = 6, max = 25)
	@Pattern(regexp = "^[A-Za-z0-9_@!]+$", message = "Only alphanumeric (A-Z, a-z, 0-9 and _,@,!) are allowed")
	private String password;

	@NotNull
	@Transient
	private String confirmpassword;

	@NotNull
	@Email
	@Size(min = 4, max = 30)
	private String username;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "UserAuthority", joinColumns = @JoinColumn(name = "TaskFirstUserDetailsId", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "TaskFirstAuthorityId", referencedColumnName = "id"))
	private Set<UserRole> authorities;

	private boolean accountNonExpired = true;
	private boolean accountNonLocked = true;
	private boolean credentialsNonExpired = true;
	private boolean enabled = true;

	public LoginUser() {
	}

	public LoginUser(String username) {
		this.username = username;
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

	public List<Task> getTasks() {
		return null;
	}

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}

}
