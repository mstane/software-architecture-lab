package org.sm.lab.mybooks.repository;
import org.sm.lab.mybooks.domain.Book;
import org.sm.lab.mybooks.domain.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
	
	Page<Note> findByBook(Book book, Pageable pageable);

	@Query("SELECT n FROM Note n WHERE LOWER(n.title) = LOWER(:keyword) OR  LOWER(n.content) = LOWER(:keyword)")
	Page<Note> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
	
	@Query("SELECT n FROM Note n WHERE "
			+ "LOWER(n.title) LIKE %:keyword%"
			+ " OR LOWER(n.content) LIKE %:keyword%"
			)
	Page<Note> search(@Param("keyword") String keyword, Pageable pageable);

	
}
