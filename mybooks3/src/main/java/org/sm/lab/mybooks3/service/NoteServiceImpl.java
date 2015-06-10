package org.sm.lab.mybooks3.service;

import java.util.List;

import org.sm.lab.mybooks3.domain.Book;
import org.sm.lab.mybooks3.domain.Note;
import org.sm.lab.mybooks3.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {

	@Autowired
    NoteRepository noteRepository;

	public long countAllNotes() {
        return noteRepository.count();
    }

	public void deleteNote(Long id) {
        noteRepository.delete(id);
    }

	public Note findNote(Long id) {
        return noteRepository.findOne(id);
    }

	public List<Note> findAllNotes() {
        return noteRepository.findAll();
    }

	public List<Note> findNoteEntries(int firstResult, int maxResults) {
        return noteRepository.findAll(new PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public Note saveNote(Note note) {
        return noteRepository.save(note);
    }

	public Note updateNote(Note note) {
        return noteRepository.save(note);
    }
	
	public List<Note> findByBook(Book book, int firstResult, int maxResults) {
		return noteRepository.findByBook(book, new PageRequest(firstResult / maxResults, maxResults, new Sort("title"))).getContent();
	}
	
	public List<Note> findByKeyword(String keyword, int firstResult, int maxResults) {
        return noteRepository.findByKeyword(keyword, new PageRequest(firstResult / maxResults, maxResults, new Sort("title"))).getContent();
    }
	
	public List<Note> search(String keyword, int firstResult, int maxResults) {
		return noteRepository.search(keyword.toLowerCase(), new PageRequest(firstResult / maxResults, maxResults)).getContent();
	}
	
}
