package org.sm.lab.mybooks3.service;


import java.util.List;

import org.sm.lab.mybooks3.domain.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
	@Override
	List<Book> findAll();
}
