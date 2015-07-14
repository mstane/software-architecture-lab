package org.sm.lab.mybooks3.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.sm.lab.mybooks3.domain.Book;
import org.sm.lab.mybooks3.domain.Reader;
import org.sm.lab.mybooks3.domain.SearchItem;
import org.sm.lab.mybooks3.enums.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class BookRepositoryImpl implements BookRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Book> search(String title, String author, String url,
			String startReadingDate, String endReadingDate, String rating,
			String genre) {

		StringBuilder queryBuilder = new StringBuilder();
		if (title != null && title.length() > 0) {
			queryBuilder.append("o.title = :title");
		}
		if (author != null && author.length() > 0) {
			if (queryBuilder.length() < 1)
				queryBuilder.append(" WHERE ");
			else
				queryBuilder.append(" AND ");
			queryBuilder.append("o.author = :author");
		}
		if (url != null && url.length() > 0) {
			if (queryBuilder.length() < 1)
				queryBuilder.append(" WHERE ");
			else
				queryBuilder.append(" AND ");
			queryBuilder.append("o.url = :url");
		}
		if (startReadingDate != null && startReadingDate.length() > 0) {
			if (queryBuilder.length() < 1)
				queryBuilder.append(" WHERE ");
			else
				queryBuilder.append(" AND ");
			queryBuilder.append("o.startReadingDate = :startReadingDate");
		}
		if (endReadingDate != null && endReadingDate.length() > 0) {
			if (queryBuilder.length() < 1)
				queryBuilder.append(" WHERE ");
			else
				queryBuilder.append(" AND ");
			queryBuilder.append("o.endReadingDate = :endReadingDate");
		}
		if (rating != null && rating.length() > 0) {
			if (queryBuilder.length() < 1)
				queryBuilder.append(" WHERE ");
			else
				queryBuilder.append(" AND ");
			queryBuilder.append("o.rating = :rating");
		}
		if (genre != null && genre.length() > 0) {
			if (queryBuilder.length() < 1)
				queryBuilder.append(" WHERE ");
			else
				queryBuilder.append(" AND ");
			queryBuilder.append("o.genre = :genre");
		}

		TypedQuery<Book> q = entityManager
				.createQuery(
						"SELECT o FROM Book AS o" + queryBuilder.toString(),
						Book.class);

		if (title != null && title.length() > 0)
			q.setParameter("title", title);
		if (author != null && author.length() > 0)
			q.setParameter("author", author);
		if (url != null && url.length() > 0)
			q.setParameter("url", url);
		if (startReadingDate != null && startReadingDate.length() > 0)
			q.setParameter("startReadingDate", startReadingDate);
		if (endReadingDate != null && endReadingDate.length() > 0)
			q.setParameter("endReadingDate", endReadingDate);
		if (rating != null && rating.length() > 0)
			q.setParameter("rating", title);
		if (genre != null && genre.length() > 0)
			q.setParameter("genre", title);

		return q.getResultList();

	}

	@Override
	public Long countSearch(String title, String author, String url,
			String startReadingDate, String endReadingDate, String rating,
			String genre) {
		List<Book> list = search(title, author, url, startReadingDate,
				endReadingDate, rating, genre);
		return Long.valueOf(list.size());
	}
	
	
	public List<Book> searchByPredicate(String username, String keyword, Pageable pageable, Genre genre) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Book> searchQuery = cb.createQuery(Book.class);
		Root<Book> searchRoot = searchQuery.from(Book.class);
		searchQuery.select(searchRoot);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		Join<Book, Reader> reader = searchRoot.join("reader");
		predicates.add(cb.equal(reader.<String>get("username"), username));
		
		
        if (keyword != null && !keyword.isEmpty()) {
            predicates.add(cb.like(searchRoot.<String>get("title"), keyword));
            predicates.add(cb.like(searchRoot.<String>get("author"), keyword));
            
//            predicates.add(cb.like(searchRoot.<String>get("review"), keyword));

//            predicates.add(cb.like(searchRoot.<String>get("title"), keyword));   //Notes
//            predicates.add(cb.like(searchRoot.<String>get("content"), keyword)); //Notes
        }
        
        if (genre != null) {
        	predicates.add(cb.equal(searchRoot.<Genre>get("genre"), genre));
        }
		
		searchQuery.where(predicates.toArray(new Predicate[]{}));
		
        List<Order> orderList = new ArrayList<Order>();
        orderList.add(cb.asc(searchRoot.get("id")));
        searchQuery.orderBy(orderList);
        
        TypedQuery<Book> filterQuery = entityManager.createQuery(searchQuery)
                .setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize());
		
		return filterQuery.getResultList();
	}
	
	@Override
	public Page<SearchItem> searchContents(String keyword, Genre genre, Pageable pageable) {
		List<SearchItem> totalContents = searchContents(keyword, genre);
		
		int firstResult = pageable.getPageNumber() * pageable.getPageSize();
		List<SearchItem> pageContent = new ArrayList<SearchItem>();
        for (int i = firstResult; i < totalContents.size() && i < firstResult + pageable.getPageSize(); i++) {
        	pageContent.add(totalContents.get(i));
        }
		
		return new PageImpl<SearchItem>(pageContent, pageable, totalContents.size());
	}
	
	public List<SearchItem> searchContents(String keyword, Genre genre) {
		List<SearchItem> bookSearchItems = searchBooks(keyword, genre);
		List<SearchItem> noteSearchItems = searchNotes(keyword, genre);
		bookSearchItems.addAll(noteSearchItems);
		return bookSearchItems;
	}
		
	private List<SearchItem> searchBooks(String keyword, Genre genre) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(" WHERE (");
		queryBuilder.append("LOWER(o.title) LIKE :keyword");
		queryBuilder.append(" OR ");
		queryBuilder.append("LOWER(o.author) LIKE :keyword");
		queryBuilder.append(" OR ");
		queryBuilder.append("LOWER(o.review) LIKE :keyword");
		queryBuilder.append(")");
		if (genre != null) {
			queryBuilder.append(" AND ");
			queryBuilder.append("o.genre = :genre");
		}

		TypedQuery<SearchItem> q = entityManager
				.createQuery(
						"SELECT NEW org.sm.lab.mybooks3.domain.SearchItem(o) FROM Book AS o" + queryBuilder.toString(),
						SearchItem.class);

		q.setParameter("keyword", "%"  +keyword + "%");
		if (genre != null) {
			q.setParameter("genre", genre);
		}

		return q.getResultList();

	}
	
	private List<SearchItem> searchNotes(String keyword, Genre genre) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(" WHERE (");
		queryBuilder.append("LOWER(o.title) LIKE :keyword");
		queryBuilder.append(" OR ");
		queryBuilder.append("LOWER(o.content) LIKE :keyword");
		queryBuilder.append(")");
		if (genre != null) {
			queryBuilder.append(" AND ");
			queryBuilder.append("o.book.genre = :genre");
		}

		TypedQuery<SearchItem> q = entityManager
				.createQuery(
						"SELECT NEW org.sm.lab.mybooks3.domain.SearchItem(o) FROM Note AS o" + queryBuilder.toString(),
						SearchItem.class);

		q.setParameter("keyword", "%"  +keyword + "%");
		if (genre != null) {
			q.setParameter("genre", genre);
		}

		return q.getResultList();

	}

	@Override
	public Long countSearch(String keyword, Genre genre) {
		List<SearchItem> list = searchContents(keyword, genre);
		return Long.valueOf(list.size());
	}
	
	
	

}

