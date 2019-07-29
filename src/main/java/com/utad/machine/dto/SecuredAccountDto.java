package com.utad.machine.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SecuredAccountDto {

	private Long securedAccountId;

	@NotBlank
	@Size(min = 1, max = 100)
	private String name;

	@Size(max = 250)
	private String description;

	@NotBlank
	@Size(min = 1, max = 256)
	private String username;

	@NotBlank
	@Size(min = 1, max = 256)
	private String password;

	@Size(max = 500)
	private String uri;

	public Long getSecuredAccountId() {
		return securedAccountId;
	}

	public void setSecuredAccountId(Long securedAccountId) {
		this.securedAccountId = securedAccountId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

}
