package org.sm.lab.mybooks.repository.index;

import java.util.Optional;

import org.sm.lab.mybooks.domain.Reader;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.Query;


public interface ReaderSearchRepository extends ElasticsearchRepository<Reader, Long> {
	
	@Query("{\"bool\" : {\"must\" : {\"field\" : {\"email\" : \"?0\"}}}}")
	Optional<Reader> findByEmail(String email);
	
//	@Query("{\"bool\" : {\"must\" : {\"field\" : {\"username\" : {\"query\" : \"?0\",\"analyze_wildcard\" : true}}}}}")
//	Page<Reader> search(String keyword, Pageable pageable);

}
