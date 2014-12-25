package org.sm.lab.mybooks2.service;
import java.util.List;

import org.sm.lab.mybooks2.domain.Note;

public interface NoteService {

	public abstract long countAllNotes();


	public abstract void deleteNote(Note note);


	public abstract Note findNote(String id);


	public abstract List<Note> findAllNotes();


	public abstract List<Note> findNoteEntries(int firstResult, int maxResults);


	public abstract void saveNote(Note note);


	public abstract Note updateNote(Note note);

}
