package com.utad.machine.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utad.machine.dto.UserDto;
import com.utad.machine.entity.UserEntity;
import com.utad.machine.exception.BusinessLogicException;
import com.utad.machine.mapper.UsersMapper;
import com.utad.machine.repository.UsersRepository;

@Service
@Transactional
public class UsersService {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private UsersMapper usersMapper;

	public UserDto getByUsername(String username) {

		UserEntity userEntity = usersRepository.findByUsername(username);
		UserDto userDto = usersMapper.toDto(userEntity);
		return userDto;

	}

	public List<UserDto> getAll() {

		List<UserEntity> userEntities = usersRepository.findAll();
		List<UserDto> userDtos = usersMapper.toDto(userEntities);

		return userDtos;

	}

	public UserDto create(String username, String password, String role) {

		UserEntity userEntity = new UserEntity();
		userEntity.setUsername(username);
		userEntity.setPassword(password);
		userEntity.setRole(role);
		userEntity = usersRepository.save(userEntity);

		UserDto userDto = usersMapper.toDto(userEntity);

		return userDto;

	}

	public void deleteByUsername(String username) {

		UserEntity userEntity = usersRepository.findByUsername(username);

		if (userEntity == null) {
			throw new BusinessLogicException("user-does-not-exist");
		}

		usersRepository.delete(userEntity);

	}

}
