package org.sm.lab.mybooks3.service;
import java.util.List;

import org.sm.lab.mybooks3.domain.Book;

public interface BookService {

	public abstract long countAllBooks();


	public abstract void deleteBook(Long id);


	public abstract Book findBook(Long id);


	public abstract List<Book> findAllBooks();


	public abstract Book saveBook(Book book);


	public abstract Book updateBook(Book book);

	

}
