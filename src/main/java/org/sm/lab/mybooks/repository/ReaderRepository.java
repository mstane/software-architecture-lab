package org.sm.lab.mybooks.repository;

import java.util.List;

import org.sm.lab.mybooks.domain.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.querybuilder.Delete;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;

@Repository
public class ReaderRepository {
	
    @Autowired
    private CassandraOperations cassandraTemplate;

	public Reader findOne(Long id) {
		Select select = QueryBuilder.select().from("reader");
		select.where(QueryBuilder.eq("id", id));
		return cassandraTemplate.selectOne(select, Reader.class);
	}

	public List<Reader> findAll() {
		Select select = QueryBuilder.select().from("reader");
		return cassandraTemplate.select(select, Reader.class);
	}

	public Reader save(Reader reader) {
		return cassandraTemplate.insert(reader);
	}

	public void delete(Long id) {
		Delete delete = QueryBuilder.delete().from("reader");
		delete.where(QueryBuilder.eq("id", id));
		cassandraTemplate.execute(delete);
	}

	public Reader findByEmail(String email) {
		Select select = QueryBuilder.select().from("reader");
		select.where(QueryBuilder.eq("email", email));

		return cassandraTemplate.selectOne(select, Reader.class);
	}

//	@Query("select * from reader where id = ?0")
//	Reader findOne(Long id);
	
//	@Query("delete from reader where id = ?0")
//	void delete(Long id);
	
//	@Query("select * from reader")
//	List<Reader> findAll();
//	
//	Optional<Reader> findByUsername(String username);
	
//	@Query("select * from reader where email = ?0")
//	Reader findByEmail(String email);
	
//	@Query("SELECT r FROM Reader r WHERE "
//			+ "LOWER(r.username) LIKE %:keyword%"
//			+ " OR LOWER(r.email) LIKE %:keyword%"
//			)
//	List<Reader> search(@Param("keyword") String keyword);
	
//	List<Reader> findByUsernameContaining(String keyword);
	
}
