package com.utad.machine.controller.api;

import java.util.List;

import javax.servlet.http.HttpSession;
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
import com.utad.machine.dto.ModifyUserDto;
import com.utad.machine.dto.SuccessDto;
import com.utad.machine.dto.UserDto;
import com.utad.machine.service.UsersService;

@RestController
@RequestMapping("/api/user")
@Validated
public class UsersController {

	@Autowired
	private HttpSession httpSession;

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

	public UserDto create(@RequestBody(required = true) @Valid CreateUserDto createUserDto) {

		return usersService.create(createUserDto.getUsername(), createUserDto.getPassword1(),
				createUserDto.getPassword2(), createUserDto.getRole());

	}

	@PostMapping("/modify")

	@PreAuthorize("hasRole('ROLE_USER')")
	public SuccessDto modify(@RequestBody(required = true) @Valid ModifyUserDto modifyUserDto) {

		Long userId = (Long) httpSession.getAttribute("user-id");

		usersService.modify(userId, modifyUserDto.getOldPassword(), modifyUserDto.getNewPassword1(),
				modifyUserDto.getNewPassword2());

		return new SuccessDto(true);

	}

	@GetMapping("/delete")
	@PreAuthorize("hasRole('ROLE_USER')")
	public SuccessDto delete() {
		Long userId = (Long) httpSession.getAttribute("user-id");
		usersService.deleteByUserId(userId);

		return new SuccessDto(true);

	}

}
