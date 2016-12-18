package org.sm.lab.mybooks.web;

import javax.validation.Valid;

import org.sm.lab.mybooks.domain.Reader;
import org.sm.lab.mybooks.service.ReaderKVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/profiles")
public class ProfileRestController {
	
	@Autowired
	ReaderKVService readerKVService;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Reader get(@PathVariable("id") long id) {
		return this.readerKVService.findReader(id);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public Reader update(@PathVariable("id") long id, @RequestBody @Valid Reader reader) {
		reader.setPassword(encodePassword(reader.getPassword()));
		return readerKVService.saveReader(reader);
	}
	
	private String encodePassword(String password) {
		if (password != null) {
			password  = passwordEncoder.encode(password);
			return password;
		}
		return null;
	}

	
}
