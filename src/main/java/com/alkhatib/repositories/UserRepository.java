package com.alkhatib.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alkhatib.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	
	Optional<User> findByUsername(String username);
}