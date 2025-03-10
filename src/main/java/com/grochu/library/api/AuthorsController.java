package com.grochu.library.api;

import com.grochu.library.DAL.Author;
import com.grochu.library.DAL.Book;
import com.grochu.library.PresenceProps;
import com.grochu.library.interfaces.AuthorRepository;
import com.grochu.library.interfaces.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/authors", produces="application/json")
@RequiredArgsConstructor
public class AuthorsController
{
    private final AuthorRepository authorRepo;
    private final BookRepository bookRepo;
    private final PresenceProps presenceProps;

    @GetMapping(params="!page")
    public List<Author> getAllAuthors()
    {
        PageRequest pageRequest = PageRequest.of(0, presenceProps.getElementsOnPage());
        return authorRepo.findAll(pageRequest);
    }

    @GetMapping(params="page")
    public List<Author> getAllAuthors(@RequestParam("page") int page)
    {
        PageRequest pageRequest = PageRequest.of(page, presenceProps.getElementsOnPage());
        return authorRepo.findAll(pageRequest);
    }

    @GetMapping("/{author_id}")
    public List<Book> getBooksOfAuthor(@PathVariable("author_id") int author_id)
    {
        Author auth = authorRepo.getAuthorsById(author_id);
        return bookRepo.findByAuthor(auth);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Author addBook(@RequestBody Author author)
    {
        return authorRepo.save(author);
    }

    @DeleteMapping(path="/{id}/delete",consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@RequestBody Author author)
    {
        List<Book> booksOfAuthor = bookRepo.findByAuthor(author);
        bookRepo.deleteAll(booksOfAuthor);
        authorRepo.delete(author);
    }
}
