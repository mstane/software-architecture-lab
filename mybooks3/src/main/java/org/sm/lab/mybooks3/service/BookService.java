package org.sm.lab.mybooks3.service;
import org.sm.lab.mybooks3.domain.Book;
import org.sm.lab.mybooks3.domain.SearchItem;
import org.sm.lab.mybooks3.enums.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface BookService {
	
	public Page<Book> findReadersBooks(PageRequest pageRequest);

	public abstract Book findBook(String id);
	
	public abstract Page<SearchItem> search(String keyword, Genre genre, PageRequest pageRequest);
	
	public abstract Book saveBook(Book book);

	public abstract void deleteBook(String id);


}
