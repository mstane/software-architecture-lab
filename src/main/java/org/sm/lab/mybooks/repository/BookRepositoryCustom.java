package org.sm.lab.mybooks.repository;

import java.util.List;

import org.sm.lab.mybooks.domain.Book;
import org.sm.lab.mybooks.domain.SearchItem;
import org.sm.lab.mybooks.enums.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookRepositoryCustom {
	
	public List<Book> search(String title, String author, String startReadingDate, String endReadingDate, String rating, String genre);

	public Long countSearch(String title, String author, String startReadingDate, String endReadingDate, String rating, String genre);

	public Page<SearchItem> searchContents(String readerId, String keyword, Genre genre, Pageable pageable);

	public Long countSearch(String readerId, String keyword, Genre genre);
	
}
