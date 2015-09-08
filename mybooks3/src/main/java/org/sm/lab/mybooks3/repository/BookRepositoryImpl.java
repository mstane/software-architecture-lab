package org.sm.lab.mybooks3.repository;

import java.util.ArrayList;
import java.util.List;

import org.sm.lab.mybooks3.domain.Book;
import org.sm.lab.mybooks3.domain.SearchItem;
import org.sm.lab.mybooks3.enums.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class BookRepositoryImpl implements BookRepositoryCustom {


	@Override
	public List<Book> search(String title, String author,
			String startReadingDate, String endReadingDate, String rating,
			String genre) {

		return null;

	}

	@Override
	public Long countSearch(String title, String author,
			String startReadingDate, String endReadingDate, String rating,
			String genre) {
		List<Book> list = search(title, author, startReadingDate,
				endReadingDate, rating, genre);
		return Long.valueOf(list.size());
	}
	
	
	public List<Book> searchByPredicate(String username, String keyword, Pageable pageable, Genre genre) {
		return null;
	}
	
	@Override
	public Page<SearchItem> searchContents(String readerId, String keyword, Genre genre, Pageable pageable) {
		List<SearchItem> totalContents = searchContents(readerId, keyword, genre);
		
		int firstResult = pageable.getPageNumber() * pageable.getPageSize();
		List<SearchItem> pageContent = new ArrayList<SearchItem>();
        for (int i = firstResult; i < totalContents.size() && i < firstResult + pageable.getPageSize(); i++) {
        	pageContent.add(totalContents.get(i));
        }
		
		return new PageImpl<SearchItem>(pageContent, pageable, totalContents.size());
	}
	
	public List<SearchItem> searchContents(String readerId, String keyword, Genre genre) {
		List<SearchItem> bookSearchItems = searchBooks(readerId, keyword, genre);
		List<SearchItem> noteSearchItems = searchNotes(readerId, keyword, genre);
		bookSearchItems.addAll(noteSearchItems);
		return bookSearchItems;
	}
		
	private List<SearchItem> searchBooks(String readerId, String keyword, Genre genre) {
		return null;
	}
	
	private List<SearchItem> searchNotes(String readerId, String keyword, Genre genre) {
		return null;
	}

	@Override
	public Long countSearch(String readerId, String keyword, Genre genre) {
		List<SearchItem> list = searchContents(readerId, keyword, genre);
		return Long.valueOf(list.size());
	}
	
	
	

}

