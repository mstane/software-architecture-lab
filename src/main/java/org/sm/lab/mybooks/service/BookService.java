package org.sm.lab.mybooks.service;
import org.sm.lab.mybooks.domain.Book;
import org.sm.lab.mybooks.domain.SearchItem;
import org.sm.lab.mybooks.enums.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface BookService {
	
	public Page<Book> findReadersBooks(PageRequest pageRequest);

	public abstract Book findBook(String id);
	
	public abstract Page<SearchItem> search(String keyword, Genre genre, PageRequest pageRequest);
	
	public abstract Book saveBook(Book book);

	public abstract void deleteBook(String id);


}
