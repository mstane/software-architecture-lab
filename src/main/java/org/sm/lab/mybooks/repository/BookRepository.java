package org.sm.lab.mybooks.repository;

import org.sm.lab.mybooks.domain.Book;
import org.sm.lab.mybooks.domain.Reader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {

	Page<Book> findByReader(Reader reader, Pageable pageable);
	
	Page<Book> findByReaderId(String readerId, Pageable pageable);

	@Query("{ '$or': [ {'title' : {$regex : ?0}}, {'author' : {$regex : ?0}}, {'review' : {$regex : ?0}} ] }")
	Page<Book> findByKeyword(String keyword, Pageable pageable);

}
