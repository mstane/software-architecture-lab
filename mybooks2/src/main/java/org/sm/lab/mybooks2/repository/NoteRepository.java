package org.sm.lab.mybooks2.repository;
import org.sm.lab.mybooks2.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, String>, JpaSpecificationExecutor<Note> {
}
