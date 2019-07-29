package com.utad.machine.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.utad.machine.dto.ModifySecuredAccountDto;
import com.utad.machine.dto.SecuredAccountDto;
import com.utad.machine.entity.SecuredAccountsEntity;
import com.utad.machine.exception.BusinessLogicException;
import com.utad.machine.util.CryptoUtils;

@Service
public class SecuredAccountsMapper {

	@Autowired
	private Environment env;

	public String descifrar(String cadena) {
		char[] cryptoPassword = env.getProperty("crypto.password").toCharArray();
		CryptoUtils cryptoUtils = new CryptoUtils();
		String cadenaDescifrada = "";

		try {
			cadenaDescifrada = cryptoUtils.decrypt(cadena, "AES", cryptoPassword);
		} catch (Exception e) {
			throw new BusinessLogicException("account-error");
		}

		return cadenaDescifrada;
	}

	public SecuredAccountDto toDto(SecuredAccountsEntity securedAccountEntity) {

		if (securedAccountEntity == null) {
			return null;
		}

		SecuredAccountDto securedAccountDto = new SecuredAccountDto();

		String username = descifrar(securedAccountEntity.getUsername());
		String password = descifrar(securedAccountEntity.getPassword());

		securedAccountDto.setSecuredAccountId(securedAccountEntity.getSecuredAccountId());
		securedAccountDto.setName(securedAccountEntity.getName());
		securedAccountDto.setDescription(securedAccountEntity.getDescription());
		securedAccountDto.setUsername(username);
		securedAccountDto.setPassword(password);
		securedAccountDto.setUri(securedAccountEntity.getUri());

		return securedAccountDto;

	}

	public ModifySecuredAccountDto toModifyDto(SecuredAccountsEntity securedAccountEntity) {

		if (securedAccountEntity == null) {
			return null;
		}

		ModifySecuredAccountDto securedAccountDto = new ModifySecuredAccountDto();

		String username = descifrar(securedAccountEntity.getUsername());
		String password = descifrar(securedAccountEntity.getPassword());

		securedAccountDto.setSecuredAccountId(securedAccountEntity.getSecuredAccountId());
		securedAccountDto.setName(securedAccountEntity.getName());
		securedAccountDto.setDescription(securedAccountEntity.getDescription());
		securedAccountDto.setUsername(username);
		securedAccountDto.setPassword(password);
		securedAccountDto.setUri(securedAccountEntity.getUri());

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
