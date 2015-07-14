package org.sm.lab.mybooks3.service;
import org.sm.lab.mybooks3.domain.Book;
import org.sm.lab.mybooks3.domain.SearchItem;
import org.sm.lab.mybooks3.enums.Genre;
import org.springframework.data.domain.Page;

public interface BookService {
	
	public Page<Book> findReadersBooks(Long readerId, int pageNumber, int pageSize);

	public abstract Book findBook(Long id);
	
	public abstract Page<SearchItem> search(String keyword, Genre genre, int pageNumber, int pageSize);
	
	public abstract Book saveBook(Book book);

	public abstract void deleteBook(Long id);


}
