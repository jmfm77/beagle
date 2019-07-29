package com.utad.machine.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CreateUserDto {

	@NotBlank
	@Size(min = 6, max = 256)
	@Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
	private String username;

	@NotBlank
	@Size(min = 8, max = 256)
	// Minimum eight characters, at least one uppercase letter, one lowercase
	// letter, one number and one special character
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&].*$")
	private String password1;

	@NotBlank
	@Size(min = 8, max = 256)
	// Minimum eight characters, at least one uppercase letter, one lowercase
	// letter, one number and one special character
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&].*$")
	private String password2;

	@NotBlank
	@Pattern(regexp = "ROLE_USER")
	private String role;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

}
