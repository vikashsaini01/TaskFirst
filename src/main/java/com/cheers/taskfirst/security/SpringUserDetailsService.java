package com.cheers.taskfirst.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cheers.taskfirst.model.LoginUser;
import com.cheers.taskfirst.service.LoginUserService;

@Service
public class SpringUserDetailsService implements UserDetailsService {

	@Autowired
	LoginUserService taskFirstUserDetailsService;
	
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LoginUser loginUser = taskFirstUserDetailsService.findByUsername(username);
		loginUser.getAuthorities();
		return loginUser;

/*		if (loginUser == null) {
			return null;
		}

		Collection<GrantedAuthority> authorities = new HashSet<>();
		
		authorities.addAll(loginUser.getAuthorities());
		
		User userDetails = new User(loginUser.getUsername(), loginUser.getPassword(), loginUser.isEnabled(),
				loginUser.isAccountNonExpired(), loginUser.isCredentialsNonExpired(), loginUser.isAccountNonLocked(),
				authorities);
		return userDetails;
		*/		

	}

}
