package com.utad.beagle.config.security;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utad.beagle.dto.LoginDto;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private ObjectMapper objectMapper;

	private final Validator validator;

	public AuthenticationFilter(ObjectMapper objectMapper, Validator validator) {
		this.objectMapper = objectMapper;
		this.validator = validator;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

		// Check request method.
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		// Check request content-type.
		if (!request.getContentType().startsWith(MediaType.APPLICATION_JSON_VALUE)) {
			throw new AuthenticationServiceException("Login request content-type is not valid");
		}

		// Parse request body.
		LoginDto loginDto = null;
		try (InputStream requestInputStream = request.getInputStream()) {
			loginDto = objectMapper.readValue(requestInputStream, LoginDto.class);
		} catch (IOException e) {
			throw new AuthenticationServiceException("Error reading the login request body");
		}

		// Validate it.
		if (!validator.validate(loginDto).isEmpty()) {
			throw new AuthenticationServiceException("Login request body is not valid");
		}

		// Build the authentication based on that email and the received one time
		// password.
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginDto.getUsername(), loginDto.getPassword());
		setDetails(request, authenticationToken);
		Authentication authentication = this.getAuthenticationManager().authenticate(authenticationToken);

		// Return the authentication.
		return authentication;

	}

}
