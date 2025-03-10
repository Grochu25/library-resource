package com.grochu.library.interfaces;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.grochu.library.DAL.Author;

import java.util.List;

public interface AuthorRepository extends CrudRepository<Author, Long>
{
    Author getAuthorsById(long id);
    List<Author> findAll(Pageable pageable);
}
