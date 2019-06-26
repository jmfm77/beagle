package com.utad.machine.service;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.utad.curso.desarrollo.seguro.utils.IbanService;
import com.utad.machine.dto.SecuredAccountDto;
import com.utad.machine.dto.UserDto;
import com.utad.machine.entity.SecuredAccountEntity;
import com.utad.machine.entity.UserEntity;
import com.utad.machine.exception.BusinessLogicException;
import com.utad.machine.mapper.SecuredAccountsMapper;
import com.utad.machine.mapper.UsersMapper;
import com.utad.machine.repository.SecuredAccountsRepository;
import com.utad.machine.repository.UsersRepository;

@Service
@Transactional
public class SecuredAccountsService {

	@Autowired
	private SecuredAccountsRepository SecuredAccountsRepository;

	@Autowired
	private SecuredAccountsMapper SecuredAccountsMapper;

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private UsersMapper usersMapper;

	// @Autowired
	// private IbanService ibanService;

	public List<SecuredAccountDto> getAll() {

		List<SecuredAccountEntity> SecuredAccountEntities = SecuredAccountsRepository.findAll();
		List<SecuredAccountDto> SecuredAccountDtos = SecuredAccountsMapper.toDto(SecuredAccountEntities);

		return SecuredAccountDtos;

	}

	public List<SecuredAccountDto> getByOwner(Long ownerUserId) {

		List<SecuredAccountEntity> SecuredAccountEntities = SecuredAccountsRepository.findByOwnerUserId(ownerUserId);
		List<SecuredAccountDto> SecuredAccountDtos = SecuredAccountsMapper.toDto(SecuredAccountEntities);

		return SecuredAccountDtos;

	}

	public SecuredAccountDto create(Long ownerUserId) {

		SecuredAccountEntity SecuredAccountEntity = new SecuredAccountEntity();
		SecuredAccountEntity.setIban(ibanService.randomIban());
		SecuredAccountEntity.setBalance(0.0);
		SecuredAccountEntity.setOwner(usersRepository.findOne(ownerUserId));
		SecuredAccountEntity = SecuredAccountsRepository.save(SecuredAccountEntity);

		SecuredAccountDto SecuredAccountDto = SecuredAccountsMapper.toDto(SecuredAccountEntity);

		return SecuredAccountDto;

	}

	public void deleteByIban(Long ownerUserId, String iban) {

		SecuredAccountEntity SecuredAccountEntity = findOwnedSecuredAccountByIban(ownerUserId, iban);

		if (SecuredAccountEntity.getBalance() > 0) {
			throw new BusinessLogicException("account-has-funds");
		}

		SecuredAccountsRepository.delete(SecuredAccountEntity);

	}

	public List<UserDto> getAllowedUsers(Long ownerUserId, String iban) {

		SecuredAccountEntity SecuredAccountEntity = findOwnedSecuredAccountByIban(ownerUserId, iban);

		List<UserEntity> allowedUserEntities = SecuredAccountEntity.getAllowedUsers();
		List<UserDto> allowedUserDtos = usersMapper.toDto(allowedUserEntities);

		return allowedUserDtos;

	}

	public void allowUserToIban(Long ownerUserId, String username, String iban) {

		SecuredAccountEntity SecuredAccountEntity = findOwnedSecuredAccountByIban(ownerUserId, iban);

		UserEntity user = usersRepository.findByUsername(username);

		if (user == null) {
			throw new BusinessLogicException("user-does-not-exist");
		}

		if (user.getUserId().equals(ownerUserId)) {
			throw new BusinessLogicException("cannot-allow-owner-to-account");
		}

		if (!user.getRole().equals("ROLE_USER")) {
			throw new BusinessLogicException("cannot-allow-admin-to-account");
		}

		for (UserEntity allowedUser : SecuredAccountEntity.getAllowedUsers()) {
			if (allowedUser.getUserId().equals(user.getUserId())) {
				throw new BusinessLogicException("user-already-allowed");
			}
		}

		SecuredAccountEntity.getAllowedUsers().add(user);

	}

