package org.sm.lab.mybooks3.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.sm.lab.mybooks3.domain.Book;

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

}
