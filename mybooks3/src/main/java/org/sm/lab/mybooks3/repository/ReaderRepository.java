package org.sm.lab.mybooks3.repository;
import java.util.Optional;

import org.sm.lab.mybooks3.domain.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long>, JpaSpecificationExecutor<Reader> {

	Optional<Reader> findByUsername(String username);
	
	Optional<Reader> findByEmail(String email);
	
}
