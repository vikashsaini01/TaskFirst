package com.cheers.taskfirst.validations;

import java.lang.reflect.Method;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ConfirmPasswordValidator implements ConstraintValidator<ValidConfirmPassword, Object>{

	@Override
	public void initialize(ValidConfirmPassword constraintAnnotation) {		
	}

	
	@Override
	public boolean isValid(Object candidate, ConstraintValidatorContext context) {
		
		try {
			Method methodGetPassword = candidate.getClass().getMethod("getPassword");
			Method methodGetConfirmpassword = candidate.getClass().getMethod("getConfirmpassword");
			
			if(methodGetPassword.invoke(candidate) == null && methodGetConfirmpassword.invoke(candidate)==null) 
				return true;
			else if(methodGetPassword.invoke(candidate) == null )
				return false;
			return methodGetPassword.invoke(candidate).equals(methodGetConfirmpassword.invoke(candidate));
					
		} catch (NoSuchMethodException ex) {
			ex.printStackTrace();
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		
		
		/*
		if(candidate instanceof LoginUserDTO){
			LoginUserDTO loginUser = (LoginUserDTO)candidate;
			if(loginUser.getPassword()==null && loginUser.getConfirmpassword()==null)
				return true;
			else if(loginUser.getPassword()==null )
				return false;
			return loginUser.getPassword().equals(loginUser.getConfirmpassword());
		}		
		else
			return false;*/
	}
	
}