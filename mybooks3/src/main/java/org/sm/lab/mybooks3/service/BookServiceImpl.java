package org.sm.lab.mybooks3.service;

import java.util.List;

import org.sm.lab.mybooks3.domain.Book;
import org.sm.lab.mybooks3.domain.Reader;
import org.sm.lab.mybooks3.repository.BookRepository;
import org.sm.lab.mybooks3.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookServiceImpl implements BookService {

	@Autowired
    BookRepository bookRepository;
	
	@Autowired
	ReaderRepository readerRepository;

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

	public List<Book> findBookEntries(int firstResult, int maxResults) {
        return bookRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public Book saveBook(Book book) {
//        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Reader reader = readerRepository.findOne(userDetails.getId());
//        book.setReader(reader);
        return bookRepository.save(book);
    }

	public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

	@Override
	public List<Book> findByReader(Reader reader, int firstResult, int maxResults) {
		return bookRepository.findByReader(reader, new PageRequest(firstResult / maxResults, maxResults, new Sort("title"))).getContent();
	}

	@Override
	public List<Book> findByKeyword(String keyword, int firstResult, int maxResults) {
		return bookRepository.findByKeyword(keyword, new PageRequest(firstResult / maxResults, maxResults, new Sort("title"))).getContent();
	}

	@Override
	public List<Book> search(String title, String author, String url, String startReadingDate, String endReadingDate, String rating, String genre) {
		return bookRepository.search(title, author, url, startReadingDate, endReadingDate, rating, genre);
	}
	
	@Override
	public Long countSearch(String title, String author, String url, String startReadingDate, String endReadingDate, String rating, String genre) {
		return bookRepository.countSearch(title, author, url, startReadingDate, endReadingDate, rating, genre);
	}
	
}
