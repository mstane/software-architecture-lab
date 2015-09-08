package org.sm.lab.mybooks.repository;
import org.sm.lab.mybooks.domain.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long>, JpaSpecificationExecutor<Reader> {

	Reader findByUsername(String username);
	
}
