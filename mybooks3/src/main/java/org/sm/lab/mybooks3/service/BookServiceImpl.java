package org.sm.lab.mybooks3.service;

import org.sm.lab.mybooks3.domain.Book;
import org.sm.lab.mybooks3.domain.SearchItem;
import org.sm.lab.mybooks3.enums.Genre;
import org.sm.lab.mybooks3.repository.BookRepository;
import org.sm.lab.mybooks3.repository.ReaderRepository;
import org.sm.lab.mybooks3.security.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookServiceImpl implements BookService {

	@Autowired
    BookRepository bookRepository;
	
	@Autowired
	ReaderRepository readerRepository;
	
	@Autowired
	AuthorizationService authorizationService;
	
	@Override
	@PreAuthorize("@authorizationService.canAccessUser(principal, #readerId)")
	public Page<Book> findReadersBooks(Long readerId, int pageNumber, int pageSize) {
		Page<Book> books = bookRepository.findByReaderId(readerId, new PageRequest(pageNumber, pageSize));
		return books;
    }
	
	@Override
	public Book findBook(Long id) {
        return bookRepository.findOne(id);
    }
	
	@Override
	public Page<SearchItem> search(String keyword, Genre genre, int pageNumber, int pageSize) {
		return bookRepository.searchContents(keyword, genre, new PageRequest(pageNumber, pageSize));
	}

	@Override
	@PreAuthorize("@authorizationService.canUpdateBook(principal, #receivedBook)")
	public Book saveBook(Book receivedBook) {
		Book book = null;
		
		if (receivedBook.getId() != null) {
			book = bookRepository.findOne(receivedBook.getId());
			book.copyFields(receivedBook); // to preserve relationship to notes
		} else {
			book = receivedBook;
		}
		
        return bookRepository.save(book);
    }
	
	@Override
	@PreAuthorize("@authorizationService.canUpdateBook(principal, #id)")
	public void deleteBook(Long id) {
        bookRepository.delete(id);
    }

	
}
