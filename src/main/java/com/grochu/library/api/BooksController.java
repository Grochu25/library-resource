package com.grochu.library.api;

import com.grochu.library.DAL.Copy;
import com.grochu.library.PresenceProps;
import com.grochu.library.interfaces.CopyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.grochu.library.DAL.Book;
import com.grochu.library.interfaces.BookRepository;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path="/api/books", produces="application/json")
@RequiredArgsConstructor
public class BooksController 
{
	private final BookRepository bookRepo;
	private final CopyRepository copyRepo;
	private final PresenceProps presenceProps;

	@GetMapping(params = "!page")
	public List<Book> getAllBooks()
	{
		PageRequest pageRequest = PageRequest.of(0, presenceProps.getElementsOnPage());
		return bookRepo.findAll(pageRequest);
	}

	@GetMapping(params = "page")
	public List<Book> getBooksPage(@RequestParam("page") int page)
	{
		PageRequest pageRequest = PageRequest.of(page, presenceProps.getElementsOnPage());
		return bookRepo.findAll(pageRequest);
	}

	@GetMapping("/number")
	public int getBookNumber()
	{
		return bookRepo.countAll();
	}

	@GetMapping("/{id}")
	public Book getBook(@PathVariable("id") String id)
	{
		return bookRepo.findById(Long.parseLong(id));
	}

	@GetMapping("/mostPopular")
	public List<Book> mostPopularBooks()
	{
		return bookRepo.findMostPopular();
	}

	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Book addBook(@RequestBody Book book)
	{
		return bookRepo.save(book);
	}

	@DeleteMapping(path="/{id}/delete",consumes = "application/json")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteBook(@RequestBody Book book)
	{
		List<Copy> copies = copyRepo.getCopiesByBook(book);
		copyRepo.deleteAll(copies);
		bookRepo.delete(book);
	}
}
