package com.utad.machine.config.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utad.machine.dto.SuccessDto;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private Validator validator;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationFilter authenticationFilter() throws Exception {
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(objectMapper, validator);
		authenticationFilter.setFilterProcessesUrl("/api/session/login");
		authenticationFilter.setAuthenticationSuccessHandler(this::loginSuccessHandler);
		authenticationFilter.setAuthenticationFailureHandler(this::loginFailureHandler);
		authenticationFilter.setAuthenticationManager(authenticationManagerBean());
		return authenticationFilter;
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);
		return authenticationProvider;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Login and Logout.
		http.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class).logout()
				.logoutUrl("/api/session/logout").logoutSuccessHandler(this::logoutSuccessHandler).and()
				.exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));

		// Authentication Provider.
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder);
		http.authenticationProvider(authProvider);

		// Authorization.
		http.authorizeRequests()

				// Allow Base URLs
				.and().authorizeRequests().antMatchers("/api/session/login").permitAll().and().authorizeRequests().and()
				.authorizeRequests().antMatchers("/api/user/create").permitAll().and().authorizeRequests().and()
				.authorizeRequests().antMatchers("/api/user/remember-password").permitAll().and().authorizeRequests()
				.and().authorizeRequests().antMatchers("/api/user/reset").permitAll().and().authorizeRequests()
				.antMatchers("/api/session/logout").permitAll().and().authorizeRequests()
				.antMatchers("/api/session/info").permitAll()

				// Allow Web URLs.
				.and().authorizeRequests().antMatchers("/").permitAll().and().authorizeRequests()
				.antMatchers("/index.html").permitAll().and().authorizeRequests().antMatchers("/js/bundle.js")
				.permitAll().and().authorizeRequests().antMatchers("/css/bundle.css").permitAll().and()
				.authorizeRequests().regexMatchers("/favicon\\.(gif|png|jpe?g|ico|svg)").permitAll().and()
				.authorizeRequests().regexMatchers("/img/.*\\.(gif|png|jpe?g|ico|svg)").permitAll().and()
				.authorizeRequests().regexMatchers("/font/.*\\.(woff2?|eot|ttf)").permitAll()

				// Require Authentication for any other URL
				.and().authorizeRequests().anyRequest().fullyAuthenticated();

		// CSRF.
		http.csrf().disable();

	}

	private void loginSuccessHandler(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {

		// Change session tokens (avoid fixation).
		try {
			request.changeSessionId();
		} catch (IllegalStateException e) {
		}

		// Return an object indicating that the login was successful.
		response.setStatus(HttpStatus.OK.value());
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		objectMapper.writeValue(response.getWriter(), new SuccessDto(true));

	}

	private void loginFailureHandler(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException e) throws IOException {

		// Return an object indicating that the login was unsuccessful.
		response.setStatus(HttpStatus.OK.value());
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		objectMapper.writeValue(response.getWriter(), new SuccessDto(false));

	}

	private void logoutSuccessHandler(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {

		// Return an object indicating that the logout was successful.
		response.setStatus(HttpStatus.OK.value());
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		objectMapper.writeValue(response.getWriter(), new SuccessDto(true));

	}

}
