package com.utad.machine.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ResetUserDto {
	@NotBlank
	@Size(max = 288)
	private String token;

	@NotBlank
	@Size(min = 8, max = 256)
	// Minimum eight characters, at least one uppercase letter, one lowercase
	// letter, one number and one special character
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&].*$")
	private String newPassword1;

	@NotBlank
	@Size(min = 8, max = 256)
	// Minimum eight characters, at least one uppercase letter, one lowercase
	// letter, one number and one special character
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&].*$")
	private String newPassword2;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getNewPassword1() {
		return newPassword1;
	}

	public void setNewPassword1(String newPassword1) {
		this.newPassword1 = newPassword1;
	}

	public String getNewPassword2() {
		return newPassword2;
	}

	public void setNewPassword2(String newPassword2) {
		this.newPassword2 = newPassword2;
	}

}
