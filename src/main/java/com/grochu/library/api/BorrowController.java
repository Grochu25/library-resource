package com.grochu.library.api;

import com.grochu.library.DAL.Book;
import com.grochu.library.DAL.Borrow;
import com.grochu.library.DAL.Copy;
import com.grochu.library.DAL.User;
import com.grochu.library.interfaces.BookRepository;
import com.grochu.library.interfaces.BorrowRepository;
import com.grochu.library.interfaces.CopyRepository;
import com.grochu.library.interfaces.UserRepository;
import jakarta.persistence.OneToOne;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path="api/borrows")
@RequiredArgsConstructor
public class BorrowController
{
    private final BorrowRepository borrowRepository;
    private final UserRepository userRepository;
    private final CopyRepository copyRepository;

    @GetMapping(path="/user/{user_id}/current")
    public ResponseEntity<List<Borrow>> getBorrowedByUser(@PathVariable("user_id") long user_id)
    {
        User user = userRepository.findById(user_id).orElse(null);
        if(user == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(borrowRepository.findBorrowsByUntilIsNullAndUser(user), HttpStatus.OK);
    }

    @GetMapping(path="/user/{user_id}/all")
    public ResponseEntity<List<Borrow>> getAllOfUserBorrows(@PathVariable("user_id") long user_id)
    {
        User user = userRepository.findById(user_id).orElse(null);
        if(user == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(borrowRepository.findBorrowsByUser(user), HttpStatus.OK);
    }

    @GetMapping(path="/user/{user_id}/history")
    public ResponseEntity<List<Borrow>> getHistoryOfUserBorrows(@PathVariable("user_id") long user_id)
    {
        User user = userRepository.findById(user_id).orElse(null);
        if(user == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(borrowRepository.findBorrowsByUntilIsNotNullAndUser(user), HttpStatus.OK);
    }

    @GetMapping(path="/copy/{copy_id}")
    public ResponseEntity<Borrow> getCurrentCopyBorrow(@PathVariable("copy_id") long copyId)
    {
        Copy copy = copyRepository.findById(copyId).orElse(null);
        if(copy == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(borrowRepository.findBorrowByCopyAndUntilNull(copy), HttpStatus.OK);
    }

    @PostMapping(consumes="application/json")
    public Borrow registerNewBorrow(@RequestBody Borrow borrow)
    {
        return borrowRepository.save(borrow);
    }

    @PutMapping(consumes="application/json", path="return/{borrow_id}")
    public ResponseEntity<Borrow> updateBorrow(@RequestBody Borrow borrow, @PathVariable("borrow_id") long borrow_id)
    {
        Borrow b = borrowRepository.findById(borrow_id).orElse(null);
        if(b == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        b.setSince(borrow.getSince());
        b.setUntil(borrow.getUntil());
        return new ResponseEntity<>(borrowRepository.save(borrow), HttpStatus.OK);
    }
}
