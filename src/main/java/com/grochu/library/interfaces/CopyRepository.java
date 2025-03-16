package com.grochu.library.interfaces;

import com.grochu.library.DAL.Book;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.CrudRepository;

import com.grochu.library.DAL.Copy;

import java.util.List;

public interface CopyRepository extends CrudRepository<Copy, Long>
{
    List<Copy> getCopiesByBook(Book book);

    @NativeQuery("select c.id, c.book, c.destroyed from books b join copies c on b.id = c.book where b.id = :bookId and c.id not in (select cc.id from copies cc join borrows br on cc.id = br.copy where until is NULL and cc.book = b.id) and c.destroyed = false;")
    List<Copy> getAvailableCopiesByBook(long bookId);

    @NativeQuery("select c.id, c.book, c.destroyed from books b join copies c on b.id = c.book where b.id = :bookId and c.destroyed = true;")
    List<Copy> getDestroyedCopiesByBook(long bookId);

    @Transactional
    @Modifying
    @NativeQuery("insert into copies values (:copyId, :bookId, false);")
    void addCopy(Long copyId, Long bookId);
}
