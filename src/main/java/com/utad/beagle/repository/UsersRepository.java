package com.utad.beagle.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utad.beagle.entity.UserEntity;

public interface UsersRepository extends JpaRepository<UserEntity, Long> {

	UserEntity findByUsername(String username);

	UserEntity findByUserId(Long idUser);

	UserEntity findByToken(String token);

}
