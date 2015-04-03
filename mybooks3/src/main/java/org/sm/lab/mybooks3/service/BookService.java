package org.sm.lab.mybooks3.service;
import java.util.List;

import org.sm.lab.mybooks3.domain.Book;
import org.sm.lab.mybooks3.domain.Reader;

public interface BookService {

	public abstract long countAllBooks();


	public abstract void deleteBook(Long id);


	public abstract Book findBook(Long id);


	public abstract List<Book> findAllBooks();


	public abstract List<Book> findBookEntries(int firstResult, int maxResults);


	public abstract Book saveBook(Book book);


	public abstract Book updateBook(Book book);
	
	public abstract List<Book> findByReader(Reader reader, int firstResult, int maxResults);
	
	public abstract List<Book> findByKeyword(String keyword, int firstResult, int maxResults);
	

}
