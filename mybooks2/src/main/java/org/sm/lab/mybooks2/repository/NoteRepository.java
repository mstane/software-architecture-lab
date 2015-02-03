package org.sm.lab.mybooks2.repository;
import java.util.List;

import org.sm.lab.mybooks2.domain.Book;
import org.sm.lab.mybooks2.domain.Note;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long>, JpaSpecificationExecutor<Note> {
	
	List<Note> findByBook(Book book, Pageable pageable);

	@Query("SELECT n FROM Note n WHERE LOWER(n.title) = LOWER(:keyword) OR  LOWER(n.content) = LOWER(:keyword)")
	List<Note> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

	
}
