package com.utad.machine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utad.machine.entity.SecuredAccountsEntity;

public interface SecuredAccountsRepository extends JpaRepository<SecuredAccountsEntity, Long> {

	List<SecuredAccountsEntity> findByUserUserIdOrderByName(Long userId);

	List<SecuredAccountsEntity> findByOrderByName();

	List<SecuredAccountsEntity> findByUserUserIdAndName(Long userId, String name);

	SecuredAccountsEntity findBySecuredAccountId(Long securedAccountId);

}
