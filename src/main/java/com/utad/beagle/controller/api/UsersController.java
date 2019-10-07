package com.utad.beagle.controller.api;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.utad.beagle.dto.AdminModifyUserDto;
import com.utad.beagle.dto.AdminUserDto;
import com.utad.beagle.dto.CreateUserDto;
import com.utad.beagle.dto.ModifyUserDto;
import com.utad.beagle.dto.RememberUserDto;
import com.utad.beagle.dto.ResetUserDto;
import com.utad.beagle.dto.SuccessDto;
import com.utad.beagle.dto.UserDto;
import com.utad.beagle.service.UsersService;

@RestController
@RequestMapping("/api/user")
@Validated
public class UsersController {

	@Autowired
	private HttpSession httpSession;

	@Autowired
	private UsersService usersService;

	@GetMapping("/get-all-users")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<AdminUserDto> getAll() {

		return usersService.getAll();

	}

	@GetMapping("/get-user")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public UserDto getUser(@RequestParam(name = "userId", required = true) @NotNull Long userId) {

		return usersService.getByUserId(userId);

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

	@PostMapping("/admin-modify")

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public SuccessDto adminModify(@RequestBody(required = true) @Valid AdminModifyUserDto modifyUserDto) {

		Long adminId = (Long) httpSession.getAttribute("user-id");

		usersService.adminModify(adminId, modifyUserDto.getUserId(), modifyUserDto.getAdminPassword(),
				modifyUserDto.getNewPassword1(), modifyUserDto.getNewPassword2());

		return new SuccessDto(true);

	}

	@GetMapping("/delete")
	@PreAuthorize("hasRole('ROLE_USER')")
	public SuccessDto delete() {
		Long userId = (Long) httpSession.getAttribute("user-id");
		usersService.deleteByUserId(userId);

		return new SuccessDto(true);

	}

	@PostMapping("/admin-delete")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public SuccessDto adminDelete(@RequestBody(required = true) @Valid AdminUserDto deleteAdminUserDto) {

		usersService.deleteByUserId(deleteAdminUserDto.getUserId());

		return new SuccessDto(true);

	}

	@PostMapping("/remember-password")
	public SuccessDto rememberPassword(@RequestBody(required = true) @Valid RememberUserDto rememberUserDto) {

		usersService.rememberPassword(rememberUserDto.getUsername());

		return new SuccessDto(true);

	}

	@GetMapping("/reset")
	public UserDto reset(@RequestParam(name = "token", required = true) @NotBlank String token) {

		return usersService.getByToken(token);
	}

	@PostMapping("/reset")

	public SuccessDto reset(@RequestBody(required = true) @Valid ResetUserDto resetUserDto) {

		usersService.resetUser(resetUserDto);

		return new SuccessDto(true);

	}

}
