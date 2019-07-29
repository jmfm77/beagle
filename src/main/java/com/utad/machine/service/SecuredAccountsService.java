package com.utad.machine.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.utad.machine.dto.ModifySecuredAccountDto;
import com.utad.machine.dto.SecuredAccountDto;
import com.utad.machine.entity.SecuredAccountsEntity;
import com.utad.machine.exception.BusinessLogicException;
import com.utad.machine.mapper.SecuredAccountsMapper;
import com.utad.machine.repository.SecuredAccountsRepository;
import com.utad.machine.repository.UsersRepository;
import com.utad.machine.util.CryptoUtils;

@Service
@Transactional
public class SecuredAccountsService {

	@Autowired
	private Environment env;

	@Autowired
	private SecuredAccountsRepository securedAccountsRepository;

	@Autowired
	private SecuredAccountsMapper securedAccountsMapper;

	@Autowired
	private UsersRepository usersRepository;

	public List<SecuredAccountDto> getAll() {

		List<SecuredAccountsEntity> securedAccountEntities = securedAccountsRepository.findByOrderByName();
		List<SecuredAccountDto> securedAccountDtos = securedAccountsMapper.toDto(securedAccountEntities);

		return securedAccountDtos;

	}

	public List<SecuredAccountDto> getByUser(Long userId) {

		List<SecuredAccountsEntity> securedAccountEntities = securedAccountsRepository
				.findByUserUserIdOrderByName(userId);
		List<SecuredAccountDto> securedAccountDtos = securedAccountsMapper.toDto(securedAccountEntities);

		return securedAccountDtos;

	}

	public SecuredAccountDto getByUserSecuredAccountId(Long userId, Long securedAccountId) {

		SecuredAccountsEntity securedAccountsEntity = securedAccountsRepository
				.findBySecuredAccountId(securedAccountId);

		if (securedAccountsEntity != null && securedAccountsEntity.getUser() != null
				&& !userId.equals(securedAccountsEntity.getUser().getUserId())) {
			throw new BusinessLogicException("account-does-not-exist");
		}
		SecuredAccountDto securedAccountDto = securedAccountsMapper.toDto(securedAccountsEntity);

		return securedAccountDto;

	}

	public String cifrar(String cadena) {
		char[] cryptoPassword = env.getProperty("crypto.password").toCharArray();
		CryptoUtils cryptoUtils = new CryptoUtils();
		String cadenaCifrada = "";

		try {
			cadenaCifrada = cryptoUtils.encrypt(cadena, "AES", cryptoPassword);
		} catch (Exception e) {
			throw new BusinessLogicException("account-error");
		}

		return cadenaCifrada;
	}

	public SecuredAccountDto create(Long userId, SecuredAccountDto initialSecuredAccountDto) {

		String username = cifrar(initialSecuredAccountDto.getUsername());
		String password = cifrar(initialSecuredAccountDto.getPassword());

		SecuredAccountsEntity securedAccountEntity = new SecuredAccountsEntity();
		securedAccountEntity.setName(initialSecuredAccountDto.getName());
		securedAccountEntity.setDescription(initialSecuredAccountDto.getDescription());
		securedAccountEntity.setUsername(username);
		securedAccountEntity.setPassword(password);
		securedAccountEntity.setUri(initialSecuredAccountDto.getUri());
		securedAccountEntity.setUser(usersRepository.findOne(userId));

		SecuredAccountsEntity securedAccountEntityAux = findOwnedSecuredAccountByName(userId,
				initialSecuredAccountDto.getName());

		if (securedAccountEntityAux != null) {
			throw new BusinessLogicException("account-already-exist");
		}

		securedAccountEntity = securedAccountsRepository.save(securedAccountEntity);

		SecuredAccountDto securedAccountDto = securedAccountsMapper.toDto(securedAccountEntity);

		return securedAccountDto;

	}

	public ModifySecuredAccountDto modify(Long userId, ModifySecuredAccountDto initialSecuredAccountDto) {

		SecuredAccountsEntity securedAccountEntityAux = findOwnedSecuredAccountByName(userId,
				initialSecuredAccountDto.getName());

		if (securedAccountEntityAux == null) {
			throw new BusinessLogicException("account-does-not-exist");
		}

		String username = cifrar(initialSecuredAccountDto.getUsername());
		String password = cifrar(initialSecuredAccountDto.getPassword());

		SecuredAccountsEntity securedAccountEntity = new SecuredAccountsEntity();
		securedAccountEntity.setSecuredAccountId(securedAccountEntityAux.getSecuredAccountId());
		securedAccountEntity.setName(initialSecuredAccountDto.getName());
		securedAccountEntity.setDescription(initialSecuredAccountDto.getDescription());
		securedAccountEntity.setUsername(username);
		securedAccountEntity.setPassword(password);
		securedAccountEntity.setUri(initialSecuredAccountDto.getUri());
		securedAccountEntity.setUser(usersRepository.findOne(userId));
		securedAccountEntity = securedAccountsRepository.save(securedAccountEntity);

		ModifySecuredAccountDto securedAccountDto = securedAccountsMapper.toModifyDto(securedAccountEntity);

		return securedAccountDto;

	}

	public void deleteByName(Long ownerUserId, String name) {

		SecuredAccountsEntity securedAccountsEntity = findOwnedSecuredAccountByName(ownerUserId, name);

		if (securedAccountsEntity == null) {
			throw new BusinessLogicException("account-does-not-exist");
		}

		securedAccountsRepository.delete(securedAccountsEntity);

	}

	private SecuredAccountsEntity findOwnedSecuredAccountByName(Long ownerUserId, String name) {

		List<SecuredAccountsEntity> securedAccountsEntity = securedAccountsRepository
				.findByUserUserIdAndName(ownerUserId, name);

		SecuredAccountsEntity securedAccountEntity = null;
		if (securedAccountsEntity != null && securedAccountsEntity.size() > 0) {
			securedAccountEntity = securedAccountsEntity.get(0);
		}

		return securedAccountEntity;

	}

}
