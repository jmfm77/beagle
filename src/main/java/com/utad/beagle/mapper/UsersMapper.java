package com.utad.beagle.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.utad.beagle.dto.AdminUserDto;
import com.utad.beagle.dto.UserDto;
import com.utad.beagle.entity.UserEntity;

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
		userDto.setToken(userEntity.getToken());
		userDto.setCreatedOn(userEntity.getCreatedOn());
		userDto.setLastToken(userEntity.getLastToken());

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

	public AdminUserDto toAdminDto(UserEntity userEntity) {

		if (userEntity == null) {
			return null;
		}

		AdminUserDto userDto = new AdminUserDto();

		userDto.setUserId(userEntity.getUserId());
		userDto.setUsername(userEntity.getUsername());
		userDto.setPassword(userEntity.getPassword());
		userDto.setRole(userEntity.getRole());
		userDto.setToken(userEntity.getToken());
		userDto.setCreatedOn(userEntity.getCreatedOn());
		userDto.setLastToken(userEntity.getLastToken());

		return userDto;

	}

	public List<AdminUserDto> toAdminDto(List<UserEntity> userEntities) {

		if (userEntities == null) {
			return null;
		}

		List<AdminUserDto> userDtos = new ArrayList<>(userEntities.size());

		for (UserEntity userEntity : userEntities) {
			AdminUserDto userDto = toAdminDto(userEntity);
			userDtos.add(userDto);
		}

		return userDtos;

	}

}