	public void disallowUserFromIban(Long ownerUserId, String username, String iban) {

		SecuredAccountEntity SecuredAccountEntity = findOwnedSecuredAccountByIban(ownerUserId, iban);

		UserEntity user = usersRepository.findByUsername(username);

		if (user == null) {
			throw new BusinessLogicException("user-does-not-exist");
		}

		UserEntity foundAllowedUser = null;

		for (UserEntity allowedUser : SecuredAccountEntity.getAllowedUsers()) {
			if (allowedUser.getUserId().equals(user.getUserId())) {
				foundAllowedUser = allowedUser;
			}
		}

		if (foundAllowedUser == null) {
			throw new BusinessLogicException("user-was-not-allowed");
		}

		SecuredAccountEntity.getAllowedUsers().remove(foundAllowedUser);

	}

	public SecuredAccountDto depositFunds(String iban, Double funds) {

		SecuredAccountEntity SecuredAccountEntity = SecuredAccountsRepository.findByIban(iban);
		SecuredAccountEntity.setBalance(SecuredAccountEntity.getBalance() + funds);

		SecuredAccountDto SecuredAccountDto = SecuredAccountsMapper.toDto(SecuredAccountEntity);

		return SecuredAccountDto;

	}

	public SecuredAccountDto withdrawFunds(Long ownerOrAllowedUserId, String iban, Double funds) {

		SecuredAccountEntity SecuredAccountEntity = findOwnedOrAllowedSecuredAccountByIban(ownerOrAllowedUserId, iban);

		Double newBalance = SecuredAccountEntity.getBalance() - funds;

		if (newBalance < 0) {
			throw new BusinessLogicException("insufficient-funds");
		}

		SecuredAccountEntity.setBalance(newBalance);

		SecuredAccountDto SecuredAccountDto = SecuredAccountsMapper.toDto(SecuredAccountEntity);

		return SecuredAccountDto;

	}

	public List<SecuredAccountDto> transferFunds(Long ownerOrAllowedUserId, String sourceIban, String destinationIban,
			Double funds) {

		SecuredAccountEntity sourceSecuredAccountEntity = findOwnedOrAllowedSecuredAccountByIban(ownerOrAllowedUserId,
				sourceIban);
		SecuredAccountEntity destinationAccountEntity = SecuredAccountsRepository.findByIban(destinationIban);

		Double newSourceBalance = sourceSecuredAccountEntity.getBalance() - funds;
		if (newSourceBalance < 0) {
			throw new BusinessLogicException("insufficient-funds");
		}
		sourceSecuredAccountEntity.setBalance(newSourceBalance);

		Double newDestinationBalance = destinationAccountEntity.getBalance() + funds;
		destinationAccountEntity.setBalance(newDestinationBalance);

		SecuredAccountDto sourceSecuredAccountDto = SecuredAccountsMapper.toDto(sourceSecuredAccountEntity);
		SecuredAccountDto destinationAccountDto = SecuredAccountsMapper.toDto(destinationAccountEntity);
		List<SecuredAccountDto> ret = Arrays.asList(sourceSecuredAccountDto, destinationAccountDto);

		return ret;

	}

	private SecuredAccountEntity findOwnedSecuredAccountByIban(Long ownerUserId, String iban) {

		SecuredAccountEntity SecuredAccountEntity = SecuredAccountsRepository.findByIban(iban);

		if (SecuredAccountEntity == null) {
			throw new BusinessLogicException("account-does-not-exist");
		}

		if (!SecuredAccountEntity.getOwner().getUserId().equals(ownerUserId)) {
			throw new BusinessLogicException("account-does-not-exist");
		}

		return SecuredAccountEntity;

	}

	private SecuredAccountEntity findOwnedOrAllowedSecuredAccountByIban(Long ownerOrAllowedUserId, String iban) {

		SecuredAccountEntity SecuredAccountEntity = SecuredAccountsRepository.findByIban(iban);

		if (SecuredAccountEntity == null) {
			throw new BusinessLogicException("account-does-not-exist");
		}

		if (!SecuredAccountEntity.getOwner().getUserId().equals(ownerOrAllowedUserId)) {

			UserEntity foundAllowedUser = null;

			for (UserEntity allowedUser : SecuredAccountEntity.getAllowedUsers()) {
				if (allowedUser.getUserId().equals(ownerOrAllowedUserId)) {
					foundAllowedUser = allowedUser;
				}
			}

			if (foundAllowedUser == null) {
				throw new BusinessLogicException("account-does-not-exist");
			}

		}

		return SecuredAccountEntity;

	}

}
