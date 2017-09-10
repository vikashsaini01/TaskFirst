package com.cheers.taskfirst.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cheers.taskfirst.model.LoginUser;
import com.cheers.taskfirst.service.LoginUserService;

@Controller
public class AuthenticationController {

	@Autowired
	LoginUserService loginUserService;

	/*
	 * This function is called to sign up for the new user It validates the
	 * LoginUser and checks if the username already exists
	 */
	@RequestMapping(value = "/adduser", method = RequestMethod.POST)
	public ModelAndView signUp(ModelAndView modelAndView, @ModelAttribute @Valid LoginUser loginUser, BindingResult bindingResult) {
		LoginUser loginUserAdded = null;

		if (!bindingResult.hasErrors()) {
			loginUserAdded = createUser(loginUser);
			if (loginUserAdded == null) {
				bindingResult.reject("message.regError");
			}
		}		

		/*
		 * If user is added successfully, go to login page
		 * If not then return the loginUser <ModelAttribute> so that earlier values are bound to form
		 */
		if (!bindingResult.hasErrors()) { // Login Success
			modelAndView.setViewName("login");
			modelAndView.addObject("home_userCreatedMessage", "home.userCreatedMessage");
		} else { // Login failure
			modelAndView.setViewName("home");
			modelAndView.addObject("loginUser", loginUser.clearPassword());
		}
		return modelAndView;
	}

	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public ModelAndView getHome(@ModelAttribute LoginUser loginUser) {
		if (loginUser == null)
			loginUser = new LoginUser().clearPassword();

		return new ModelAndView("home", "loginUser", loginUser);
	}

	/*
	 * @RequestMapping(value = "/login", method = RequestMethod.POST) public
	 * ModelAndView showLoginPage(ModelAndView modelAndView) { LoginUser
	 * loginUser = new LoginUser() ; modelAndView.addObject("loginUser",
	 * loginUser); modelAndView.setViewName("login"); return modelAndView; }
	 */

	private LoginUser createUser(LoginUser loginUser) {
		LoginUser userAdded = null;
		try {
			userAdded  = loginUserService.addLoginUser(loginUser);
		} catch (IllegalArgumentException ex) {
			return null;
		}
		return userAdded;
	}
}
