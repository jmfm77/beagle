package com.utad.beagle.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDto {

	@JsonIgnore
	private Long userId;

	private String username;

	@JsonIgnore
	private String password;

	private String role;

	@JsonIgnore
	private String token;

	@JsonIgnore
	private String createdOn;

	@JsonIgnore
	private String lastToken;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getLastToken() {
		return lastToken;
	}

	public void setLastToken(String lastToken) {
		this.lastToken = lastToken;
	}

}
