package com.cheers.taskfirst.controllers;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cheers.taskfirst.dto.LoginUserDTO;
import com.cheers.taskfirst.dto.ResetPasswordDTO;
import com.cheers.taskfirst.events.OnUserVerificationRequiredEvent;
import com.cheers.taskfirst.model.LoginUser;
import com.cheers.taskfirst.model.UserRole;
import com.cheers.taskfirst.model.VerificationToken;
import com.cheers.taskfirst.model.VerificationToken.VerifyFor;
import com.cheers.taskfirst.service.LoginUserService;
import com.cheers.taskfirst.service.VerificationTokenService;
import com.cheers.taskfirst.utils.TaskFirstConstants;

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
				eventPublisher.publishEvent(new OnUserVerificationRequiredEvent(loginUserAdded, applicationURL, VerifyFor.New_USER));
			} catch (Exception ex) {
				ex.printStackTrace();
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

		if (verificationToken == null || !verificationToken.getVerifyFor().equals(VerifyFor.New_USER)) {
			modelAndView.addObject("mail_verifyEmail", "Invalid token value");
		} else if (!verificationTokenService.isTokenValid(verificationToken)) { // Check
																				// if
																				// valid
			modelAndView.addObject("mail_verifyEmail", "Token has expired, please click here to genereate new.");
		} else if (!verificationTokenService.enableUserByToken(verificationToken)) { // Verify
																						// called
																						// here
			modelAndView.addObject("mail_verifyEmail", "Some error has occurred in verifying email, Please try again after some time.");
		} else {
			modelAndView.addObject("mail_verifyEmail", "Email verified succesfully, please login");
		}
		modelAndView.setViewName("login");
		return modelAndView;

	}

	private LoginUser createUser(LoginUser loginUser) {
		LoginUser userAdded = null;
		try {
			UserRole userRole = loginUserService.findByAuthority("APP_USER");
			loginUser.addAuthority(userRole);
			userAdded = loginUserService.addLoginUser(loginUser);
		} catch (IllegalArgumentException ex) {
			return null;
		}
		return userAdded;
	}

	@RequestMapping(path = "/resetpasswordlink", method = RequestMethod.POST)
	public ModelAndView sendResetPasswordLink(ModelAndView modelAndView, @RequestParam String emailid, HttpServletRequest httpServletRequest) {
		if (emailid == null || emailid.trim().isEmpty()) {
			modelAndView.addObject(TaskFirstConstants.MESSAGE_TO_USER_FIELD, "Email id provided is null or empty");
			modelAndView.setViewName("baduser");
			return modelAndView;
		} else {
			String applicationURL = httpServletRequest.getContextPath();
			LoginUser loginUserFound = loginUserService.findByUsername(emailid);
			if (loginUserFound == null) {
				modelAndView.addObject(TaskFirstConstants.MESSAGE_TO_USER_FIELD, "Email id provided doesn't exist");
				modelAndView.setViewName("baduser");
				return modelAndView;
			} else {
				eventPublisher.publishEvent(new OnUserVerificationRequiredEvent(loginUserFound, applicationURL, VerifyFor.RESET_PASSWORD));
				modelAndView.addObject(TaskFirstConstants.MESSAGE_TO_USER_FIELD, "A link has been sent to your email ID, please click that to reset password");
				modelAndView.setViewName("baduser");
				return modelAndView;
			}
		}
	}

	@RequestMapping(path = "/resetmypassword", method = RequestMethod.GET)
	public String resetPasswordPage(Model model, @RequestParam(name = "token") String tokenValue, RedirectAttributes redirectAttributes) {

		VerificationToken verificationToken = verificationTokenService.findByTokenValue(tokenValue);

		if (verificationToken == null || !verificationToken.getVerifyFor().equals(VerifyFor.RESET_PASSWORD)) {
			redirectAttributes.addFlashAttribute(TaskFirstConstants.MESSAGE_TO_USER_FIELD, "Invalid token value");
			return "redirect:/baduser";
		} else if (!verificationTokenService.isTokenValid(verificationToken)) { // Check
																				// if
																				// valid
			redirectAttributes.addFlashAttribute(TaskFirstConstants.MESSAGE_TO_USER_FIELD, "Token has expired");
			return "redirect:/baduser";
		} else {
			verificationTokenService.updateVerificationToken(verificationToken.setTokenUsedToTrue());
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication(); System.out.println(auth);
			SecurityContextHolder.getContext().setAuthentication(
					new UsernamePasswordAuthenticationToken(verificationToken.getLoginUser(), null, 
							Arrays.asList(new SimpleGrantedAuthority(TaskFirstConstants.RESET_PASSWORD_ACCESS))));
			redirectAttributes.addFlashAttribute(TaskFirstConstants.MESSAGE_TO_USER_FIELD, "Change password here");
			redirectAttributes.addFlashAttribute("resetPasswordDTO", new ResetPasswordDTO(verificationToken.getLoginUser().getUsername()));
			return "redirect:/resetpasswordpage";
			
		/*	model.addAttribute(TaskFirstConstants.MESSAGE_TO_USER_FIELD, "Change password here");
			model.addAttribute("resetPasswordDTO", new ResetPasswordDTO(verificationToken.getLoginUser().getUsername()));
			return "resetpasswordpage"; */
		}
		
	}

	@RequestMapping(path = "/resetpassword", method = RequestMethod.POST)
	public ModelAndView resetPassword(ModelAndView modelAndView, @ModelAttribute @Valid ResetPasswordDTO resetPasswordDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors() || resetPasswordDTO == null) {
			modelAndView.setViewName("resetPasswordPage");
			modelAndView.addObject("resetPasswordDTO", resetPasswordDTO);
			return modelAndView;
		}

		//LoginUser loginUserFound = loginUserService.findByUsername(resetPasswordDTO.getUsername());
		LoginUser loginUserFound = (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		loginUserFound.setPassword(passwordEncoder.encode(resetPasswordDTO.getPassword()));
		LoginUser loginUserUpdated = loginUserService.updateLoginUser(loginUserFound);
		//SecurityContextHolder.getContext().setAuthentication(null);
		if (loginUserUpdated != null) {
			modelAndView.setViewName("login");
			modelAndView.addObject(TaskFirstConstants.MESSAGE_TO_USER_FIELD, "Password changed succesfully.");
			return modelAndView;

		}
		modelAndView.setViewName("baduser");
		modelAndView.addObject(TaskFirstConstants.MESSAGE_TO_USER_FIELD, "Something went wrong, Please try again.");		
		return modelAndView;

	}

}