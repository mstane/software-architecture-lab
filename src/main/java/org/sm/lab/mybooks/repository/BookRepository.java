package org.sm.lab.mybooks.repository;

import org.sm.lab.mybooks.domain.Book;
import org.sm.lab.mybooks.domain.Reader;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends CrudRepository<Book, String>, BookRepositoryCustom {

	Page<Book> findByReader(Reader reader, Pageable pageable);
	
	Page<Book> findByReaderId(String readerId, Pageable pageable);

	@Query("SELECT b FROM Book b WHERE LOWER(b.title) = LOWER(:keyword) OR  LOWER(b.author) = LOWER(:keyword)")
	Page<Book> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

}
