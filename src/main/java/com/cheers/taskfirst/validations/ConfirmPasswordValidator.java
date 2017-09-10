package com.cheers.taskfirst.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.cheers.taskfirst.model.LoginUser;

public class ConfirmPasswordValidator implements ConstraintValidator<ValidConfirmPassword, Object>{

	@Override
	public void initialize(ValidConfirmPassword constraintAnnotation) {		
	}

	
	@Override
	public boolean isValid(Object candidate, ConstraintValidatorContext context) {
		if(candidate instanceof LoginUser){
			LoginUser loginUser = (LoginUser)candidate;
			if(loginUser.getPassword()==null && loginUser.getConfirmpassword()==null)
				return true;
			else if(loginUser.getPassword()==null )
				return false;
			return loginUser.getPassword().equals(loginUser.getConfirmpassword());
		}		
		else
			return false;
	}
	
}