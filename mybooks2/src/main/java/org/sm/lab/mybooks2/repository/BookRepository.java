package org.sm.lab.mybooks2.repository;
import org.sm.lab.mybooks2.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaSpecificationExecutor<Book>, JpaRepository<Book, String> {
}
