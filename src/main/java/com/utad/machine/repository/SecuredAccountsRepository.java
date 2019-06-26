package com.utad.machine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utad.machine.entity.SecuredAccountEntity;

public interface SecuredAccountsRepository extends JpaRepository<SecuredAccountEntity, Long> {

	SecuredAccountEntity findByIban(String iban);

	List<SecuredAccountEntity> findByOwnerUserId(Long userId);

}
