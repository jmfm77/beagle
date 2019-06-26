package com.utad.machine.config.security;

import java.util.Arrays;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.utad.machine.dto.UserDto;
import com.utad.machine.service.UsersService;

@Configuration
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UsersService usersService;

	@Autowired
	private HttpSession httpSession;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserDto user = usersService.getByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("");
		}

		httpSession.setAttribute("user-id", user.getUserId());

		return new org.springframework.security.core.userdetails.User(username, user.getPassword(),
				Arrays.asList(new SimpleGrantedAuthority(user.getRole())));

	}

}
