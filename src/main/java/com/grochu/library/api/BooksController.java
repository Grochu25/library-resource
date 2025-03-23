package com.grochu.library.api;

import com.grochu.library.Domain.Copy;
import com.grochu.library.PresenceProps;
import com.grochu.library.interfaces.CopyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.grochu.library.Domain.Book;
import com.grochu.library.interfaces.BookRepository;

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
	public List<Book> getAllBooksPagable(@RequestParam(value="search", required = false) String searchPhrase)
	{
		PageRequest pageRequest = PageRequest.of(0, presenceProps.getElementsOnPage());
		if(searchPhrase == null)
			return bookRepo.findAllByOrderByTitleAsc(pageRequest);
		else
			return bookRepo.findByTitleContainingOrderByTitleAsc(searchPhrase, pageRequest);
	}

	@GetMapping(params = "page")
	public List<Book> getAllBooksPagable(@RequestParam("page") int page,
										 @RequestParam(value="search", required = false) String searchPhrase)
	{
		PageRequest pageRequest = PageRequest.of(page-1, presenceProps.getElementsOnPage());
		if(searchPhrase == null)
			return bookRepo.findAllByOrderByTitleAsc(pageRequest);
		else
			return bookRepo.findByTitleContainingOrderByTitleAsc(searchPhrase, pageRequest);
	}

	@GetMapping("/all")
	public Iterable<Book> getAllBooks()
	{
		return bookRepo.findAll();
	}

	@GetMapping("/number")
	public long getBookNumber(@RequestParam(value = "search", required = false) String searchPhrase)
	{
		if(searchPhrase == null)
			return bookRepo.count();
		else
			return bookRepo.countByTitleContainingOrderByTitleAsc(searchPhrase);
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
