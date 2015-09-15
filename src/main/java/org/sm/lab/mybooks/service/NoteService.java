package org.sm.lab.mybooks.service;
import org.sm.lab.mybooks.domain.Note;

public interface NoteService {
	
	public abstract Note findNote(Long id);
	
	public abstract Note saveNote(Long bookId, Note note);
	
	public abstract void deleteNote(Long id);
	
}
