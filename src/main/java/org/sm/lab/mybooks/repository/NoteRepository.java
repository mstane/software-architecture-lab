package org.sm.lab.mybooks.repository;
import org.sm.lab.mybooks.domain.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.querybuilder.Delete;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;

@Repository
public class NoteRepository {
	
    @Autowired
    private CassandraOperations cassandraTemplate;

	public Note findOne(Long id) {
		Select select = QueryBuilder.select().from("note");
		select.where(QueryBuilder.eq("id", id));
		return cassandraTemplate.selectOne(select, Note.class);
	}

	public Note save(Note note) {
		return cassandraTemplate.insert(note);
	}

	public void delete(Long id) {
		Delete delete = QueryBuilder.delete().from("note");
		delete.where(QueryBuilder.eq("id", id));
		cassandraTemplate.execute(delete);
	}

	
//	@Query("select * from note where id = ?0")
//	Note findOne(Long id);
//	
//	@Query("delete from note where id = ?0")
//	void delete(Long id);
	
//	List<Note> findByBook(Book book);

//	@Query("SELECT n FROM Note n WHERE LOWER(n.title) = LOWER(:keyword) OR  LOWER(n.content) = LOWER(:keyword)")
//	List<Note> findByKeyword(@Param("keyword") String keyword);
	
//	@Query("SELECT n FROM Note n WHERE "
//			+ "LOWER(n.title) LIKE %:keyword%"
//			+ " OR LOWER(n.content) LIKE %:keyword%"
//			)
//	List<Note> search(@Param("keyword") String keyword);

	
}
