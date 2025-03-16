package com.grochu.library.api;

import com.grochu.library.DAL.Book;
import com.grochu.library.DAL.Borrow;
import com.grochu.library.DAL.Copy;
import com.grochu.library.interfaces.BookRepository;
import com.grochu.library.interfaces.BorrowRepository;
import com.grochu.library.interfaces.CopyRepository;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path="/api/copies", produces = "application/json")
@RequiredArgsConstructor
public class CopyController
{
    private final CopyRepository copyRepository;
    private final BookRepository bookRepository;
    private final BorrowRepository borrowRepository;

    @GetMapping(path="/{copy_id}")
    public Copy getCopy(@PathVariable("copy_id") long copyId)
    {
        return copyRepository.findById(copyId).orElse(null);
    }

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

    @GetMapping(path="/{book_id}/destroyed")
    public ResponseEntity<List<Copy>> getDestroyedCopiesOfBook(@PathVariable("book_id") long bookId)
    {
        Book book = bookRepository.findById(bookId);
        if(book == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(copyRepository.getDestroyedCopiesByBook(bookId), HttpStatus.OK);
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
        if(copy.getId() != null)
        {
            copyRepository.addCopy(copy.getId(), copy.getBook().getId());
            return copy;
        }
        else
            return copyRepository.save(copy);
    }

    @PostMapping("/return")
    public Borrow returnCopyNow(@RequestBody Copy copy)
    {
        Borrow borrow = borrowRepository.findBorrowByCopyAndUntilNull(copy);
        if(borrow != null)
        {
            borrow.setUntil(LocalDate.now());
            borrowRepository.save(borrow);
        }
        return borrow;
    }

    @PostMapping("/borrow")
    public Borrow borrowCopyNow(@RequestBody Borrow borrow)
    {
        if(borrow != null)
        {
            borrow.setSince(LocalDate.now());
            borrow.setUntil(null);
            borrowRepository.save(borrow);
        }
        return borrow;
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCopyOfBook(@PathParam("copy") long copyId)
    {
        Copy copy = copyRepository.findById(copyId).orElse(null);
        if(copy == null)
            return;
        copy.setDestroyed(true);
        copyRepository.save(copy);
    }
}
