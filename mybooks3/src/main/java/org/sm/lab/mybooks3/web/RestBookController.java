package org.sm.lab.mybooks3.web;

import java.util.List;

import javax.validation.Valid;

import org.sm.lab.mybooks3.domain.Book;
import org.sm.lab.mybooks3.service.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/books")
public class RestBookController {
	private BookRepository bookRepository;
	
	@Autowired
	public RestBookController(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@RequestMapping(method=RequestMethod.POST)
	public Book create(@RequestBody @Valid Book book) {
		return this.bookRepository.save(book);
	}

	@RequestMapping(method=RequestMethod.GET)
	public List<Book> list() {
		return this.bookRepository.findAll();
	}

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Book get(@PathVariable("id") long id) {
		return this.bookRepository.findOne(id);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public Book update(@PathVariable("id") long id, @RequestBody @Valid Book book) {
		return bookRepository.save(book);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Boolean> delete(@PathVariable("id") long id) {
		this.bookRepository.delete(id);
		return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
	}
}
