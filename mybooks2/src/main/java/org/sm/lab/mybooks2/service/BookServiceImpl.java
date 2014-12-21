package org.sm.lab.mybooks2.service;

import java.util.List;
import org.sm.lab.mybooks2.domain.Book;
import org.sm.lab.mybooks2.repository.BookRepository;
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

	public void deleteBook(Book book) {
        bookRepository.delete(book);
    }

	public Book findBook(Long id) {
        return bookRepository.findOne(id);
    }

	public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

	public List<Book> findBookEntries(int firstResult, int maxResults) {
        return bookRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveBook(Book book) {
        bookRepository.save(book);
    }

	public Book updateBook(Book book) {
        return bookRepository.save(book);
    }
}
