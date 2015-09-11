package org.sm.lab.mybooks.repository;
import java.util.Optional;

import org.sm.lab.mybooks.domain.Reader;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ReaderRepository extends CrudRepository<Reader, String> {

	Page<Reader> findAll(Pageable pageable);
	
	Optional<Reader> findByUsername(String username);
	
	Optional<Reader> findByEmail(String email);
	
	@Query("SELECT r FROM Reader r WHERE "
			+ "LOWER(r.username) LIKE %:keyword%"
			+ " OR LOWER(r.email) LIKE %:keyword%"
			)
	Page<Reader> search(@Param("keyword") String keyword, Pageable pageable);
	
	Page<Reader> findByUsernameContaining(String keyword, Pageable pageable);
	
}
