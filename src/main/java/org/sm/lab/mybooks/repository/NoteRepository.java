package org.sm.lab.mybooks.repository;
import java.util.List;

import org.sm.lab.mybooks.domain.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface NoteRepository extends MongoRepository<Note, String> {
	
	List<Note> findByBookId(String bookId);

	@Query("{ '$or': [ {'title' : {$regex : ?0}}, {'content' : {$regex : ?0}} ] }")
	Page<Note> findByKeyword(String keyword, Pageable pageable);
	
	@Query("{ '$or': [ {'title' : {$regex : ?0}}, {'content' : {$regex : ?0}} ] }")
	Page<Note> search(String keyword, Pageable pageable);

	
}
