package org.sm.lab.mybooks.repository;

import java.util.List;

import org.sm.lab.mybooks.domain.Book;
import org.sm.lab.mybooks.domain.Reader;
import org.sm.lab.mybooks.domain.SearchItem;
import org.sm.lab.mybooks.enums.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.querybuilder.Delete;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;

@Repository
public class BookRepository {
	
    @Autowired
    private CassandraOperations cassandraTemplate;

	public Book findOne(Long id) {
		Select select = QueryBuilder.select().from("book");
		select.where(QueryBuilder.eq("id", id));
		return cassandraTemplate.selectOne(select, Book.class);
	}

	public List<Book> findByReaderId(Long id) {
		Select select = QueryBuilder.select().from("book");
		select.where(QueryBuilder.eq("reader_id", id));
		return cassandraTemplate.select(select, Book.class);
	}

	public Page<SearchItem> searchContents(Long id, String keyword,
			Genre genre, PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	public Book save(Book book) {
		return cassandraTemplate.insert(book);
	}

	public void delete(Long id) {
		Delete delete = QueryBuilder.delete().from("book");
		delete.where(QueryBuilder.eq("id", id));
		cassandraTemplate.execute(delete);
		
	}

//	@Query("select * from book where id = ?0")
//	Book findOne(Long id);
//	
//	@Query("delete from book where id = ?0")
//	void delete(Long id);
	
//	List<Book> findByReader(Reader reader);
	
//	@Query("select * from book where reader_id = ?0")
//	List<Book> findByReaderId(Long readerId);

//	@Query("SELECT b FROM Book b WHERE LOWER(b.title) = LOWER(:keyword) OR  LOWER(b.author) = LOWER(:keyword)")
//	List<Book> findByKeyword(@Param("keyword") String keyword);

}
