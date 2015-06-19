package org.sm.lab.mybooks3.web;

import javax.validation.Valid;

import org.sm.lab.mybooks3.domain.Reader;
import org.sm.lab.mybooks3.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/profiles")
public class ProfileRestController {
	
	@Autowired
	ReaderService readerService;

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Reader get(@PathVariable("id") long id) {
		return this.readerService.findReader(id);
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public Reader update(@RequestBody @Valid Reader reader) {
		return readerService.saveReader(reader);
	}

	
}
