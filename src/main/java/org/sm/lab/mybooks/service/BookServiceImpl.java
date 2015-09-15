package org.sm.lab.mybooks.service;

import java.util.List;

import org.sm.lab.mybooks.domain.Book;
import org.sm.lab.mybooks.domain.SearchItem;
import org.sm.lab.mybooks.enums.Genre;
import org.sm.lab.mybooks.repository.BookRepository;
import org.sm.lab.mybooks.repository.ReaderRepository;
import org.sm.lab.mybooks.security.AuthorizationService;
import org.sm.lab.mybooks.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
	public Page<Book> findReadersBooks(PageRequest pageRequest) {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Book> books = bookRepository.findByReaderId(userDetails.getId());
		Page<Book> page = new PageImpl<Book>(books);
		return page;
    }
	
	@Override
	@PreAuthorize("@authorizationService.canAccessBook(principal, #id)")
	public Book findBook(Long id) {
        return bookRepository.findOne(id);
    }
	
	@Override
	public Page<SearchItem> search(String keyword, Genre genre, PageRequest pageRequest) {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return bookRepository.searchContents(userDetails.getId(), keyword, genre, pageRequest);
	}

	@Override
	@PreAuthorize("@authorizationService.canAccessBook(principal, #receivedBook)")
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
	@PreAuthorize("@authorizationService.canAccessBook(principal, #id)")
	public void deleteBook(Long id) {
        bookRepository.delete(id);
    }

	
}
