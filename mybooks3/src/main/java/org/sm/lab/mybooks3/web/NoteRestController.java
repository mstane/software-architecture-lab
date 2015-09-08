package org.sm.lab.mybooks3.web;

import javax.validation.Valid;

import org.sm.lab.mybooks3.domain.Note;
import org.sm.lab.mybooks3.service.NoteService;
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
	public Note get(@PathVariable("id") String id) {
		return this.noteService.findNote(id);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public Note create(@RequestBody @Valid Note note, @RequestParam(value = "bookId") String bookId) {
		return noteService.saveNote(bookId, note);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public Note update(@PathVariable("id") String id, @RequestBody @Valid Note note, @RequestParam(value = "bookId") String bookId) {
		return noteService.saveNote(bookId, note);
	}	
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Boolean> delete(@PathVariable("id") String id) {
		this.noteService.deleteNote(id);
		return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
	}

	
}
