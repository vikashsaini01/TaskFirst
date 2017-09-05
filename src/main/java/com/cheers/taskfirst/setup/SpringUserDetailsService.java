package com.cheers.taskfirst.setup;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cheers.taskfirst.model.TaskFirstUserDetails;
import com.cheers.taskfirst.service.TaskFirstUserDetailsService;

@Service
public class SpringUserDetailsService implements UserDetailsService {

	@Autowired
	TaskFirstUserDetailsService taskFirstUserDetailsService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		TaskFirstUserDetails taskFirstUserDetails = taskFirstUserDetailsService.findByUsername(username);

		if (taskFirstUserDetails == null) {
			return null;
		}

		Collection<GrantedAuthority> authorities = new HashSet<>();
		
		authorities.addAll(taskFirstUserDetails.getAuthorities());
		
		User userDetails = new User(taskFirstUserDetails.getUsername(), taskFirstUserDetails.getPassword(), taskFirstUserDetails.isEnabled(),
				taskFirstUserDetails.isAccountNonExpired(), taskFirstUserDetails.isCredentialsNonExpired(), taskFirstUserDetails.isAccountNonLocked(),
				authorities);
		
		return userDetails;
	}

}
