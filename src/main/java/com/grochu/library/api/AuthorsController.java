package com.grochu.library.api;

import com.grochu.library.Domain.Author;
import com.grochu.library.Domain.Book;
import com.grochu.library.PresenceProps;
import com.grochu.library.interfaces.AuthorRepository;
import com.grochu.library.interfaces.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path="/api/authors", produces="application/json")
@RequiredArgsConstructor
public class AuthorsController
{
    private final AuthorRepository authorRepo;
    private final BookRepository bookRepo;
    private final PresenceProps presenceProps;

    @GetMapping(params="!page")
    public List<Author> getAllAuthorsPagable(@RequestParam(value = "search", required = false) String searchPhrase)
    {
        PageRequest pageRequest = PageRequest.of(0, presenceProps.getElementsOnPage());
        if(searchPhrase == null)
            return authorRepo.findAllByOrderByNameAsc(pageRequest);
        else
            return authorRepo.findByNameContainingOrderByNameAsc(searchPhrase, pageRequest);
    }

    @GetMapping(params="page")
    public List<Author> getAllAuthorsPagable(@RequestParam("page") int page,
                                             @RequestParam(value = "search", required = false) String searchPhrase)
    {
        PageRequest pageRequest = PageRequest.of(page-1, presenceProps.getElementsOnPage());
        if(searchPhrase == null)
            return authorRepo.findAllByOrderByNameAsc(pageRequest);
        else
            return authorRepo.findByNameContainingOrderByNameAsc(searchPhrase, pageRequest);
    }

    @GetMapping("/all")
    public Iterable<Author> getAllAuthors()
    {
        log.info(SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
        log.info(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return authorRepo.findAll();
    }

    @GetMapping("/number")
    public long getAuthorNumber(@RequestParam(value = "search", required = false) String searchPhrase)
    {
        if(searchPhrase == null)
            return authorRepo.count();
        else
            return authorRepo.countByNameContainingOrderByNameAsc(searchPhrase);
    }

    @GetMapping("/{author_id}")
    public Author getAuthorDetails(@PathVariable("author_id") int author_id)
    {
        return authorRepo.getAuthorsById(author_id);
    }

    @GetMapping("/{author_id}/books")
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
