package com.grochu.library.interfaces;

import com.grochu.library.Domain.Borrow;
import com.grochu.library.Domain.Copy;
import com.grochu.library.Domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BorrowRepository extends CrudRepository<Borrow, Long>
{
    List<Borrow> findBorrowsByUntilIsNullAndUser(User user);
    List<Borrow> findBorrowsByUntilIsNotNullAndUser(User user);
    List<Borrow> findBorrowsByUser(User user);
    Borrow findBorrowByCopyAndUntilNull(Copy copy);
}
