package org.sm.lab.mybooks3.web;

import java.util.List;

import javax.validation.Valid;

import org.sm.lab.mybooks3.domain.Reader;
import org.sm.lab.mybooks3.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/readers")
public class ReaderRestController {
	
	@Autowired
	ReaderService readerService;
	
	@RequestMapping(method=RequestMethod.POST)
	public Reader create(@RequestBody @Valid Reader reader) {
		return this.readerService.saveReader(reader);
	}

	@RequestMapping(method=RequestMethod.GET)
	public List<Reader> list() {
		return this.readerService.findAllReaders();
	}

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Reader get(@PathVariable("id") long id) {
		return this.readerService.findReader(id);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public Reader update(@PathVariable("id") long id, @RequestBody @Valid Reader reader) {
		return readerService.saveReader(reader);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Boolean> delete(@PathVariable("id") long id) {
		this.readerService.deleteReader(id);
		return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
	}
}
