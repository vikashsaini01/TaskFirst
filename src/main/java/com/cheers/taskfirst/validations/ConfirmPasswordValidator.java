package com.cheers.taskfirst.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.cheers.taskfirst.dto.LoginUserDTO;

public class ConfirmPasswordValidator implements ConstraintValidator<ValidConfirmPassword, Object>{

	@Override
	public void initialize(ValidConfirmPassword constraintAnnotation) {		
	}

	
	@Override
	public boolean isValid(Object candidate, ConstraintValidatorContext context) {
		if(candidate instanceof LoginUserDTO){
			LoginUserDTO loginUser = (LoginUserDTO)candidate;
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