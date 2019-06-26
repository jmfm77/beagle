package com.utad.machine.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.utad.machine.dto.SecuredAccountDto;
import com.utad.machine.entity.SecuredAccountEntity;

@Service
public class SecuredAccountsMapper {

	public SecuredAccountDto toDto(SecuredAccountEntity SecuredAccountEntity) {

		if (SecuredAccountEntity == null) {
			return null;
		}

		SecuredAccountDto SecuredAccountDto = new SecuredAccountDto();

		SecuredAccountDto.setName(SecuredAccountEntity.getName());
		SecuredAccountDto.setDescription(SecuredAccountEntity.getDescription());
		SecuredAccountDto.setPassword(SecuredAccountEntity.getPassword());
		SecuredAccountDto.setToken(SecuredAccountEntity.getToken());

		return SecuredAccountDto;

	}

	public List<SecuredAccountDto> toDto(List<SecuredAccountEntity> SecuredAccountEntities) {

		if (SecuredAccountEntities == null) {
			return null;
		}

		List<SecuredAccountDto> SecuredAccountDtos = new ArrayList<>(SecuredAccountEntities.size());

		for (SecuredAccountEntity SecuredAccountEntity : SecuredAccountEntities) {
			SecuredAccountDto SecuredAccountDto = toDto(SecuredAccountEntity);
			SecuredAccountDtos.add(SecuredAccountDto);
		}

		return SecuredAccountDtos;

	}

}
