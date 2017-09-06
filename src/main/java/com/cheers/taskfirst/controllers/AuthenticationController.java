package com.cheers.taskfirst.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cheers.taskfirst.model.LoginUser;
import com.cheers.taskfirst.service.LoginUserService;

@Controller
public class AuthenticationController {

	@Autowired
	LoginUserService loginUserService;

	/* This function is called to sign up for the new user 
	 * It validates the LoginUser and checks if the username already exists
	 */
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public ModelAndView signUp(ModelAndView modelAndView, @RequestBody @Valid LoginUser loginUser, BindingResult bindingResult) {
		LoginUser loginUserAdded = null;

		if (!bindingResult.hasErrors()) {
			loginUserAdded = createUser(loginUser);
		}
		if (loginUserAdded == null) {
			bindingResult.rejectValue("username", "message.regError");
		}
		
		/* If user is added successfully, return the addedUser instance as model
		 * If not then return the same so that earlier values are bound to form
		 */
		if (!bindingResult.hasErrors()) {
			modelAndView.setViewName("signupsuccess");
			modelAndView.addObject("loginUser", loginUserAdded);
		} else {
			modelAndView.setViewName("signup");
			modelAndView.addObject("loginUser", loginUser);
		}
		return modelAndView;
	}

	
	
	private LoginUser createUser(LoginUser loginUser) {
		LoginUser userAdded = null;
		try {
			loginUserService.addLoginUser(loginUser);
		} catch (IllegalArgumentException ex) {
			return null;
		}
		return userAdded;
	}
}
