package org.sm.lab.mybooks.repository.data;
import java.util.Optional;

import org.sm.lab.mybooks.domain.Reader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.hazelcast.repository.HazelcastRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReaderKVRepository extends HazelcastRepository<Reader, Long> {

	Optional<Reader> findByUsername(String username);
	
	Optional<Reader> findByEmail(String email);
	
	@Query("SELECT r FROM Reader r WHERE "
			+ "LOWER(r.username) LIKE %:keyword%"
			+ " OR LOWER(r.email) LIKE %:keyword%"
			)
	Page<Reader> search(@Param("keyword") String keyword, Pageable pageable);
	
	Page<Reader> findByUsernameContaining(String keyword, Pageable pageable);
	
}
