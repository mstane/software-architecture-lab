package org.sm.lab.mybooks.service;

import java.util.List;

import org.sm.lab.mybooks.domain.Book;
import org.sm.lab.mybooks.domain.Note;
import org.sm.lab.mybooks.repository.BookRepository;
import org.sm.lab.mybooks.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {

	@Autowired
    NoteRepository noteRepository;
	
	@Autowired
    BookRepository bookRepository;
	
	@Override
	@PreAuthorize("@authorizationService.canAccessNote(principal, #id)")
	public Note findNote(String id) {
        return noteRepository.findOne(id);
    }
	
	@Override
	@PreAuthorize("@authorizationService.canAccessBook(principal, #bookId)")
	public Note saveNote(String bookId, Note note) {
		Book book = bookRepository.findOne(bookId);
		note.setBook(book);
        note = noteRepository.save(note);
        return note;
    }

	@Override
	@PreAuthorize("@authorizationService.canAccessNote(principal, #id)")
	public void deleteNote(String id) {
        noteRepository.delete(id);
    }
	
	@Override
	@PreAuthorize("@authorizationService.canAccessBook(principal, #bookId)")
	public List<Note> findBooksNotes(String bookId) {
		return noteRepository.findByBookId(bookId);
    }
	
}
