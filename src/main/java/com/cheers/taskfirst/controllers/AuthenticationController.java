package com.cheers.taskfirst.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cheers.taskfirst.dto.LoginUserDTO;
import com.cheers.taskfirst.events.OnUserCreatedEvent;
import com.cheers.taskfirst.model.LoginUser;
import com.cheers.taskfirst.model.VerificationToken;
import com.cheers.taskfirst.service.LoginUserService;
import com.cheers.taskfirst.service.VerificationTokenService;

@Controller
public class AuthenticationController {

	@Autowired
	LoginUserService loginUserService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	VerificationTokenService verificationTokenService;

	@Autowired
	ApplicationEventPublisher eventPublisher;

	/**
	 * This function is called to sign up for the new user It validates the
	 * LoginUser and checks if the username already exists
	 * 
	 * @param modelAndView
	 * @param loginUserDTO
	 * @param bindingResult
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/adduser", method = RequestMethod.POST)
	public ModelAndView signUp(ModelAndView modelAndView, @ModelAttribute @Valid LoginUserDTO loginUserDTO, BindingResult bindingResult,
			HttpServletRequest httpServletRequest) {
		LoginUser loginUserAdded = null;

		if (!bindingResult.hasErrors()) {
			loginUserAdded = createUser(new LoginUser(loginUserDTO.getUsername(), passwordEncoder.encode(loginUserDTO.getPassword())));
			if (loginUserAdded == null) {
				bindingResult.reject("message.regError");
			}
		}

		/*
		 * If user is added successfully, go to login page If not then return
		 * the loginUser <ModelAttribute> so that earlier values are bound to
		 * form
		 */
		if (!bindingResult.hasErrors()) { // Login Success
			modelAndView.setViewName("login");
			modelAndView.addObject("home_userCreatedMessage", "home.userCreatedMessage");

			try {
				String applicationURL = httpServletRequest.getContextPath();
				eventPublisher.publishEvent(new OnUserCreatedEvent(loginUserAdded, applicationURL));
			} catch (Exception ex) {
				ex.printStackTrace();
				try {
					Thread.sleep(3000);
				} catch (InterruptedException ex1) {
				}
			}

		} else { // Login failure
			modelAndView.setViewName("home");
			modelAndView.addObject("loginUserDTO", loginUserDTO.clearPassword());
		}
		return modelAndView;
	}

	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public ModelAndView getHome() {
		return new ModelAndView("home", "loginUserDTO", new LoginUserDTO());
	}

	@RequestMapping(value = "/verifyemail", method = RequestMethod.GET)
	public ModelAndView verifyUserEmail(@RequestParam(name = "token") String tokenValue, ModelAndView modelAndView) {

		VerificationToken verificationToken = verificationTokenService.findByTokenValue(tokenValue);
		
		if(verificationToken==null){
			modelAndView.addObject("mail_verifyEmail", "Invalid token value");
		}
		else if(!verificationTokenService.isTokenValid(verificationToken)){
			modelAndView.addObject("mail_verifyEmail", "Token has expired, please click here to genereate new.");
		}
		else if(!verificationTokenService.enableUserByToken(verificationToken)){
			modelAndView.addObject("mail_verifyEmail", "Some error has occurred in verifying email, Please try again after some time.");
		}
		else{
			modelAndView.addObject("mail_verifyEmail", "Email verified succesfully, please login");			
		}		
		modelAndView.setViewName("/login");		
		return modelAndView;

	}

	private LoginUser createUser(LoginUser loginUser) {
		LoginUser userAdded = null;
		try {
			userAdded = loginUserService.addLoginUser(loginUser);
		} catch (IllegalArgumentException ex) {
			return null;
		}
		return userAdded;
	}

}
