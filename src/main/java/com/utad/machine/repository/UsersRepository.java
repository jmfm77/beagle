package com.utad.machine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utad.machine.entity.UserEntity;

public interface UsersRepository extends JpaRepository<UserEntity, Long> {

	UserEntity findByUsername(String username);

	UserEntity findByIdUser(Long idUser);

}
