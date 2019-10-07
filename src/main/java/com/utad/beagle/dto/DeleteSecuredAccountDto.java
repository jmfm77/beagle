package com.utad.beagle.dto;

import javax.validation.constraints.NotBlank;

public class DeleteSecuredAccountDto {

	@NotBlank
	// @Size(min = 24, max = 24)
	// @Pattern(regexp = "[A-Z]{2}\\d{22}")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
