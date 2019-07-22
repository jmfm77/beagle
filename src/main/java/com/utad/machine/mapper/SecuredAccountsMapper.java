package com.utad.machine.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.utad.machine.dto.ModifySecuredAccountDto;
import com.utad.machine.dto.SecuredAccountDto;
import com.utad.machine.entity.SecuredAccountsEntity;

@Service
public class SecuredAccountsMapper {

	public SecuredAccountDto toDto(SecuredAccountsEntity securedAccountEntity) {

		if (securedAccountEntity == null) {
			return null;
		}

		SecuredAccountDto securedAccountDto = new SecuredAccountDto();

		securedAccountDto.setSecuredAccountId(securedAccountEntity.getSecuredAccountId());
		securedAccountDto.setName(securedAccountEntity.getName());
		securedAccountDto.setDescription(securedAccountEntity.getDescription());
		securedAccountDto.setUsername(securedAccountEntity.getUsername());
		securedAccountDto.setPassword(securedAccountEntity.getPassword());
		securedAccountDto.setUri(securedAccountEntity.getUri());
		securedAccountDto.setToken(securedAccountEntity.getToken());

		return securedAccountDto;

	}

	public ModifySecuredAccountDto toModifyDto(SecuredAccountsEntity securedAccountEntity) {

		if (securedAccountEntity == null) {
			return null;
		}

		ModifySecuredAccountDto securedAccountDto = new ModifySecuredAccountDto();

		securedAccountDto.setSecuredAccountId(securedAccountEntity.getSecuredAccountId());
		securedAccountDto.setName(securedAccountEntity.getName());
		securedAccountDto.setDescription(securedAccountEntity.getDescription());
		securedAccountDto.setUsername(securedAccountEntity.getUsername());
		securedAccountDto.setPassword(securedAccountEntity.getPassword());
		securedAccountDto.setUri(securedAccountEntity.getUri());
		securedAccountDto.setToken(securedAccountEntity.getToken());

		return securedAccountDto;

	}

	public List<SecuredAccountDto> toDto(List<SecuredAccountsEntity> securedAccountEntities) {

		if (securedAccountEntities == null) {
			return null;
		}

		List<SecuredAccountDto> securedAccountDtos = new ArrayList<>(securedAccountEntities.size());

		for (SecuredAccountsEntity securedAccountEntity : securedAccountEntities) {
			SecuredAccountDto securedAccountDto = toDto(securedAccountEntity);
			securedAccountDtos.add(securedAccountDto);
		}

		return securedAccountDtos;

	}

}
