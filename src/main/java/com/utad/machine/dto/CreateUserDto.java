package com.utad.machine.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CreateUserDto {

    @NotBlank
    @Size(min = 9, max = 9)
    @Pattern(regexp = "\\d{8}[A-Z]")
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    @Pattern(regexp = "ROLE_(ADMIN|USER)")
    private String role;

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

    public String getRole() {
        return role;
    }

    public void setRole(
            String role) {
        this.role = role;
    }

}
