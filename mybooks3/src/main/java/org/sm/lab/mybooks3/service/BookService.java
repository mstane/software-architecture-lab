package org.sm.lab.mybooks3.service;
import java.util.List;

import org.sm.lab.mybooks3.domain.Book;
import org.sm.lab.mybooks3.domain.Reader;
import org.sm.lab.mybooks3.domain.SearchItem;
import org.sm.lab.mybooks3.enums.Genre;

public interface BookService {

	public abstract void deleteBook(Long id);

	public abstract Book findBook(Long id);

	public List<Book> findReadersBooks(Long readerId);

	public abstract List<Book> findBookEntries(int firstResult, int maxResults);

	public abstract Book saveBook(Book book);

	public abstract Book updateBook(Book book);
	
	
	
	public abstract List<Book> findByReader(Reader reader, int firstResult, int maxResults);
	
	public abstract List<Book> findByKeyword(String keyword, int firstResult, int maxResults);
	
	public abstract  List<Book> search(String title, String author, String url, String startReadingDate, String endReadingDate, String rating, String genre);

	public abstract  Long countSearch(String title, String author, String url, String startReadingDate, String endReadingDate, String rating, String genre);

	public abstract List<SearchItem> search(String keyword, Genre genre);

	public Long countSearch(String keyword, Genre genre);


}
