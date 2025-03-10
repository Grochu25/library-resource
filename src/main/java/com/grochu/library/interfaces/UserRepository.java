package com.grochu.library.interfaces;

import org.springframework.data.repository.CrudRepository;

import com.grochu.library.DAL.User;

public interface UserRepository extends CrudRepository<User, Long>
{
	User findUserByIdAndEmail(Long id, String email);
	User findByEmail(String email);
}
