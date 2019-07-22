package com.utad.machine.controller.api;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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

import com.utad.machine.dto.DeleteSecuredAccountDto;
import com.utad.machine.dto.ModifySecuredAccountDto;
import com.utad.machine.dto.SecuredAccountDto;
import com.utad.machine.dto.SuccessDto;
import com.utad.machine.service.SecuredAccountsService;

@RestController
@RequestMapping("/api/secured-accounts")
@Validated
public class SecuredAccountsController {

	@Autowired
	private SecuredAccountsService securedAccountsService;

	@Autowired
	private HttpSession httpSession;

	@GetMapping("/get-all-my-accounts")
	@PreAuthorize("hasRole('ROLE_USER')")
	public List<SecuredAccountDto> getAllMyAccounts() {

		Long userId = (Long) httpSession.getAttribute("user-id");

		return securedAccountsService.getByUser(userId);

	}

	@GetMapping("/get-my-account")
	@PreAuthorize("hasRole('ROLE_USER')")
	public SecuredAccountDto getMyAccount(
			@RequestParam(name = "securedAccountId", required = true) @NotNull Long securedAccountId) {

		Long userId = (Long) httpSession.getAttribute("user-id");

		return securedAccountsService.getByUserSecuredAccountId(userId, securedAccountId);

	}

	@PostMapping("/create")
	@PreAuthorize("hasRole('ROLE_USER')")
	public SecuredAccountDto create(@RequestBody(required = true) @Valid SecuredAccountDto securedAccountDto) {

		Long userId = (Long) httpSession.getAttribute("user-id");

		return securedAccountsService.create(userId, securedAccountDto);

	}

	@PostMapping("/modify")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ModifySecuredAccountDto modify(
			@RequestBody(required = true) @Valid ModifySecuredAccountDto securedAccountDto) {

		Long userId = (Long) httpSession.getAttribute("user-id");

		return securedAccountsService.modify(userId, securedAccountDto);

	}

	@PostMapping("/delete")

	@PreAuthorize("hasRole('ROLE_USER')")
	public SuccessDto delete(@RequestBody(required = true) @Valid DeleteSecuredAccountDto deleteSecuredAccountDto) {

		Long userId = (Long) httpSession.getAttribute("user-id");

		securedAccountsService.deleteByName(userId, deleteSecuredAccountDto.getName());

		return new SuccessDto(true);

	}

	@GetMapping("/get-all")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<SecuredAccountDto> getAll() {

		return securedAccountsService.getAll();

	}

}
