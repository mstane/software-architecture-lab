package org.sm.lab.mybooks3.repository;


import org.sm.lab.mybooks3.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookRepository extends JpaSpecificationExecutor<Book>, JpaRepository<Book, Long> {

	
}
