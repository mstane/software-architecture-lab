package org.sm.lab.mybooks.repository.data;
import org.sm.lab.mybooks.domain.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long>, JpaSpecificationExecutor<Reader> {

	@Query("SELECT r.id FROM Reader r")
    public Iterable<Long> findAllId();
	
}
