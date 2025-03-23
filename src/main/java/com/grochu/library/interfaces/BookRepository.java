package com.grochu.library.interfaces;

import com.grochu.library.Domain.Author;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.CrudRepository;

import com.grochu.library.Domain.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long>
{
    List<Book> findAllByOrderByTitleAsc(Pageable page);
    List<Book> findByTitleContainingOrderByTitleAsc(String title, Pageable pageable);
    Book findById(long id);
    List<Book> findByTitle(String title);
    List<Book> findByAuthor(Author author);
    long countByTitleContainingOrderByTitleAsc(String searchPhrase);

    @NativeQuery("select b.id,b.title,b.author_id,publish_year from books as b left join copies as c on b.id = c.book left join borrows as br on c.id = br.copy group by title order by count(copy) desc;")
    List<Book> findMostPopular();
}
