package com.grochu.library.interfaces;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.grochu.library.DAL.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long>
{
	List<User> findAllByOrderBySurnameAscNameAsc(Pageable page);
	List<User> findBySurnameContainingIgnoreCaseOrderBySurnameAscNameAsc(String surname, Pageable page);
	List<User> findByNameContainingIgnoreCaseOrderBySurnameAscNameAsc(String name, Pageable page);
	User findUserByIdAndEmail(Long id, String email);
	User findByEmail(String email);
	User getUserById(long id);
	long countByNameContainingIgnoreCaseOrderBySurnameAscNameAsc(String searchPhrase);
	long countBySurnameContainingIgnoreCaseOrderBySurnameAscNameAsc(String searchPhrase);
}
