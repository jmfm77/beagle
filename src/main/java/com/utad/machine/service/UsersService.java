package com.utad.machine.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.utad.machine.dto.AdminUserDto;
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

	@Autowired
	private PasswordEncoder passwordEncoder;

	public UserDto getByUsername(String username) {

		UserEntity userEntity = usersRepository.findByUsername(username);
		UserDto userDto = usersMapper.toDto(userEntity);
		return userDto;

	}

	public UserDto getByUserId(Long iduser) {

		UserEntity userEntity = usersRepository.findByUserId(iduser);
		UserDto userDto = usersMapper.toDto(userEntity);
		return userDto;

	}

	public List<AdminUserDto> getAll() {

		List<UserEntity> userEntities = usersRepository.findAll();
		List<AdminUserDto> userDtos = usersMapper.toAdminDto(userEntities);

		return userDtos;

	}

	public UserDto create(String username, String password1, String password2, String role) {

		if (!password1.equals(password2)) {
			throw new BusinessLogicException("incorrect-password");
		}

		UserDto userDtoAux = getByUsername(username);

		if (userDtoAux != null) {
			throw new BusinessLogicException("user-already-exists");
		}

		UserEntity userEntity = new UserEntity();
		userEntity.setUsername(username);
		userEntity.setPassword(passwordEncoder.encode(password1));
		userEntity.setRole(role);
		userEntity = usersRepository.save(userEntity);

		UserDto userDto = usersMapper.toDto(userEntity);

		return userDto;

	}

	public void modify(Long idUser, String oldPassword, String newPassword1, String newPassword2) {

		UserEntity userEntity = new UserEntity();

		UserDto userDto = getByUserId(idUser);

		if (!passwordEncoder.matches(oldPassword, userDto.getPassword())) {
			throw new BusinessLogicException("incorrect-password");
		}
		if (!newPassword1.equals(newPassword2)) {
			throw new BusinessLogicException("incorrect-password");
		}

		userEntity.setUserId(idUser);
		userEntity.setUsername(userDto.getUsername());
		userEntity.setPassword(passwordEncoder.encode(newPassword1));
		userEntity.setRole(userDto.getRole());
		userEntity = usersRepository.save(userEntity);
	}

	public void adminModify(Long idAdmin, Long idUser, String adminPassword, String newPassword1, String newPassword2) {

		UserEntity userEntity = new UserEntity();

		UserDto adminDto = getByUserId(idAdmin);
		UserDto userDto = getByUserId(idUser);

		if (!passwordEncoder.matches(adminPassword, adminDto.getPassword())) {
			throw new BusinessLogicException("incorrect-password");
		}
		if (!newPassword1.equals(newPassword2)) {
			throw new BusinessLogicException("incorrect-password");
		}

		userEntity.setUserId(idUser);
		userEntity.setUsername(userDto.getUsername());
		userEntity.setPassword(passwordEncoder.encode(newPassword1));
		userEntity.setRole(userDto.getRole());
		userEntity = usersRepository.save(userEntity);
	}

	public void deleteByUserId(Long userId) {

		UserEntity userEntity = usersRepository.findByUserId(userId);

		if (userEntity == null) {
			throw new BusinessLogicException("user-does-not-exist");
		}

		usersRepository.delete(userEntity);

	}

}
