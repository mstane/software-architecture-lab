package org.sm.lab.mybooks.repository;
import java.util.Optional;

import org.sm.lab.mybooks.domain.Reader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ReaderRepository extends MongoRepository<Reader, String> {

	Optional<Reader> findByUsername(String username);
	
	Optional<Reader> findByEmail(String email);
	
	@Query("{ '$or': [ {'email' : {$regex : ?0}}, {'username' : {$regex : ?0}} ] }")
	Page<Reader> search(String keyword, Pageable pageable);
	
	Page<Reader> findByUsernameContaining(String keyword, Pageable pageable);
	
}
