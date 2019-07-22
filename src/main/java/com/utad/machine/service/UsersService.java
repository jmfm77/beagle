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

	public UserDto getByIdUser(Long iduser) {

		UserEntity userEntity = usersRepository.findByIdUser(iduser);
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

	public void modify(Long idUser, String oldPassword, String newPassword1, String newPassword2) {

		UserEntity userEntity = new UserEntity();

		UserDto userDto = getByIdUser(idUser);

		if (!userDto.getPassword().equals(oldPassword)) {
			throw new BusinessLogicException("incorrect-password");
		}
		if (!newPassword1.equals(newPassword2)) {
			throw new BusinessLogicException("incorrect-password");
		}

		userEntity.setUsername(userDto.getUsername());
		userEntity.setPassword(newPassword1);
		userEntity.setRole(userDto.getRole());
		userEntity = usersRepository.save(userEntity);
	}

	public void deleteByUsername(String username) {

		UserEntity userEntity = usersRepository.findByUsername(username);

		if (userEntity == null) {
			throw new BusinessLogicException("user-does-not-exist");
		}

		usersRepository.delete(userEntity);

	}

}
