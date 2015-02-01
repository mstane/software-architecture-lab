package org.sm.lab.mybooks2.service;
import java.util.List;

import org.sm.lab.mybooks2.domain.Book;

public interface BookService {

	public abstract long countAllBooks();


	public abstract void deleteBook(Book book);


	public abstract Book findBook(Long id);


	public abstract List<Book> findAllBooks();


	public abstract List<Book> findBookEntries(int firstResult, int maxResults);


	public abstract void saveBook(Book book);


	public abstract Book updateBook(Book book);

}
