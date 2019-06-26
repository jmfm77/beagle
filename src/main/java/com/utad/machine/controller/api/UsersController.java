package com.utad.machine.controller.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utad.machine.dto.CreateUserDto;
import com.utad.machine.dto.DeleteUserDto;
import com.utad.machine.dto.SuccessDto;
import com.utad.machine.dto.UserDto;
import com.utad.machine.service.UsersService;

@RestController
@RequestMapping("/api/user")
@Validated
public class UsersController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/get-all")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<UserDto> getAll() {

		return usersService.getAll();

	}

	@PostMapping("/create")

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public UserDto create(@RequestBody(required = true) @Valid CreateUserDto createUserDto) {

		return usersService.create(createUserDto.getUsername(), passwordEncoder.encode(createUserDto.getPassword()),
				createUserDto.getRole());

	}

	@PostMapping("/delete")

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public SuccessDto delete(@RequestBody(required = true) @Valid DeleteUserDto deleteUserDto) {

		usersService.deleteByUsername(deleteUserDto.getUsername());

		return new SuccessDto(true);

	}

}
