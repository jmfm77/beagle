package com.utad.machine.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.utad.machine.dto.UserDto;
import com.utad.machine.entity.UserEntity;

@Service
public class UsersMapper {

	public UserDto toDto(UserEntity userEntity) {

		if (userEntity == null) {
			return null;
		}

		UserDto userDto = new UserDto();

		userDto.setUserId(userEntity.getUserId());
		userDto.setUsername(userEntity.getUsername());
		userDto.setPassword(userEntity.getPassword());
		userDto.setRole(userEntity.getRole());

		return userDto;

	}

	public List<UserDto> toDto(List<UserEntity> userEntities) {

		if (userEntities == null) {
			return null;
		}

		List<UserDto> userDtos = new ArrayList<>(userEntities.size());

		for (UserEntity userEntity : userEntities) {
			UserDto userDto = toDto(userEntity);
			userDtos.add(userDto);
		}

		return userDtos;

	}

}
