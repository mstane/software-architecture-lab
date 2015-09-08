package org.sm.lab.mybooks3.repository;
import java.util.Optional;

import org.sm.lab.mybooks3.domain.Reader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReaderRepository extends MongoRepository<Reader, String> {

	Optional<Reader> findByUsername(String username);
	
	Optional<Reader> findByEmail(String email);
	
	@Query("SELECT r FROM Reader r WHERE "
			+ "LOWER(r.username) LIKE %:keyword%"
			+ " OR LOWER(r.email) LIKE %:keyword%"
			)
	Page<Reader> search(@Param("keyword") String keyword, Pageable pageable);
	
	Page<Reader> findByUsernameContaining(String keyword, Pageable pageable);
	
}
