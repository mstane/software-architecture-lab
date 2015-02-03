package org.sm.lab.mybooks2.repository;

import java.util.List;

import org.sm.lab.mybooks2.domain.Book;
import org.sm.lab.mybooks2.domain.Reader;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaSpecificationExecutor<Book>,
		JpaRepository<Book, Long> {

	List<Book> findByReader(Reader reader, Pageable pageable);

	@Query("SELECT b FROM Book b WHERE LOWER(b.title) = LOWER(:keyword) OR  LOWER(b.author) = LOWER(:keyword)")
	List<Book> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

}
