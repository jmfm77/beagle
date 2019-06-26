package com.utad.machine.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class LoginDto {

    @NotBlank
    @Size(min = 9, max = 9)
    @Pattern(regexp = "\\d{8}[A-Z]")
    private String username;

    @NotBlank
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(
            String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(
            String password) {
        this.password = password;
    }

}
