package org.sm.lab.mybooks.web;

import javax.validation.Valid;

import org.sm.lab.mybooks.domain.Book;
import org.sm.lab.mybooks.domain.SearchItem;
import org.sm.lab.mybooks.enums.Genre;
import org.sm.lab.mybooks.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/books")
public class BookRestController {
	
	@Autowired
	BookService bookService;
	
	@RequestMapping(method=RequestMethod.GET)
	public Page<Book> list(@RequestParam(value = "pageNumber", required = false) Integer pageNumber, @RequestParam(value = "pageSize", required = false) Integer pageSize) {
		return this.bookService.findReadersBooks(new PageRequest(pageNumber, pageSize));
	}

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Book get(@PathVariable("id") long id) {
		return this.bookService.findBook(id);
	}
	
	@RequestMapping(params = "search", method=RequestMethod.GET)
	public Page<SearchItem> searchContent(@RequestParam("search") String keyword, @RequestParam(value = "genre", required = false) Genre genre, @RequestParam(value = "pageNumber", required = false) Integer pageNumber, @RequestParam(value = "pageSize", required = false) Integer pageSize) {
		return bookService.search(keyword, genre, new PageRequest(pageNumber, pageSize));
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public Book create(@RequestBody @Valid Book book) {
		return this.bookService.saveBook(book);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public Book update(@PathVariable("id") long id, @RequestBody @Valid Book book) {
		return bookService.saveBook(book);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Boolean> delete(@PathVariable("id") long id) {
		this.bookService.deleteBook(id);
		return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
	}
	

}
