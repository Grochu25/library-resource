package com.grochu.library.api;

import com.grochu.library.DAL.Borrow;
import com.grochu.library.DAL.User;
import com.grochu.library.PresenceProps;
import com.grochu.library.interfaces.BorrowRepository;
import com.grochu.library.interfaces.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController
{
    private final UserRepository userRepository;
    private final BorrowRepository borrowRepository;
    private final PresenceProps presenceProps;

    @GetMapping(params="!page")
    public List<User> getFirstPageOfUsers(@RequestParam(value="search", required = false) String searchPhrase)
    {
        PageRequest pageRequest = PageRequest.of(0, presenceProps.getElementsOnPage());
        if(searchPhrase == null)
            return userRepository.findAllByOrderBySurnameAscNameAsc(pageRequest);
        else
        {
            Set set = new HashSet();
            set.addAll(userRepository.findBySurnameContainingIgnoreCaseOrderBySurnameAscNameAsc(searchPhrase, pageRequest));
            set.addAll(userRepository.findByNameContainingIgnoreCaseOrderBySurnameAscNameAsc(searchPhrase, pageRequest));
            return set.stream().toList();
        }
    }

    @GetMapping(params="page")
    public List<User> getPageOfUsers(@RequestParam("page") int page,
                                     @RequestParam(value="search", required = false) String searchPhrase)
    {
        PageRequest pageRequest = PageRequest.of(page-1, presenceProps.getElementsOnPage());
        if(searchPhrase == null)
            return userRepository.findAllByOrderBySurnameAscNameAsc(pageRequest);
        else
        {
            Set set = new HashSet();
            set.addAll(userRepository.findBySurnameContainingIgnoreCaseOrderBySurnameAscNameAsc(searchPhrase, pageRequest));
            set.addAll(userRepository.findByNameContainingIgnoreCaseOrderBySurnameAscNameAsc(searchPhrase, pageRequest));
            return set.stream().toList();
        }
    }

    @GetMapping(path="/number")
    public Long getUserNumber(@RequestParam(value = "search", required = false) String searchPhrase)
    {
        if(searchPhrase == null)
            return userRepository.count();
        else
            return userRepository.countByNameContainingIgnoreCaseOrderBySurnameAscNameAsc(searchPhrase)+
                    userRepository.countBySurnameContainingIgnoreCaseOrderBySurnameAscNameAsc(searchPhrase);
    }

    @GetMapping(path="/{id}")
    public User getUsersDetails(@PathVariable("id") Long id)
    {
        return userRepository.getUserById(id);
    }

    @GetMapping(path="/{id}/borrows/all")
    public ResponseEntity<List<Borrow>> getAllOfUserBorrows(@PathVariable("id") Long id)
    {
        User user = userRepository.findById(id).orElse(null);
        if(user == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(borrowRepository.findBorrowsByUser(user), HttpStatus.OK);
    }

    @GetMapping("/self/info")
    public User getPersonalData(Principal principal)
    {
        User user = userRepository.findByEmail(principal.getName());
        return user;
    }

    @GetMapping(path="/self/borrows/current")
    public ResponseEntity<List<Borrow>> getBorrowedByUserPersonal(Principal principal)
    {
        User user = userRepository.findByEmail(principal.getName());
        if(user == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(borrowRepository.findBorrowsByUntilIsNullAndUser(user), HttpStatus.OK);
    }

    @GetMapping(path="/self/borrows/all")
    public ResponseEntity<List<Borrow>> getAllOfUserBorrowsPersonal(Principal principal)
    {
        User user = userRepository.findByEmail(principal.getName());
        if(user == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(borrowRepository.findBorrowsByUser(user), HttpStatus.OK);
    }

    @GetMapping(path="/self/borrows/history")
    public ResponseEntity<List<Borrow>> getHistoryOfUserBorrowsPersonal(Principal principal)
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
