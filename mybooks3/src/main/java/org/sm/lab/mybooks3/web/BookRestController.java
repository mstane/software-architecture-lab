package org.sm.lab.mybooks3.web;

import java.util.List;

import javax.validation.Valid;

import org.sm.lab.mybooks3.domain.Book;
import org.sm.lab.mybooks3.domain.SearchItem;
import org.sm.lab.mybooks3.enums.Genre;
import org.sm.lab.mybooks3.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	@RequestMapping(method=RequestMethod.POST)
	public Book create(@RequestBody @Valid Book book) {
		return this.bookService.saveBook(book);
	}

	@RequestMapping(method=RequestMethod.GET)
	public List<Book> list() {
//		return this.bookService.findAllBooks();
		return this.bookService.findReadersBooks((long)1);
	}

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Book get(@PathVariable("id") long id) {
		return this.bookService.findBook(id);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public Book update(@PathVariable("id") long id, @RequestBody @Valid Book book) {
		return bookService.saveBook(book);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Boolean> delete(@PathVariable("id") long id) {
		this.bookService.deleteBook(id);
		return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
	}
	
	@RequestMapping(value="/search/{keyword}", method=RequestMethod.GET)
	public List<SearchItem> get(@PathVariable("keyword") String keyword, @RequestParam(value = "genre", required = false) Genre genre) {
		List<SearchItem> result = bookService.search(keyword, genre);
		return result;
	}
}
