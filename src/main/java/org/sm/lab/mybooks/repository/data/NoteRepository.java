package org.sm.lab.mybooks.repository.data;
import org.sm.lab.mybooks.domain.Book;
import org.sm.lab.mybooks.domain.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long>, JpaSpecificationExecutor<Note> {
	
	Page<Note> findByBook(Book book, Pageable pageable);

	@Query("SELECT n FROM Note n WHERE LOWER(n.title) = LOWER(:keyword) OR  LOWER(n.content) = LOWER(:keyword)")
	Page<Note> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
	
	@Query("SELECT n FROM Note n WHERE "
			+ "LOWER(n.title) LIKE %:keyword%"
			+ " OR LOWER(n.content) LIKE %:keyword%"
			)
	Page<Note> search(@Param("keyword") String keyword, Pageable pageable);

	
}
