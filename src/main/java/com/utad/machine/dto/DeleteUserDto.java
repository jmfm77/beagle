package com.utad.machine.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class DeleteUserDto {

    @NotBlank
    @Size(min = 9, max = 9)
    @Pattern(regexp = "\\d{8}[A-Z]")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(
            String username) {
        this.username = username;
    }

}
