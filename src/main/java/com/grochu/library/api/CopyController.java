package com.grochu.library.api;

import com.grochu.library.DAL.Book;
import com.grochu.library.DAL.Copy;
import com.grochu.library.interfaces.BookRepository;
import com.grochu.library.interfaces.CopyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/copies", produces = "application/json")
@RequiredArgsConstructor
public class CopyController
{
    private final CopyRepository copyRepository;
    private final BookRepository bookRepository;

    @GetMapping(path="/{book_id}/all")
    public List<Copy> getAllCopiesOfBook(@PathVariable("book_id") long bookId)
    {
        Book book = bookRepository.findById(bookId);
        return copyRepository.getCopiesByBook(book);
    }

    @GetMapping(path="/{book_id}/available")
    public ResponseEntity<List<Copy>> getAvailableCopiesOfBook(@PathVariable("book_id") long bookId)
    {
        Book book = bookRepository.findById(bookId);
        if(book == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(copyRepository.getAvailableCopiesByBook(bookId), HttpStatus.OK);
    }

    @GetMapping(path="/{book_id}/available/number")
    public ResponseEntity<Integer> getNumberOfAvailableCopiesOfBook(@PathVariable("book_id") long bookId)
    {
        Book book = bookRepository.findById(bookId);
        if(book == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(copyRepository.getAvailableCopiesByBook(bookId).size(), HttpStatus.OK);
    }

    @PostMapping
    public Copy addNewCopyOfBook(@RequestBody Copy copy)
    {
        return copyRepository.save(copy);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCopyOfBook(@RequestBody Copy copy)
    {
        copyRepository.delete(copy);
    }
}
