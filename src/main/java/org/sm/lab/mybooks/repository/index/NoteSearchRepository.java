package org.sm.lab.mybooks.repository.index;

import org.sm.lab.mybooks.domain.Note;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface NoteSearchRepository extends ElasticsearchRepository<Note, Long> {

}
