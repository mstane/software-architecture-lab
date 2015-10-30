package org.sm.lab.mybooks.service;

import org.sm.lab.mybooks.domain.Book;
import org.sm.lab.mybooks.domain.Note;
import org.sm.lab.mybooks.repository.data.BookRepository;
import org.sm.lab.mybooks.repository.data.NoteRepository;
import org.sm.lab.mybooks.repository.index.NoteSearchRepository;
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
	
	@Autowired
	NoteSearchRepository noteSearchRepository;
	
	@Override
	@PreAuthorize("@authorizationService.canAccessNote(principal, #id)")
	public Note findNote(Long id) {
        return noteRepository.findOne(id);
    }
	
	@Override
	@PreAuthorize("@authorizationService.canAccessBook(principal, #bookId)")
	public Note saveNote(Long bookId, Note note) {
		Book book = bookRepository.findOne(bookId);
		note.setBook(book);
        note = noteRepository.save(note);
        noteSearchRepository.save(note);
        return note;
    }

	@Override
	@PreAuthorize("@authorizationService.canAccessNote(principal, #id)")
	public void deleteNote(Long id) {
        noteRepository.delete(id);
        noteSearchRepository.delete(id);
    }
	
}
