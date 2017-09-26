package com.cheers.taskfirst.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.cheers.taskfirst.validations.ValidConfirmPassword;

@ValidConfirmPassword
public class ResetPasswordDTO {

	@NotNull
	@Size(min = 6, max = 80)
	@Pattern(regexp = "^[A-Za-z0-9_@!]+$", message = "Only alphanumeric (A-Z, a-z, 0-9 and _,@,!) are allowed")
	private String password;

	@NotNull
	private String confirmpassword;
	
	private String username;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}

	public ResetPasswordDTO() {

	}

	public ResetPasswordDTO(String username) {
		this.username= username;
	}

	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
