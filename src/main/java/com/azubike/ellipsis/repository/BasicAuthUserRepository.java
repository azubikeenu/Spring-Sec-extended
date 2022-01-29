package com.azubike.ellipsis.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.azubike.ellipsis.entity.BasicAuthUser;

@Repository
public interface BasicAuthUserRepository extends CrudRepository<BasicAuthUser, Integer> {
	Optional<BasicAuthUser> findByUsername(String encryptedUsername);
}
