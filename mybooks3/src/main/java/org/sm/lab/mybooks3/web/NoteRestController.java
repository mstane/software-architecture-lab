package org.sm.lab.mybooks3.web;

import java.util.List;

import javax.validation.Valid;

import org.sm.lab.mybooks3.domain.Note;
import org.sm.lab.mybooks3.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/notes")
public class NoteRestController {
	
	@Autowired
	NoteService noteService;
	
	@RequestMapping(method=RequestMethod.POST)
	public Note create(@RequestBody @Valid Note note) {
		return this.noteService.saveNote(note);
	}

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Note get(@PathVariable("id") long id) {
		return this.noteService.findNote(id);
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public Note update(@RequestBody @Valid Note note) {
		return noteService.saveNote(note);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public Note update(@PathVariable("id") long id, @RequestBody @Valid Note note) {
		return noteService.saveNote(note);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Boolean> delete(@PathVariable("id") long id) {
		this.noteService.deleteNote(id);
		return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
	}
	
	@RequestMapping(value="/search/{keyword}", method=RequestMethod.GET)
	public List<Note> get(@PathVariable("keyword") String keyword) {
		List<Note> resultNotes = noteService.search(keyword, 0, 10);
		return resultNotes;
	}

	
}
