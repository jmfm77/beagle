package com.utad.machine.controller.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utad.machine.dto.SessionInfoDto;

@RestController
@RequestMapping("/api/session")
public class SessionController {

	@Autowired
	private Environment env;

	@GetMapping("/info")
	public SessionInfoDto sessionInfo() {

		SessionInfoDto sessionInfoDto = new SessionInfoDto();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Authentication and Email.
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			sessionInfoDto.setAuthenticated(true);
			sessionInfoDto.setUsername(authentication.getName());
		}

		// Roles.
		List<String> roles = new ArrayList<>(authentication.getAuthorities().size());
		for (GrantedAuthority authority : authentication.getAuthorities()) {
			roles.add(authority.getAuthority());
		}
		sessionInfoDto.setRoles(roles);

		// recaptcha sitekey
		sessionInfoDto.setSitekey(env.getProperty("google.recaptcha.secret"));

		return sessionInfoDto;

	}

}
