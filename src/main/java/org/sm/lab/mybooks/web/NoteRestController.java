package org.sm.lab.mybooks.web;

import javax.validation.Valid;

import org.sm.lab.mybooks.domain.Note;
import org.sm.lab.mybooks.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Note get(@PathVariable("id") Long id) {
		return this.noteService.findNote(id);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public Note create(@RequestBody @Valid Note note, @RequestParam(value = "bookId") Long bookId) {
		return noteService.saveNote(bookId, note);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public Note update(@PathVariable("id") Long id, @RequestBody @Valid Note note, @RequestParam(value = "bookId") Long bookId) {
		return noteService.saveNote(bookId, note);
	}	
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
		this.noteService.deleteNote(id);
		return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
	}

	
}
