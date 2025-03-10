package com.grochu.library.interfaces;

import com.grochu.library.DAL.Author;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.CrudRepository;

import com.grochu.library.DAL.Book;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long>
{
    List<Book> findAll(Pageable page);
    Book findById(long id);
    List<Book> findByTitle(String title);
    List<Book> findByAuthor(Author author);

    @NativeQuery("select b.id,b.title,b.author_id,publish_year from books as b left join copies as c on b.id = c.book left join borrows as br on c.id = br.copy group by title order by count(copy) desc;")
    List<Book> findMostPopular();

    @NativeQuery("select count(*) from books")
    int countAll();
}
