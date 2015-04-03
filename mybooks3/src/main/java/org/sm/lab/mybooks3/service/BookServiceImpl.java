package org.sm.lab.mybooks3.service;

import java.util.List;

import org.sm.lab.mybooks3.domain.Book;
import org.sm.lab.mybooks3.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookServiceImpl implements BookService {

	@Autowired
    BookRepository bookRepository;

	public long countAllBooks() {
        return bookRepository.count();
    }

	public void deleteBook(Long id) {
        bookRepository.delete(id);
    }

	public Book findBook(Long id) {
        return bookRepository.findOne(id);
    }

	public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

	public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

	public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

}
