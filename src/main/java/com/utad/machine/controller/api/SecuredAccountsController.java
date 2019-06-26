package com.utad.machine.controller.api;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utad.curso.desarrollo.seguro.dto.AllowUserToBankAccountDto;
import com.utad.curso.desarrollo.seguro.dto.BankAccountDto;
import com.utad.curso.desarrollo.seguro.dto.FundsDto;
import com.utad.machine.dto.SecuredAccountDto;
import com.utad.machine.dto.SuccessDto;

@RestController
@RequestMapping("/api/bank-account")
@Validated
public class SecuredAccountsController {

	@Autowired
	private SecuredAccountsService bankAccountsService;

	@Autowired
	private HttpSession httpSession;

	@GetMapping("/get-all-my-accounts")
	@PreAuthorize("hasRole('ROLE_USER')")
	public List<SecuredAccountDto> getAllMyAccounts() {

		Long userId = (Long) httpSession.getAttribute("user-id");

		return securedAccountsService.getByOwner(userId);

	}

	@PostMapping("/create")
	@PreAuthorize("hasRole('ROLE_USER')")
	public securedAccountDto create() {

		Long userId = (Long) httpSession.getAttribute("user-id");

		return securedAccountsService.create(userId);

	}

	@PostMapping("/delete")
	@PreAuthorize("hasRole('ROLE_USER')")
	public SuccessDto delete(@RequestBody(required = true) @Valid DeleteSecuredAccountDto deleteBankAccountDto) {

		Long userId = (Long) httpSession.getAttribute("user-id");

		securedAccountsService.deleteByIban(userId, deleteBankAccountDto.getIban());

		return new SuccessDto(true);

	}

	@PostMapping("/allow-user")
	@PreAuthorize("hasRole('ROLE_USER')")
	public SuccessDto allowUser(
			@RequestBody(required = true) @Valid AllowUserToBankAccountDto allowUserToBankAccountDto) {

		Long userId = (Long) httpSession.getAttribute("user-id");

		bankAccountsService.allowUserToIban(userId, allowUserToBankAccountDto.getUsername(),
				allowUserToBankAccountDto.getIban());

		return new SuccessDto(true);

	}

	@PostMapping("/disallow-user")
	@PreAuthorize("hasRole('ROLE_USER')")
	public SuccessDto disallowUser(
			@RequestBody(required = true) @Valid AllowUserToBankAccountDto allowUserToBankAccountDto) {

		Long userId = (Long) httpSession.getAttribute("user-id");

		bankAccountsService.disallowUserFromIban(userId, allowUserToBankAccountDto.getUsername(),
				allowUserToBankAccountDto.getIban());

		return new SuccessDto(true);

	}

	@PostMapping("/withdraw-funds")
	@PreAuthorize("hasRole('ROLE_USER')")
	public BankAccountDto withdrawFunds(@RequestBody(required = true) @Valid FundsDto fundsDto) {

		Long userId = (Long) httpSession.getAttribute("user-id");

		return bankAccountsService.withdrawFunds(userId, fundsDto.getIban(), fundsDto.getFunds());

	}

	@GetMapping("/get-all")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<SecuredAccountDto> getAll() {

		return securedAccountsService.getAll();

	}

}
