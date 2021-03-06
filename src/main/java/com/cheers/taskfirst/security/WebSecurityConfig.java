package com.cheers.taskfirst.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cheers.taskfirst.utils.TaskFirstConstants;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * Registers password encoder as bean, Would be used for hashing the password before saving 
	 * @return PasswordEncoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	/**
	 * Registers AuthenticationProviderBean with PasswordEncoder
	 * @param passwordEncoder
	 * @return DaoAuthenticationProvider	 
	 */
	@Bean
	@Autowired
	public DaoAuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService ){
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		return daoAuthenticationProvider;		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// @formatter:off
		 http
         .authorizeRequests()
             .antMatchers("/", "/home","/login","/adduser","/verifyemail", 
            		 "/baduser", "/resetmypassword","/resetpasswordlink").permitAll()
             .antMatchers("/tasks").hasAnyAuthority("APP_USER","APP_TENURED_USER","ADMIN")
             //Wasn't working as I was using hasRole ;)
             .antMatchers("/resetpasswordpage","/resetpassword").hasAuthority(TaskFirstConstants.RESET_PASSWORD_ACCESS)
             .antMatchers("/resetpasswordpage","/resetpassword").hasRole("")
             .anyRequest().authenticated()
             .and().rememberMe().key("uniqueAndSecret")
             .and()
         .formLogin()
         	 .loginProcessingUrl("/perform_login")
             .loginPage("/login")
             .defaultSuccessUrl("/tasks")
             .permitAll()
             .and()
         .logout()
             .permitAll()
             .logoutSuccessUrl("/login");
		 
		// @formatter:on
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	
	}
}
