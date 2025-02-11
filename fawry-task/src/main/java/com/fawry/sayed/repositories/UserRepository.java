package com.fawry.sayed.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.fawry.sayed.entities.User;

public interface UserRepository extends CrudRepository<User, Long>{
	
	Optional<User> findByEmail(String email); 

}
