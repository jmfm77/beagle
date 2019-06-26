package com.utad.machine.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.utad.curso.desarrollo.seguro.utils.IbanService;
import com.utad.machine.dto.SecuredAccountDto;
import com.utad.machine.entity.SecuredAccountEntity;
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

	public List<SecuredAccountDto> getByOwner(Long userId) {

		List<SecuredAccountEntity> SecuredAccountEntities = SecuredAccountsRepository.findByOwnerUserId(userId);
		List<SecuredAccountDto> SecuredAccountDtos = SecuredAccountsMapper.toDto(SecuredAccountEntities);

		return SecuredAccountDtos;

	}

	public SecuredAccountDto create(Long userId, String name) {

		SecuredAccountEntity SecuredAccountEntity = new SecuredAccountEntity();
		SecuredAccountEntity.setName(name);
		SecuredAccountEntity.setIdUser(usersRepository.findOne(userId));
		SecuredAccountEntity = SecuredAccountsRepository.save(SecuredAccountEntity);

		SecuredAccountDto SecuredAccountDto = SecuredAccountsMapper.toDto(SecuredAccountEntity);

		return SecuredAccountDto;

	}

}
