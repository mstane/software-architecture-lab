package org.sm.lab.mybooks.repository.index;

import org.sm.lab.mybooks.domain.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface BookSearchRepository extends ElasticsearchRepository<Book, Long> {

}
