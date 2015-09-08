package org.sm.lab.mybooks.service;
import org.sm.lab.mybooks.domain.Note;

public interface NoteService {
	
	public abstract Note findNote(String id);
	
	public abstract Note saveNote(String bookId, Note note);
	
	public abstract void deleteNote(String id);
	
}
