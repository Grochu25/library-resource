package com.grochu.library.interfaces;

import com.grochu.library.DAL.Book;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.CrudRepository;

import com.grochu.library.DAL.Copy;

import java.util.List;

public interface CopyRepository extends CrudRepository<Copy, Long>
{
    List<Copy> getCopiesByBook(Book book);

    @NativeQuery("select c.id, c.book from books b join copies c on b.id = c.book where b.id = :bookId and c.id not in (select cc.id from copies cc join borrows br on cc.id = br.copy where until is NULL and cc.book = b.id);")
    List<Copy> getAvailableCopiesByBook(long bookId);
}
