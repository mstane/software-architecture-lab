package org.sm.lab.mybooks3.repository;

import java.util.List;

import org.sm.lab.mybooks3.domain.Book;
import org.sm.lab.mybooks3.domain.SearchItem;
import org.sm.lab.mybooks3.enums.Genre;

public interface BookRepositoryCustom {
	
	public List<Book> search(String title, String author, String url, String startReadingDate, String endReadingDate, String rating, String genre);

	public Long countSearch(String title, String author, String url, String startReadingDate, String endReadingDate, String rating, String genre);

	public List<SearchItem> search(String keyword, Genre genre);

	public Long countSearch(String keyword, Genre genre);
	
}
