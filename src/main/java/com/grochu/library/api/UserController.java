package com.grochu.library.api;

import com.grochu.library.DAL.Borrow;
import com.grochu.library.DAL.User;
import com.grochu.library.interfaces.BookRepository;
import com.grochu.library.interfaces.BorrowRepository;
import com.grochu.library.interfaces.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController
{
    private final UserRepository userRepository;
    private final BorrowRepository borrowRepository;

    @GetMapping("/self/info")
    public User getPersonalData(Principal principal)
    {
        User user = userRepository.findByEmail(principal.getName());
        return user;
    }

    @GetMapping(path="/borrows/current")
    public ResponseEntity<List<Borrow>> getBorrowedByUser(Principal principal)
    {
        User user = userRepository.findByEmail(principal.getName());
        if(user == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(borrowRepository.findBorrowsByUntilIsNullAndUser(user), HttpStatus.OK);
    }

    @GetMapping(path="/borrows/all")
    public ResponseEntity<List<Borrow>> getAllOfUserBorrows(Principal principal)
    {
        User user = userRepository.findByEmail(principal.getName());
        if(user == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(borrowRepository.findBorrowsByUser(user), HttpStatus.OK);
    }

    @GetMapping(path="/borrows/history")
    public ResponseEntity<List<Borrow>> getHistoryOfUserBorrows(Principal principal)
    {
        User user = userRepository.findByEmail(principal.getName());
        if(user == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(borrowRepository.findBorrowsByUntilIsNotNullAndUser(user), HttpStatus.OK);
    }

    @PutMapping("/self/info")
    public ResponseEntity<User> updatePersonalData(Principal principal, @RequestBody User user)
    {
        if(!user.getEmail().equals(principal.getName()))
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        User inBase = userRepository.findByEmail(principal.getName());
        if(inBase == null || !inBase.getId().equals(user.getId()))
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
